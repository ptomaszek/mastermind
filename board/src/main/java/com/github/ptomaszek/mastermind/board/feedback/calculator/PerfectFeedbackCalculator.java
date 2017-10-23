package com.github.ptomaszek.mastermind.board.feedback.calculator;

import com.github.ptomaszek.mastermind.board.exception.WrongNumberOfInsertsException;
import com.github.ptomaszek.mastermind.board.feedback.Feedback;
import com.github.ptomaszek.mastermind.board.feedback.Peg;
import com.github.ptomaszek.mastermind.board.insert.Color;
import com.github.ptomaszek.mastermind.board.insert.Enigma;
import com.github.ptomaszek.mastermind.board.insert.Guess;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.OptionalInt;
import java.util.stream.IntStream;

import static com.github.ptomaszek.mastermind.board.util.Preconditions.checkArgument;
import static java.util.stream.Collectors.toList;

public class PerfectFeedbackCalculator extends FeedbackCalculator {

    public Feedback calculateFeedback(final Enigma enigma, Guess insert) {
        checkArgument(insert.colors().size() == enigma.colors().size(), WrongNumberOfInsertsException::new);

        final Peg[] pegs = new Peg[insert.colors().size()];

        final List<Pair<Color, Boolean>> enigmaColorsUsage = enigma.colors().stream().map(color -> new MutablePair<>(color, false)).collect(toList());

        IntStream.range(0, pegs.length).forEach(
                i -> {
                    if (enigma.colors().get(i) == insert.colors().get(i)) {
                        pegs[i] = Peg.GREEN;
                        enigmaColorsUsage.get(i).setValue(true);
                    }
                }
        );

        IntStream.range(0, pegs.length)
                .filter(i -> pegs[i] == null)
                .forEach(i -> findIndexOfUnusedEnigmaColor(insert.colors().get(i), enigmaColorsUsage)
                        .ifPresent(index -> {
                                    pegs[i] = Peg.WHITE;
                                    enigmaColorsUsage.get(index).setValue(true);
                                }
                        ));

        return Feedback.of(pegs);
    }


    private static OptionalInt findIndexOfUnusedEnigmaColor(Color color, List<Pair<Color, Boolean>> enigmaColorsMarkers) {
        return IntStream.range(0, enigmaColorsMarkers.size())
                .filter(i -> enigmaColorsMarkers.get(i).getKey() == color && !enigmaColorsMarkers.get(i).getValue())
                .findFirst();
    }
}
