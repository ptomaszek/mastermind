package com.github.ptomaszek.mastermind.board;

import com.github.ptomaszek.mastermind.board.exception.ColorsNotInColorPoolException;
import com.github.ptomaszek.mastermind.board.exception.MastermindException;
import com.github.ptomaszek.mastermind.board.exception.NonUniqueColorsException;
import com.github.ptomaszek.mastermind.board.exception.WrongNumberOfInsertsException;
import com.github.ptomaszek.mastermind.board.insert.Color;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.util.Arrays.array;

class BoardTest {

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
        final Board boardWithUniqueInserts = Board.BoardBuilder.aBoard().enigmaColors(Color.RED, Color.BLUE, Color.GREEN, Color.WHITE).build();
        final Board boardWithLimitedColorPool = Board.BoardBuilder.aBoard().colorsPool(Color.RED, Color.BLUE, Color.GREEN, Color.WHITE).enigmaColors(Color.RED, Color.BLUE, Color.GREEN, Color.WHITE).build();

        return Stream.of(
                Arguments.of(boardWithUniqueInserts, array(Color.RED, Color.BLUE, Color.GREEN, Color.WHITE, Color.ORANGE), WrongNumberOfInsertsException.class),
                Arguments.of(boardWithUniqueInserts, array(Color.RED, Color.BLUE, Color.GREEN), WrongNumberOfInsertsException.class),
                Arguments.of(boardWithUniqueInserts, array(Color.RED, Color.BLUE, Color.GREEN, Color.GREEN), NonUniqueColorsException.class),
                Arguments.of(boardWithLimitedColorPool, array(Color.RED, Color.BLUE, Color.GREEN, Color.ORANGE), ColorsNotInColorPoolException.class)
        );
    }
}
