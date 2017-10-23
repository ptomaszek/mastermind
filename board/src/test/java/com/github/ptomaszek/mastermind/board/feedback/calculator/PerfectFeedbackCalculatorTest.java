package com.github.ptomaszek.mastermind.board.feedback.calculator;

import com.github.ptomaszek.mastermind.board.feedback.Feedback;
import com.github.ptomaszek.mastermind.board.insert.Color;
import com.github.ptomaszek.mastermind.board.insert.Enigma;
import com.github.ptomaszek.mastermind.board.insert.Guess;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class PerfectFeedbackCalculatorTest {

    private final FeedbackCalculator feedbackCalculator = new PerfectFeedbackCalculator();

    @ParameterizedTest
    @MethodSource("insertsAndExpectedPegsCount")
    void shouldCalculatePegsForInserts(Enigma enigma, Guess guess, int expectedGreenPegsCount, int expectedWhitePegsCount) {
        //given

        //when
        final Feedback feedback = feedbackCalculator.calculateFeedback(enigma, guess);

        //then
        Assertions.assertThat(feedback.greenPegsCount()).isEqualTo(expectedGreenPegsCount);
        Assertions.assertThat(feedback.whitePegsCount()).isEqualTo(expectedWhitePegsCount);
    }

    private static Stream<Arguments> insertsAndExpectedPegsCount() {
        final Enigma enigma = new Enigma(Color.RED, Color.BLUE, Color.GREEN, Color.WHITE);
        final Enigma enigma2 = new Enigma(Color.RED, Color.RED, Color.GREEN, Color.WHITE);

        return Stream.of(
                Arguments.of(enigma, new Guess(Color.RED, Color.BLUE, Color.GREEN, Color.WHITE), 4, 0),
                Arguments.of(enigma, new Guess(Color.BLUE, Color.RED, Color.GREEN, Color.WHITE), 2, 2),
                Arguments.of(enigma, new Guess(Color.ORANGE, Color.BLUE, Color.GREEN, Color.WHITE), 3, 0),
                Arguments.of(enigma, new Guess(Color.ORANGE, Color.GREEN, Color.BLUE, Color.YELLOW), 0, 2),

                Arguments.of(enigma2, new Guess(Color.RED, Color.RED, Color.GREEN, Color.WHITE), 4, 0),
                Arguments.of(enigma2, new Guess(Color.GREEN, Color.WHITE, Color.RED, Color.RED), 0, 4),
                Arguments.of(enigma2, new Guess(Color.RED, Color.RED, Color.RED, Color.RED), 2, 0),
                Arguments.of(enigma2, new Guess(Color.WHITE, Color.WHITE, Color.WHITE, Color.GREEN), 0, 2),
                Arguments.of(enigma2, new Guess(Color.WHITE, Color.WHITE, Color.WHITE, Color.ORANGE), 0, 1),
                Arguments.of(enigma2, new Guess(Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE), 1, 0),
                Arguments.of(enigma2, new Guess(Color.BLUE, Color.RED, Color.GREEN, Color.WHITE), 3, 0),
                Arguments.of(enigma2, new Guess(Color.ORANGE, Color.BLUE, Color.GREEN, Color.WHITE), 2, 0),
                Arguments.of(enigma2, new Guess(Color.ORANGE, Color.GREEN, Color.BLUE, Color.YELLOW), 0, 1),
                Arguments.of(enigma2, new Guess(Color.ORANGE, Color.ORANGE, Color.RED, Color.YELLOW), 0, 1),
                Arguments.of(enigma2, new Guess(Color.ORANGE, Color.ORANGE, Color.ORANGE, Color.YELLOW), 0, 0)
        );
    }
}
