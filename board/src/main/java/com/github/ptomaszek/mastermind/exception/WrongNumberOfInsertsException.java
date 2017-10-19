package com.github.ptomaszek.mastermind.exception;

public class WrongNumberOfInsertsException extends MastermindException {
    public WrongNumberOfInsertsException() {
        super("Wrong number of inserts");
    }
}
