package com.github.ptomaszek.mastermind.board;

import com.github.ptomaszek.mastermind.board.insert.Insert;
import com.github.ptomaszek.mastermind.board.insert.InsertAndPegs;
import com.github.ptomaszek.mastermind.board.insert.Peg;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class History {
    private static final String GUESSES = "GUESSES";

    private final List<InsertAndPegs> historyList = new ArrayList<>();

    History() {

    }

    public History(List<InsertAndPegs> guessesAndPegs) {
        guessesAndPegs.forEach(insertAndPegs -> saveGuessInsertAndPegs(insertAndPegs.getInsert(), insertAndPegs.getPegs()));
    }

    void saveGuessInsertAndPegs(Insert insert, List<Peg> pegs) {
        historyList.add(new InsertAndPegs(insert, pegs));
    }

    public ImmutableList<InsertAndPegs> getHistoryList() {
        return ImmutableList.copyOf(historyList);
    }

    public Optional<InsertAndPegs> getLast() {
        return Optional.ofNullable(Iterables.getLast(historyList, null));
    }
}
