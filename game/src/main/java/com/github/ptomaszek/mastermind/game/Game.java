package com.github.ptomaszek.mastermind.game;

import com.github.ptomaszek.mastermind.board.Board;

import static com.github.ptomaszek.mastermind.board.Board.BoardBuilder.aBoard;
import static com.github.ptomaszek.mastermind.board.insert.Color.BLUE;
import static com.github.ptomaszek.mastermind.board.insert.Color.GREEN;
import static com.github.ptomaszek.mastermind.board.insert.Color.RED;
import static com.github.ptomaszek.mastermind.board.insert.Color.WHITE;

public class Game {
    public static void main(String[] args) {
        final Board board = aBoard().enigmaColors(RED, BLUE, GREEN, WHITE).build();

        board.insertColors(RED, BLUE, GREEN, WHITE);
    }
}
