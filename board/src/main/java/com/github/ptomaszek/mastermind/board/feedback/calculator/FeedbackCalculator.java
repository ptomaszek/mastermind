package com.github.ptomaszek.mastermind.board.feedback.calculator;

import com.github.ptomaszek.mastermind.board.feedback.Feedback;
import com.github.ptomaszek.mastermind.board.insert.Enigma;
import com.github.ptomaszek.mastermind.board.insert.Guess;

public abstract class FeedbackCalculator {

    public abstract Feedback calculateFeedback(final Enigma enigma, Guess insert);
}
