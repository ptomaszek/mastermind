package com.github.ptomaszek.mastermind.exception;

public class InsertsNotInColorPoolException extends MastermindException {

    public InsertsNotInColorPoolException() {
        super("Chosen colors must belong to the color pool");
    }
}
