package com.github.ptomaszek.mastermind;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Table;
import com.google.common.collect.TreeBasedTable;

import java.util.List;

class History {
    private static final String GUESSES = "GUESSES";
    private static final String PEGS = "PEGS";

    private final Table<Integer, String, GuessAndPegs> historyTable = TreeBasedTable.create();
    private int lives;

    History(int lives) {
        this.lives = lives;
        Preconditions.checkArgument(lives > 0, "Lives number must be positive");
    }

    public History(Integer lives, List<GuessAndPegs> guessesAndPegs) {
        Preconditions.checkArgument(guessesAndPegs.size() <= lives);

        guessesAndPegs.forEach(guessAndPegs -> saveGuessAndPegs(guessAndPegs.getColors(), guessAndPegs.getPegs()));
    }

    void saveGuessAndPegs(List<Color> colors, List<Peg> pegs) {
        final int guessNo = historyTable.size() + 1;
        Preconditions.checkArgument(guessNo <= lives, "No more lives");

        historyTable.put(guessNo, GUESSES, new GuessAndPegs(colors, pegs));
    }

    public Table<Integer, String, GuessAndPegs> getHistoryTable() {
        return ImmutableTable.copyOf(historyTable);
    }
}
