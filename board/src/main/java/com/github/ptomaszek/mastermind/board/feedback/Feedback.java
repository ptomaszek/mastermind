package com.github.ptomaszek.mastermind.board.feedback;

import com.google.common.collect.ImmutableList;

import java.util.List;
import java.util.Objects;

import static com.google.common.collect.Lists.newArrayList;

public class Feedback {
    private final ImmutableList<Peg> pegs;

    private Feedback(ImmutableList<Peg> pegs) {
        this.pegs = pegs;
    }

    public static Feedback of(Peg[] pegs) {
        final List<Peg> pegsList = newArrayList(pegs);
        pegsList.removeIf(Objects::isNull);
        return new Feedback(ImmutableList.copyOf(pegs));
    }

    public Integer greenPegsCount() {
        return Math.toIntExact(pegs.stream().filter(peg -> peg == Peg.GREEN).count());
    }

    public Integer whitePegsCount() {
        return Math.toIntExact(pegs.stream().filter(peg -> peg == Peg.WHITE).count());
    }
}
