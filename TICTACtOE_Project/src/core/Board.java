package core;

import application.Main;
import application.HomeSceneController;
import javafx.application.Platform;

public class Board {

    HomeSceneController HSC;

    private int size;
    private int emptySpaces;

    private Space[][] level;

    /**
     * size of board and empty spaces
     */
    public Board(int size) {
        this.size = size;
        level = new Space[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                level[i][j] = Space.NONE;
            }
        }
        emptySpaces = size * size;
    }

    /**
     * check empty cells
     */
    public boolean hasEmptySpaces() {
        return emptySpaces == 0 ? false : true;
    }

    public int getSize() {
        return size;
    }

    /**
     * call put symbol and decrease empty cells
     */
    public void set(int x, int y, Space value) {
        level[y][x] = value;
        emptySpaces--;
        Platform.runLater(new Runnable() {
            public void run() {
                HomeSceneController.mainController.putSymbol(value, x, y);
            }
        });
    }

    /**
     * select cell
     */
    public Space get(int x, int y) {
        return level[y][x];
    }

    public boolean isInBounds(int x, int y) {
        return x < size && y < size && x >= 0 && y >= 0;
    }

}
