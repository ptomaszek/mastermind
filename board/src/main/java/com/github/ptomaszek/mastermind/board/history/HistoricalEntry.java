package com.github.ptomaszek.mastermind.board.history;

import com.github.ptomaszek.mastermind.board.feedback.Feedback;
import com.github.ptomaszek.mastermind.board.insert.Guess;

public final class HistoricalEntry {

    private final Guess guess;
    private final Feedback feedback;

    private HistoricalEntry(Guess guess, Feedback feedback) {
        this.guess = guess;
        this.feedback = feedback;
    }

    public static HistoricalEntry of(Guess guess, Feedback feedback) {
        return new HistoricalEntry(guess, feedback);
    }

    public Guess getGuess() {
        return guess;
    }

    public Feedback getFeedback() {
        return feedback;
    }
}
