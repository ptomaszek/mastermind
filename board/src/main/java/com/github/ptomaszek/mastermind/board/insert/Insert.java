package com.github.ptomaszek.mastermind.board.insert;

import com.google.common.collect.ImmutableList;

public abstract class Insert {
    private final ImmutableList<Color> colors;

    Insert(Color... colors) {
        this(ImmutableList.copyOf(colors));
    }

    Insert(ImmutableList<Color> colors) {
        this.colors = colors;
    }

    public ImmutableList<Color> colors() {
        return colors;
    }
}
