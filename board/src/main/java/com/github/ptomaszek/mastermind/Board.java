package com.github.ptomaszek.mastermind;

import com.github.ptomaszek.mastermind.exception.InsertsNotInColorPoolException;
import com.github.ptomaszek.mastermind.exception.NonUniqueInsertsException;
import com.github.ptomaszek.mastermind.exception.WrongNumberOfInsertsException;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.OptionalInt;
import java.util.stream.IntStream;

import static com.github.ptomaszek.mastermind.Color.BLACK;
import static com.github.ptomaszek.mastermind.Color.BLUE;
import static com.github.ptomaszek.mastermind.Color.GREEN;
import static com.github.ptomaszek.mastermind.Color.RED;
import static com.github.ptomaszek.mastermind.Color.WHITE;
import static com.github.ptomaszek.mastermind.Color.YELLOW;
import static com.github.ptomaszek.mastermind.util.Preconditions.checkArgument;
import static com.google.common.collect.Lists.newArrayList;
import static java.util.Arrays.asList;
import static java.util.Collections.shuffle;
import static java.util.stream.Collectors.toList;

public class Board {

    private ImmutableSet<Color> colorsPool;
    private ImmutableCollection<Color> enigmaColors;
    private boolean unique;
    private int lives;
    private History history;

    private Board() {
    }

    public ImmutableSet<Color> getColorsPool() {
        return colorsPool;
    }

    public History getHistory() {
        return history;
    }

    public final ImmutableCollection<Color> getEnigmaColors() {
        return enigmaColors;
    }

    public final boolean isUnique() {
        return unique;
    }

    public final int getLives() {
        return lives;
    }

    public List<Peg> insertColors(Color... guessColors) {
        final List<Color> colors = asList(guessColors);

        final List<Peg> pegs = calculatePegs(colors);
        history.saveGuessAndPegs(colors, pegs);
        return pegs;
    }

    private List<Peg> calculatePegs(final List<Color> inserts) {
        checkArgument(inserts.size() == enigmaColors.size(), WrongNumberOfInsertsException::new);
        checkUnique(unique, inserts);
        checkInsertsInColorPool(colorsPool, inserts);

        final Peg[] pegs = new Peg[inserts.size()];

        final List<Pair<Color, Boolean>> enigmaColorsUsage = enigmaColors.stream().map(color -> new MutablePair<>(color, false)).collect(toList());

        IntStream.range(0, pegs.length).forEach(
                i -> {
                    if (enigmaColors.asList().get(i) == inserts.get(i)) {
                        pegs[i] = Peg.GREEN;
                        enigmaColorsUsage.get(i).setValue(true);
                    }
                }
        );

        IntStream.range(0, pegs.length)
                .filter(i -> pegs[i] == null)
                .forEach(i -> findIndexOfUnusedEnigmaColor(inserts.get(i), enigmaColorsUsage)
                        .ifPresent(index -> {
                                    pegs[i] = Peg.WHITE;
                                    enigmaColorsUsage.get(index).setValue(true);
                                }
                        ));

        final List<Peg> pegsList = newArrayList(pegs);
        pegsList.removeIf(Objects::isNull);
        shuffle(pegsList);
        return pegsList;
    }


    private OptionalInt findIndexOfUnusedEnigmaColor(Color color, List<Pair<Color, Boolean>> enigmaColorsMarkers) {
        return IntStream.range(0, enigmaColorsMarkers.size())
                .filter(i -> enigmaColorsMarkers.get(i).getKey() == color && !enigmaColorsMarkers.get(i).getValue())
                .findFirst();
    }

    private static void checkInsertsInColorPool(ImmutableSet<Color> colorsPool, Collection<Color> colors) {
        checkArgument(colorsPool.containsAll(colors), InsertsNotInColorPoolException::new);
    }

    private static void checkUnique(boolean unique, Collection<Color> colors) {
        if (unique) {
            checkArgument(colors.size() == Sets.newHashSet(colors).size(), NonUniqueInsertsException::new);
        }
    }


    public static final class BoardBuilder {
        private ImmutableSet<Color> colorsPool = ImmutableSet.of(RED, BLUE, GREEN, WHITE, BLACK, YELLOW);
        private boolean unique = true;
        private int lives = 10;
        private ImmutableCollection<Color> enigmaColors;
        private List<GuessAndPegs> guessesAndPegs;

        private BoardBuilder() {
        }

        public static BoardBuilder aBoard() {
            return new BoardBuilder();
        }

        public BoardBuilder colorsPool(Color... colorsPool) {
            this.colorsPool = ImmutableSet.copyOf(colorsPool);
            return this;
        }

        public BoardBuilder enigmaColors(Color... colors) {
            this.enigmaColors = ImmutableList.copyOf(colors);
            return this;
        }

        public BoardBuilder unique(boolean unique) {
            this.unique = unique;
            return this;
        }

        public BoardBuilder lives(int lives) {
            this.lives = lives;
            return this;
        }

        public BoardBuilder history(List<GuessAndPegs> guessesAndPegs) {
            this.guessesAndPegs = guessesAndPegs;
            return this;
        }

        public Board build() {
            Preconditions.checkNotNull(enigmaColors);

            final Board board = new Board();
            board.unique = this.unique;
            board.enigmaColors = this.enigmaColors;
            board.colorsPool = this.colorsPool;
            board.lives = this.lives;

            if (CollectionUtils.isEmpty(guessesAndPegs)) {
                board.history = new History(lives);
            } else {
                board.history = new History(lives, guessesAndPegs);
            }

            checkInsertsInColorPool(colorsPool, enigmaColors);
            checkUnique(unique, enigmaColors);

            return board;
        }
    }
}
