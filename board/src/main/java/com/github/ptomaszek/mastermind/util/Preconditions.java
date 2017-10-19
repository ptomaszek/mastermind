package com.github.ptomaszek.mastermind.util;

import com.github.ptomaszek.mastermind.exception.MastermindException;

import java.util.function.Supplier;

public class Preconditions {

    private Preconditions(){
    }

    public static void checkArgument(boolean expression, Supplier<? extends MastermindException> exceptionSupplier) {
        if (!expression) {
            throw exceptionSupplier.get();
        }
    }
}
