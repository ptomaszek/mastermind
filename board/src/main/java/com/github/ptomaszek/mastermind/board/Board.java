package com.github.ptomaszek.mastermind.board;

import com.github.ptomaszek.mastermind.board.exception.ColorsNotInColorPoolException;
import com.github.ptomaszek.mastermind.board.exception.EnigmaNotSetException;
import com.github.ptomaszek.mastermind.board.exception.GameEndedException;
import com.github.ptomaszek.mastermind.board.exception.NonUniqueColorsException;
import com.github.ptomaszek.mastermind.board.insert.Color;
import com.github.ptomaszek.mastermind.board.insert.EnigmaInsert;
import com.github.ptomaszek.mastermind.board.insert.GuessInsert;
import com.github.ptomaszek.mastermind.board.insert.Insert;
import com.github.ptomaszek.mastermind.board.insert.InsertAndPegs;
import com.github.ptomaszek.mastermind.board.insert.Peg;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.Optional;

import static com.github.ptomaszek.mastermind.board.GameEndedStatus.PLAYER_LOST;
import static com.github.ptomaszek.mastermind.board.GameEndedStatus.PLAYER_WON;
import static com.github.ptomaszek.mastermind.board.insert.Color.BLUE;
import static com.github.ptomaszek.mastermind.board.insert.Color.GREEN;
import static com.github.ptomaszek.mastermind.board.insert.Color.ORANGE;
import static com.github.ptomaszek.mastermind.board.insert.Color.RED;
import static com.github.ptomaszek.mastermind.board.insert.Color.WHITE;
import static com.github.ptomaszek.mastermind.board.insert.Color.YELLOW;
import static com.github.ptomaszek.mastermind.board.util.Preconditions.checkArgument;
import static com.github.ptomaszek.mastermind.board.util.Preconditions.checkNotNull;
import static java.util.stream.Collectors.toList;

public class Board {

    private ImmutableSet<Color> colorsPool;
    private EnigmaInsert enigmaInsert;
    private boolean unique;
    private int lives;
    private History history;
    private PegsCalculator pegsCalculator = new PegsCalculator();

    private Board() {
    }

    public ImmutableSet<Color> getColorsPool() {
        return colorsPool;
    }

    public History getHistory() {
        return history;
    }

    public List<Peg> insertColors(List<Color> colors) {
        gameEndedStatus().ifPresent(status -> {
            throw new GameEndedException(status);
        });

        final GuessInsert guessInsert = new GuessInsert(ImmutableList.copyOf(colors));
        checkInsertHasUniqueColors(unique, guessInsert);
        checkInsertInColorPool(colorsPool, guessInsert);

        final List<Peg> pegs = pegsCalculator.calculatePegs(enigmaInsert, guessInsert);
        history.saveGuessInsertAndPegs(guessInsert, pegs);
        return pegs;
    }

    public List<Peg> insertColors(Color... colors) {
        return insertColors(ImmutableList.copyOf(colors));
    }

    public Optional<GameEndedStatus> gameEndedStatus() {
        if (history.getHistoryList().size() == lives) {
            return Optional.of(PLAYER_LOST);
        }

        if (history.getLast().isPresent() && history.getLast().get().getPegs().stream().filter(peg -> peg == Peg.GREEN).count() == enigmaInsert.colors().size()) {
            return Optional.of(PLAYER_WON);
        }

        return Optional.empty();
    }

    private static void checkInsertInColorPool(ImmutableSet<Color> colorsPool, Insert insert) {
        checkArgument(colorsPool.containsAll(insert.colors()), ColorsNotInColorPoolException::new);
    }

    private static void checkInsertHasUniqueColors(boolean unique, Insert insert) {
        if (unique) {
            checkArgument(insert.colors().size() == Sets.newHashSet(insert.colors()).size(), NonUniqueColorsException::new);
        }
    }

    public int livesLeft() {
        return lives - history.getHistoryList().size();
    }


    public static final class BoardBuilder {
        private ImmutableSet<Color> colorsPool = ImmutableSet.of(RED, BLUE, GREEN, WHITE, ORANGE, YELLOW);
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
            checkNotNull(enigmaInsert, EnigmaNotSetException::new);
            Preconditions.checkArgument(lives > 0, "Lives number must be positive");

            final Board board = new Board();
            board.unique = this.unique;
            board.enigmaInsert = this.enigmaInsert;
            board.colorsPool = this.colorsPool;
            board.lives = this.lives;

            if (CollectionUtils.isEmpty(historicalInserts)) {
                board.history = new History();
            } else {
                final List<InsertAndPegs> historicalInsertsAndPegs = historicalInserts.stream()
                        .map(insert -> new InsertAndPegs(insert, board.pegsCalculator.calculatePegs(enigmaInsert, insert)))
                        .collect(toList());
                board.history = new History(historicalInsertsAndPegs);
            }

            checkInsertInColorPool(colorsPool, enigmaInsert);
            checkInsertHasUniqueColors(unique, enigmaInsert);

            return board;
        }
    }
}
