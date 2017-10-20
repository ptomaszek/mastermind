package com.github.ptomaszek.mastermind.board.test.asserter;

import com.github.ptomaszek.mastermind.board.insert.Peg;
import org.assertj.core.api.ListAssert;

import java.util.List;


public class PegsAssert extends ListAssert<Peg> {

    private PegsAssert(List<? extends Peg> actual) {
        super(actual);
    }

    public static PegsAssert assertThat(List<Peg> actual) {
        return new PegsAssert(actual);
    }

    public void containsGreenPegs(int expectedNumberOfPegs) {
        final long actualNumberOfPegs = actual.stream().filter(p -> p == Peg.GREEN).count();
        if (expectedNumberOfPegs != actualNumberOfPegs) {
            failWithMessage("Expected <%s> green pegs but found <%s>", expectedNumberOfPegs, actualNumberOfPegs);
        }
    }

    public void containsWhitePegs(int expectedNumberOfPegs) {
        final long actualNumberOfPegs = actual.stream().filter(p -> p == Peg.WHITE).count();
        if (expectedNumberOfPegs != actualNumberOfPegs) {
            failWithMessage("Expected <%s> white pegs but found <%s>", expectedNumberOfPegs, actualNumberOfPegs);
        }
    }

    public void doesNotContainGreenPegs() {
        containsGreenPegs(0);
    }

    public void doesNotContainWhitePegs() {
        containsWhitePegs(0);
    }
}