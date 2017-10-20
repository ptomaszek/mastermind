package com.github.ptomaszek.mastermind.board.insert;

import com.google.common.collect.ImmutableList;

public abstract class Insert {
    private final ImmutableList<Color> colors;

    public Insert(Color... colors) {
        this.colors = ImmutableList.copyOf(colors);
    }

    public ImmutableList<Color> colors() {
        return colors;
    }
}
