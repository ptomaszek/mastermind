package com.github.ptomaszek.mastermind;

import com.google.common.collect.ImmutableList;

import java.util.List;

public class GuessAndPegs {

    private final ImmutableList<Color> colors;
    private final ImmutableList<Peg> pegs;

    public GuessAndPegs(List<Color> colors, List<Peg> pegs) {
        this.colors = ImmutableList.copyOf(colors);
        this.pegs = ImmutableList.copyOf(pegs);
    }

    public ImmutableList<Color> getColors() {
        return colors;
    }

    public ImmutableList<Peg> getPegs() {
        return pegs;
    }
}
