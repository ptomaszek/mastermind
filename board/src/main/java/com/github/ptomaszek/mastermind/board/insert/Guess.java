package com.github.ptomaszek.mastermind.board.insert;

import com.google.common.collect.ImmutableList;

public class Guess extends Insert {
    public Guess(Color... colors) {
        super(colors);
    }

    public Guess(ImmutableList<Color> colors) {
        super(colors);
    }
}
