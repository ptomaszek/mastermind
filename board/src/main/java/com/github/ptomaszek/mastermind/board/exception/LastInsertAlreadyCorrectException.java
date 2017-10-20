package com.github.ptomaszek.mastermind.board.exception;

public class LastInsertAlreadyCorrectException extends MastermindException{

    public LastInsertAlreadyCorrectException() {
        super("Last insert was already correct");
    }
}
