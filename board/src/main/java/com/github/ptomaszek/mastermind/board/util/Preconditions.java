package com.github.ptomaszek.mastermind.board.util;

import com.github.ptomaszek.mastermind.board.exception.MastermindException;

import java.util.function.Supplier;

public class Preconditions {

    private Preconditions() {
    }

    public static void checkArgument(boolean expression, Supplier<? extends MastermindException> exceptionSupplier) {
        if (!expression) {
            throw exceptionSupplier.get();
        }
    }

    public static void checkNotNull(Object object, Supplier<? extends MastermindException> exceptionSupplier) {
        if (object == null) {
            throw exceptionSupplier.get();
        }
    }
}
