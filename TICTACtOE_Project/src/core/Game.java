package core;

import application.Main;
import application.HomeSceneController;
import core.player.Bot;
import core.player.Player;

import application.TicTacToeClient;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Game implements Runnable {

    HomeSceneController HSC;
    private Board board;
    public boolean networkFlag = false;
    private Player player1;
    private Player player2;
    private Player activePlayer;
    private Vector winSpaceBeg;
    private Vector winSpaceEnd;

    private boolean end = false;
    private Reason endReason;

    /**
     * select type of game
     */
    public Game(int size, boolean isPvC, boolean isPvP, boolean isNetwork) {
        board = new Board(size);
        if (isPvC) {
            this.player1 = new Player(Space.X, board);
            this.player2 = new Bot(Space.O, board);

        }

        if (isPvP) {
            this.player1 = new Player(Space.X, board);
            this.player2 = new Player(Space.O, board);
        }
        if (isNetwork) {
            networkFlag = true;
           

            TicTacToeClient tttc = Main.tttc;

            Scene mySc = tttc.myNet();

        }

        if (!networkFlag) {
            activePlayer = player1;
        }

    }

    /**
     * check turn and call change active player
     */
    public void nextTurn() {
        if (!networkFlag) {
            activePlayer.nextTurn();
            changeActivePlayer();
        }
    }

    /**
     * get current symbol (x or o)
     */
    public Space getCurrentMark() {
        return activePlayer.getMark();
    }

    /**
     * change active player
     */
    private void changeActivePlayer() {
        if (activePlayer == player1) {
            activePlayer = player2;
        } else {
            activePlayer = player1;
        }
    }
    public boolean tryToPutSomething (int x, int y) {
		if (!end && board.isInBounds(x, y) && board.get(x, y) == Space.NONE) {
			activePlayer.setCoordinates(x, y);
			return true;
		}
		return false;
	}

    /**
     * cases for end game (o wins or x wins or draw)
     */
    private boolean isGameEnd() {

        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                if (board.get(i, j) != Space.NONE && threeInRow(board.get(i, j), i, j)) {
                    endReason = board.get(i, j) == Space.O ? Reason.O_WINS : Reason.X_WINS;
                    return true;
                }
            }
        }
        if (!board.hasEmptySpaces()) {
            endReason = Reason.NO__MORE_SPACE;
            return true;
        }
        return false;
    }

    /**
     * check logic winning
     */
    private boolean threeInRow(Space mark, int x, int y) {
        if (board.isInBounds(x - 1, y - 1) && board.isInBounds(x + 1, y + 1)
                && board.get(x - 1, y - 1) == mark && board.get(x + 1, y + 1) == mark) {
            winSpaceBeg = new Vector(x - 1, y - 1);
            winSpaceEnd = new Vector(x + 1, y + 1);
            return true;
        }
        if (board.isInBounds(x, y - 1) && board.isInBounds(x, y + 1)
                && board.get(x, y - 1) == mark && board.get(x, y + 1) == mark) {
            winSpaceBeg = new Vector(x, y - 1);
            winSpaceEnd = new Vector(x, y + 1);
            return true;
        }
        if (board.isInBounds(x - 1, y) && board.isInBounds(x + 1, y)
                && board.get(x - 1, y) == mark && board.get(x + 1, y) == mark) {
            winSpaceBeg = new Vector(x - 1, y);
            winSpaceEnd = new Vector(x + 1, y);
            return true;
        }
        if (board.isInBounds(x + 1, y - 1) && board.isInBounds(x - 1, y + 1)
                && board.get(x + 1, y - 1) == mark && board.get(x - 1, y + 1) == mark) {
            winSpaceBeg = new Vector(x + 1, y - 1);
            winSpaceEnd = new Vector(x - 1, y + 1);
            return true;
        }
        return false;
    }

    @Override
    public void run() {
        while (!end) {
            nextTurn();
            end = isGameEnd();
        }
    }

    /**
     * @return activePlayer
     */
    public Player getActivePlayer() {
        return activePlayer;
    }

    public Player getPlayerOne() {
        return player1;
    }

    public Player getPlayerTwo() {
        return player2;
    }

    public boolean isEnd() {
        return end = isGameEnd();
    }

    public Vector getWinSpaceBeg() {
        return winSpaceBeg;
    }

    public Vector getWinSpaceEnd() {
        return winSpaceEnd;
    }

    public Reason getEndReason() {
        return endReason;
    }
}
