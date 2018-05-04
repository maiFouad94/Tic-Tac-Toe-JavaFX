package core.player;

import core.Board;
import core.Space;
import core.Vector;
/**
 * 
 * @author NaDa_DaHaB
 */
public class Player {

    protected Space symbol;
    private volatile boolean input;
    protected Board board;

    private Vector coordinates;

    protected long turnTime;

    public Player(Space mark, Board board) {
        this.symbol = mark;
        this.board = board;
    }
/**
 * check turn
 */
    public synchronized void nextTurn() {
        input = false;
        if (!input) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (input) {
            board.set(coordinates.getX(), coordinates.getY(), symbol);
        }
    }
/**
 * select row and column
 */
    public synchronized void setCoordinates(int x, int y) {
        input = true;
        coordinates = new Vector(x, y);
        this.notifyAll();
    }
/**
 * @return symbol
 */
    public Space getMark() {
        return symbol;
    }
/**
 * @return input
 */
    public boolean isInput() {
        return input;
    }

    public boolean setInput() {
        return input;
    }

}
