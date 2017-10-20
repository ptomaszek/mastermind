package com.github.ptomaszek.mastermind.game;

import com.github.ptomaszek.mastermind.board.Board;
import com.github.ptomaszek.mastermind.board.insert.Color;
import com.github.ptomaszek.mastermind.board.insert.Peg;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

import static com.github.ptomaszek.mastermind.board.Board.BoardBuilder.aBoard;
import static com.github.ptomaszek.mastermind.board.insert.Color.BLUE;
import static com.github.ptomaszek.mastermind.board.insert.Color.GREEN;
import static com.github.ptomaszek.mastermind.board.insert.Color.ORANGE;
import static com.github.ptomaszek.mastermind.board.insert.Color.RED;
import static com.google.common.collect.MoreCollectors.onlyElement;
import static java.util.stream.Collectors.toList;

public class ConsoleGame {
    private Board board;

    public ConsoleGame(Board board) {
        Preconditions.checkNotNull(board, "Board cannot be null");
        this.board = board;
    }

    public void play() {
        final Scanner scanner = new Scanner(System.in);

        System.out.print("Available colors: ");
        Stream.of(board.getColorsPool()).forEach(System.out::print);
        System.out.println();
        System.out.println();

        while (!board.gameEndedStatus().isPresent()) {
            System.out.println();
            System.out.println("Lives left: " + board.livesLeft());
            System.out.print("Enter colors (first letter is enough): ");
            final String colors = scanner.nextLine();

            try {
                final List<Peg> pegs = board.insertColors(Stream.of(colors.split(" ")).map(colorName -> findColor(board.getColorsPool(), colorName)).collect(toList()));
                Stream.of(pegs).forEach(System.out::print);
                System.out.println();
            } catch (RuntimeException ex) {
                System.err.println(ExceptionUtils.getMessage(ex));
                System.out.println("Something went wrong. Try again");
            }
        }

        System.out.println();
        System.out.println(board.gameEndedStatus().get());
    }

    private Color findColor(ImmutableSet<Color> colorsPool, String colorName) {
        return colorsPool.stream().filter(actualColor -> actualColor.name().startsWith(colorName.toUpperCase())).collect(onlyElement());
    }

    public static void main(String[] args) {
        new ConsoleGame(aBoard().enigmaColors(RED, GREEN, BLUE, ORANGE).build()).play();
    }
}
