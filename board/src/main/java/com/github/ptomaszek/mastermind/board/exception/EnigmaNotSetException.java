package com.github.ptomaszek.mastermind.board.exception;

public class EnigmaNotSetException extends MastermindException{

    public EnigmaNotSetException() {
        super("Enigma colors must be set");
    }
}
