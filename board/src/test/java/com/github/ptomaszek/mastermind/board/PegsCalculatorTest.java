package com.github.ptomaszek.mastermind.board;

import com.github.ptomaszek.mastermind.board.insert.EnigmaInsert;
import com.github.ptomaszek.mastermind.board.insert.GuessInsert;
import com.github.ptomaszek.mastermind.board.insert.Peg;
import com.github.ptomaszek.mastermind.board.test.asserter.PegsAssert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static com.github.ptomaszek.mastermind.board.insert.Color.BLACK;
import static com.github.ptomaszek.mastermind.board.insert.Color.BLUE;
import static com.github.ptomaszek.mastermind.board.insert.Color.GREEN;
import static com.github.ptomaszek.mastermind.board.insert.Color.RED;
import static com.github.ptomaszek.mastermind.board.insert.Color.WHITE;
import static com.github.ptomaszek.mastermind.board.insert.Color.YELLOW;

class PegsCalculatorTest {

    final PegsCalculator pegsCalculator = new PegsCalculator();

    @ParameterizedTest
    @MethodSource("insertsAndExpectedPegsCount")
    void shouldCalculatePegsForInserts(EnigmaInsert enigma, GuessInsert guess, int greenPegsCount, int whitePegsCount) {
        //given

        //when
        final List<Peg> pegs = pegsCalculator.calculatePegs(enigma, guess);

        //then
        PegsAssert.assertThat(pegs).containsGreenPegs(greenPegsCount);
        PegsAssert.assertThat(pegs).containsWhitePegs(whitePegsCount);
    }

    private static Stream<Arguments> insertsAndExpectedPegsCount() {
        final EnigmaInsert enigma = new EnigmaInsert(RED, BLUE, GREEN, WHITE);
        final EnigmaInsert enigma2 = new EnigmaInsert(RED, RED, GREEN, WHITE);

        return Stream.of(
                Arguments.of(enigma, new GuessInsert(RED, BLUE, GREEN, WHITE), 4, 0),
                Arguments.of(enigma, new GuessInsert(BLUE, RED, GREEN, WHITE), 2, 2),
                Arguments.of(enigma, new GuessInsert(BLACK, BLUE, GREEN, WHITE), 3, 0),
                Arguments.of(enigma, new GuessInsert(BLACK, GREEN, BLUE, YELLOW), 0, 2),

                Arguments.of(enigma2, new GuessInsert(RED, RED, GREEN, WHITE), 4, 0),
                Arguments.of(enigma2, new GuessInsert(GREEN, WHITE, RED, RED), 0, 4),
                Arguments.of(enigma2, new GuessInsert(RED, RED, RED, RED), 2, 0),
                Arguments.of(enigma2, new GuessInsert(WHITE, WHITE, WHITE, GREEN), 0, 2),
                Arguments.of(enigma2, new GuessInsert(WHITE, WHITE, WHITE, BLACK), 0, 1),
                Arguments.of(enigma2, new GuessInsert(WHITE, WHITE, WHITE, WHITE), 1, 0),
                Arguments.of(enigma2, new GuessInsert(BLUE, RED, GREEN, WHITE), 3, 0),
                Arguments.of(enigma2, new GuessInsert(BLACK, BLUE, GREEN, WHITE), 2, 0),
                Arguments.of(enigma2, new GuessInsert(BLACK, GREEN, BLUE, YELLOW), 0, 1),
                Arguments.of(enigma2, new GuessInsert(BLACK, BLACK, RED, YELLOW), 0, 1),
                Arguments.of(enigma2, new GuessInsert(BLACK, BLACK, BLACK, YELLOW), 0, 0)
        );
    }
}
