package com.github.ptomaszek.mastermind.exception;

import static java.lang.String.format;

public class MastermindException extends RuntimeException {
    public MastermindException(String errorMessageTemplate, Object... errorMessageParams) {
        super(format(errorMessageTemplate, errorMessageParams));
    }
}
