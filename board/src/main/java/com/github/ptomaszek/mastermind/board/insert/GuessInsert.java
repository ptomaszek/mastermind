package com.github.ptomaszek.mastermind.board.insert;

import com.google.common.collect.ImmutableList;

public class GuessInsert extends Insert {
    public GuessInsert(Color... colors) {
        super(colors);
    }

    public GuessInsert(ImmutableList<Color> colors) {
        super(colors);
    }
}
