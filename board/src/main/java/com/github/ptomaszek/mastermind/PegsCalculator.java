package com.github.ptomaszek.mastermind;

import com.github.ptomaszek.mastermind.exception.WrongNumberOfInsertsException;
import com.github.ptomaszek.mastermind.insert.Color;
import com.github.ptomaszek.mastermind.insert.EnigmaInsert;
import com.github.ptomaszek.mastermind.insert.GuessInsert;
import com.github.ptomaszek.mastermind.insert.Peg;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Objects;
import java.util.OptionalInt;
import java.util.stream.IntStream;

import static com.github.ptomaszek.mastermind.util.Preconditions.checkArgument;
import static com.google.common.collect.Lists.newArrayList;
import static java.util.stream.Collectors.toList;

public class PegsCalculator {

    List<Peg> calculatePegs(final EnigmaInsert enigma, GuessInsert insert) {
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

        final List<Peg> pegsList = newArrayList(pegs);
        pegsList.removeIf(Objects::isNull);
        return pegsList;
    }


    private static OptionalInt findIndexOfUnusedEnigmaColor(Color color, List<Pair<Color, Boolean>> enigmaColorsMarkers) {
        return IntStream.range(0, enigmaColorsMarkers.size())
                .filter(i -> enigmaColorsMarkers.get(i).getKey() == color && !enigmaColorsMarkers.get(i).getValue())
                .findFirst();
    }
}
