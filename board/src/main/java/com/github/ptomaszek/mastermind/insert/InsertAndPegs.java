package com.github.ptomaszek.mastermind.insert;

import com.google.common.collect.ImmutableList;

import java.util.List;

public final class InsertAndPegs {

    private final Insert insert;
    private final ImmutableList<Peg> pegs;

    public InsertAndPegs(Insert insert, List<Peg> pegs) {
        this.insert = insert;
        this.pegs = ImmutableList.copyOf(pegs);
    }

    public Insert getInsert() {
        return insert;
    }

    public ImmutableList<Peg> getPegs() {
        return pegs;
    }
}
