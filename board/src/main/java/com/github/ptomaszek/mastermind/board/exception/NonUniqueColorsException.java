package com.github.ptomaszek.mastermind.board.exception;

public class NonUniqueColorsException extends MastermindException{
    public NonUniqueColorsException() {
        super("Colors must be unique");
    }
}
