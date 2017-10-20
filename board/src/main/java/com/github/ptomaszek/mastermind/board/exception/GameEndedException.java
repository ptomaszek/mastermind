package com.github.ptomaszek.mastermind.board.exception;

import com.github.ptomaszek.mastermind.board.GameEndedStatus;

public class GameEndedException extends MastermindException {

    public GameEndedException(GameEndedStatus gameEndedStatus) {
        super(createMessage(gameEndedStatus));
    }

    private static String createMessage(GameEndedStatus gameEndedStatus) {
        switch (gameEndedStatus) {
            case PLAYER_WON:
                return "Player already won";
            case PLAYER_LOST:
                return "No more lives";
            default:
                throw new UnsupportedOperationException("This status is not supported: " + gameEndedStatus);
        }
    }
}
