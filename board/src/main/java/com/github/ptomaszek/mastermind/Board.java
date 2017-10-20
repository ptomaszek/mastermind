package com.github.ptomaszek.mastermind;

import com.github.ptomaszek.mastermind.exception.ColorsNotInColorPoolException;
import com.github.ptomaszek.mastermind.exception.NonUniqueColorsException;
import com.github.ptomaszek.mastermind.insert.Color;
import com.github.ptomaszek.mastermind.insert.EnigmaInsert;
import com.github.ptomaszek.mastermind.insert.GuessInsert;
import com.github.ptomaszek.mastermind.insert.Insert;
import com.github.ptomaszek.mastermind.insert.InsertAndPegs;
import com.github.ptomaszek.mastermind.insert.Peg;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

import static com.github.ptomaszek.mastermind.insert.Color.BLACK;
import static com.github.ptomaszek.mastermind.insert.Color.BLUE;
import static com.github.ptomaszek.mastermind.insert.Color.GREEN;
import static com.github.ptomaszek.mastermind.insert.Color.RED;
import static com.github.ptomaszek.mastermind.insert.Color.WHITE;
import static com.github.ptomaszek.mastermind.insert.Color.YELLOW;
import static com.github.ptomaszek.mastermind.util.Preconditions.checkArgument;
import static java.util.stream.Collectors.toList;

public class Board {

    private ImmutableSet<Color> colorsPool;
    private EnigmaInsert enigmaInsert;
    private boolean unique;
    private int lives;
    private History history;
    private PegsCalculator pegsCalculator;

    private Board() {
    }

    public ImmutableSet<Color> getColorsPool() {
        return colorsPool;
    }

    public History getHistory() {
        return history;
    }

    public final EnigmaInsert getEnigmaInsert() {
        return enigmaInsert;
    }

    public final boolean isUnique() {
        return unique;
    }

    public final int getLives() {
        return lives;
    }

    public List<Peg> insertColors(Color... colors) {
        final GuessInsert guessInsert = new GuessInsert(colors);

        checkInsertHasUniqueColors(unique, guessInsert);
        checkInsertInColorPool(colorsPool, guessInsert);

        final List<Peg> pegs = pegsCalculator.calculatePegs(enigmaInsert, guessInsert);
        history.saveGuessInsertAndPegs(guessInsert, pegs);
        return pegs;
    }


    private static void checkInsertInColorPool(ImmutableSet<Color> colorsPool, Insert insert) {
        checkArgument(colorsPool.containsAll(insert.colors()), ColorsNotInColorPoolException::new);
    }

    private static void checkInsertHasUniqueColors(boolean unique, Insert insert) {
        if (unique) {
            checkArgument(insert.colors().size() == Sets.newHashSet(insert.colors()).size(), NonUniqueColorsException::new);
        }
    }

    public static final class BoardBuilder {
        private ImmutableSet<Color> colorsPool = ImmutableSet.of(RED, BLUE, GREEN, WHITE, BLACK, YELLOW);
        private boolean unique = true;
        private int lives = 10;
        private EnigmaInsert enigmaInsert;
        private ImmutableList<GuessInsert> historicalInserts;

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
            this.enigmaInsert = new EnigmaInsert(colors);
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

        public BoardBuilder history(ImmutableList<GuessInsert> inserts) {
            this.historicalInserts = inserts;
            return this;
        }

        public Board build() {
            Preconditions.checkNotNull(enigmaInsert);

            final Board board = new Board();
            board.pegsCalculator = new PegsCalculator();
            board.unique = this.unique;
            board.enigmaInsert = this.enigmaInsert;
            board.colorsPool = this.colorsPool;
            board.lives = this.lives;

            if (CollectionUtils.isEmpty(historicalInserts)) {
                board.history = new History(lives);
            } else {
                final List<InsertAndPegs> historicalInsertsAndPegs = historicalInserts.stream()
                        .map(insert -> new InsertAndPegs(insert, board.pegsCalculator.calculatePegs(enigmaInsert, insert)))
                        .collect(toList());
                board.history = new History(lives, historicalInsertsAndPegs);
            }

            checkInsertInColorPool(colorsPool, enigmaInsert);
            checkInsertHasUniqueColors(unique, enigmaInsert);

            return board;
        }
    }
}
