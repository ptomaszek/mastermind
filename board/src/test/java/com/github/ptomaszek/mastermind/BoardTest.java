package com.github.ptomaszek.mastermind;

import com.github.ptomaszek.mastermind.exception.ColorsNotInColorPoolException;
import com.github.ptomaszek.mastermind.exception.MastermindException;
import com.github.ptomaszek.mastermind.exception.NonUniqueColorsException;
import com.github.ptomaszek.mastermind.exception.WrongNumberOfInsertsException;
import com.github.ptomaszek.mastermind.insert.Color;
import com.github.ptomaszek.mastermind.insert.Peg;
import com.github.ptomaszek.mastermind.test.asserter.PegsAssert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static com.github.ptomaszek.mastermind.Board.BoardBuilder.aBoard;
import static com.github.ptomaszek.mastermind.insert.Color.BLACK;
import static com.github.ptomaszek.mastermind.insert.Color.BLUE;
import static com.github.ptomaszek.mastermind.insert.Color.GREEN;
import static com.github.ptomaszek.mastermind.insert.Color.RED;
import static com.github.ptomaszek.mastermind.insert.Color.WHITE;
import static com.github.ptomaszek.mastermind.insert.Color.YELLOW;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.util.Arrays.array;

class BoardTest {

    @ParameterizedTest
    @MethodSource("insertsAndExpectedPegsCount")
    void shouldCalculatePegsForInserts(Board board, Color[] inserts, int greenPegsCount, int whitePegsCount) {
        //given

        //when
        final List<Peg> pegs = board.insertColors(inserts);

        //then
        PegsAssert.assertThat(pegs).containsGreenPegs(greenPegsCount);
        PegsAssert.assertThat(pegs).containsWhitePegs(whitePegsCount);
    }

    private static Stream<Arguments> insertsAndExpectedPegsCount() {
        final Board boardWithUniqueInserts = aBoard().enigmaColors(RED, BLUE, GREEN, WHITE).build();
        final Board boardWithNonUniqueInserts = aBoard().enigmaColors(RED, RED, GREEN, WHITE).unique(false).lives(15).build();

        return Stream.of(
                Arguments.of(boardWithUniqueInserts, array(RED, BLUE, GREEN, WHITE), 4, 0),
                Arguments.of(boardWithUniqueInserts, array(BLUE, RED, GREEN, WHITE), 2, 2),
                Arguments.of(boardWithUniqueInserts, array(BLACK, BLUE, GREEN, WHITE), 3, 0),
                Arguments.of(boardWithUniqueInserts, array(BLACK, GREEN, BLUE, YELLOW), 0, 2),

                Arguments.of(boardWithNonUniqueInserts, array(RED, RED, GREEN, WHITE), 4, 0),
                Arguments.of(boardWithNonUniqueInserts, array(GREEN, WHITE, RED, RED), 0, 4),
                Arguments.of(boardWithNonUniqueInserts, array(RED, RED, RED, RED), 2, 0),
                Arguments.of(boardWithNonUniqueInserts, array(WHITE, WHITE, WHITE, GREEN), 0, 2),
                Arguments.of(boardWithNonUniqueInserts, array(WHITE, WHITE, WHITE, BLACK), 0, 1),
                Arguments.of(boardWithNonUniqueInserts, array(WHITE, WHITE, WHITE, WHITE), 1, 0),
                Arguments.of(boardWithNonUniqueInserts, array(BLUE, RED, GREEN, WHITE), 3, 0),
                Arguments.of(boardWithNonUniqueInserts, array(BLACK, BLUE, GREEN, WHITE), 2, 0),
                Arguments.of(boardWithNonUniqueInserts, array(BLACK, GREEN, BLUE, YELLOW), 0, 1),
                Arguments.of(boardWithNonUniqueInserts, array(BLACK, BLACK, RED, YELLOW), 0, 1),
                Arguments.of(boardWithNonUniqueInserts, array(BLACK, BLACK, BLACK, YELLOW), 0, 0)
        );
    }

    @ParameterizedTest
    @MethodSource("insertsAndExpectedException")
    void shouldThrowException(Board board, Color[] inserts, Class<? extends MastermindException> exceptionClass) {
        // given

        // when
        final Throwable thrown = catchThrowable(() -> board.insertColors(inserts));

        // then
        assertThat(thrown)
                .isExactlyInstanceOf(exceptionClass);

    }

    private static Stream<Arguments> insertsAndExpectedException() {
        final Board boardWithUniqueInserts = aBoard().enigmaColors(RED, BLUE, GREEN, WHITE).build();
        final Board boardWithLimitedColorPool = aBoard().colorsPool(RED, BLUE, GREEN, WHITE).enigmaColors(RED, BLUE, GREEN, WHITE).build();

        return Stream.of(
                Arguments.of(boardWithUniqueInserts, array(RED, BLUE, GREEN, WHITE, BLACK), WrongNumberOfInsertsException.class),
                Arguments.of(boardWithUniqueInserts, array(RED, BLUE, GREEN), WrongNumberOfInsertsException.class),
                Arguments.of(boardWithUniqueInserts, array(RED, BLUE, GREEN, GREEN), NonUniqueColorsException.class),
                Arguments.of(boardWithLimitedColorPool, array(RED, BLUE, GREEN, BLACK), ColorsNotInColorPoolException.class)
        );
    }
}
