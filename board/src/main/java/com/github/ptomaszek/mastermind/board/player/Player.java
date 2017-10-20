package com.github.ptomaszek.mastermind.board.player;

import com.github.ptomaszek.mastermind.board.Board;
import com.github.ptomaszek.mastermind.board.insert.Color;

public abstract class Player {
    private final Board board;

    public Player(Board board) {
        this.board = board;
    }

    public void guess(Color... colors) {
        board.insertColors(colors);
    }
}
