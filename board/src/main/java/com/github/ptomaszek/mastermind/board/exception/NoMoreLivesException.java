package com.github.ptomaszek.mastermind.board.exception;

public class NoMoreLivesException extends MastermindException{

    public NoMoreLivesException() {
        super("No more lives");
    }
}
