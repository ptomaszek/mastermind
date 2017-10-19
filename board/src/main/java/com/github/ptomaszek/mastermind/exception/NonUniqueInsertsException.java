package com.github.ptomaszek.mastermind.exception;

public class NonUniqueInsertsException extends MastermindException{
    public NonUniqueInsertsException() {
        super("Inserts (colors) must be unique");
    }
}
