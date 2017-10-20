package com.github.ptomaszek.mastermind.board.exception;

public class ColorsNotInColorPoolException extends MastermindException {

    public ColorsNotInColorPoolException() {
        super("Chosen colors must belong to the color pool");
    }
}
