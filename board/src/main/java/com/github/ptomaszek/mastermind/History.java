package com.github.ptomaszek.mastermind;

import com.github.ptomaszek.mastermind.insert.Insert;
import com.github.ptomaszek.mastermind.insert.InsertAndPegs;
import com.github.ptomaszek.mastermind.insert.Peg;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Table;
import com.google.common.collect.TreeBasedTable;

import java.util.List;

class History {
    private static final String GUESSES = "GUESSES";

    private final Table<Integer, String, InsertAndPegs> historyTable = TreeBasedTable.create();
    private int lives;

    History(int lives) {
        this.lives = lives;
        Preconditions.checkArgument(lives > 0, "Lives number must be positive");
    }

    public History(Integer lives, List<InsertAndPegs> guessesAndPegs) {
        Preconditions.checkArgument(guessesAndPegs.size() <= lives);

        guessesAndPegs.forEach(insertAndPegs -> saveGuessInsertAndPegs(insertAndPegs.getInsert(), insertAndPegs.getPegs()));
    }

    void saveGuessInsertAndPegs(Insert insert, List<Peg> pegs) {
        final int guessNo = historyTable.size() + 1;
        Preconditions.checkArgument(guessNo <= lives, "No more lives");

        historyTable.put(guessNo, GUESSES, new InsertAndPegs(insert, pegs));
    }

    public Table<Integer, String, InsertAndPegs> getHistoryTable() {
        return ImmutableTable.copyOf(historyTable);
    }
}
