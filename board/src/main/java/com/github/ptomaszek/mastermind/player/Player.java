package com.github.ptomaszek.mastermind.player;

import com.github.ptomaszek.mastermind.Board;
import com.github.ptomaszek.mastermind.insert.Color;

public abstract class Player {
    private final Board board;

    public Player(Board board) {
        this.board = board;
    }

    public void guess(Color... colors) {
        board.insertColors(colors);
    }
}
