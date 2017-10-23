package com.github.ptomaszek.mastermind.board.history;

import com.github.ptomaszek.mastermind.board.feedback.Feedback;
import com.github.ptomaszek.mastermind.board.insert.Guess;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class History {
    private final List<HistoricalEntry> historyList = new ArrayList<>();

    public History() {
    }

    public History(List<HistoricalEntry> historicalInsertsAndPegs) {
        historyList.addAll(historicalInsertsAndPegs);
    }

    public void save(Guess guess, Feedback feedback) {
        historyList.add(HistoricalEntry.of(guess, feedback));
    }

    public ImmutableList<HistoricalEntry> getHistoryList() {
        return ImmutableList.copyOf(historyList);
    }

    public Optional<HistoricalEntry> getLast() {
        return Optional.ofNullable(Iterables.getLast(historyList, null));
    }
}
