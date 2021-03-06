package com.example.shume.game2048;

import android.service.quicksettings.Tile;
import android.support.annotation.NonNull;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Observable;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * The 2048 board.
 */
public class Board2048 extends Observable implements Serializable, Iterable<Tile2048> {

    /**
     * The number of rows in BoardSlidingTiles.
     */
    static int NUM_ROWS = 4;

    /**
     * The number of rows.
     */
    static int NUM_COLS = 4;

    /**
     * Score count
     */
    private static int score;

    private static int scoreAdded;

    /**
     * The tiles on the board in row-major order.
     */
    public Tile2048[][] tiles = new Tile2048[NUM_ROWS][NUM_COLS];

    /**
     * A new board of tiles in row-major order.
     */
    Board2048() {
        for (int row = 0; row != Board2048.NUM_ROWS; row++) {
            for (int col = 0; col != Board2048.NUM_COLS; col++) {
                Tile2048 tile = new Tile2048(0);
                this.tiles[row][col] = tile;
            }
        }
        spawnTile();
        spawnTile();
    }

    /**
     * Getter for the scoreADDED
     * @return int
     */
    public static int getScoreAdded() {
        return Board2048.scoreAdded;
    }

    /**
     * Getter for the score
     * @return int
     */
    public static int getScore() {
        return Board2048.score;
    }

    /**
     * Resets the number of moves at the beginning of a game
     */
    public static void resetNumMoves() {
        score = 0;
    }

    /**
     * Return the tile at (row, col)
     *
     * @param row the tile row
     * @param col the tile column
     * @return the tile at (row, col)
     */
    Tile2048 getTile(int row, int col) {
        return tiles[row][col];
    }

    public Tile2048[][] makeTempCopy(Tile2048[][] tiles) {
        //        Tile2048[][] temp = System.arraycopy(tiles);
        Tile2048[][] temp = new Tile2048[NUM_ROWS][NUM_COLS];
        for (int row = 0; row < Board2048.NUM_ROWS; row++) {
            for (int col = 0; col < Board2048.NUM_COLS; col++) {
                temp[row][col] = tiles[row][col];
            }
        }
        return temp;
    }
    /**
     * Merges tiles together when a left swipe is initiated
     */
    public void mergeLeft() {
        Tile2048[][] temp1 = makeTempCopy(tiles);
        pushLeft();
        int tempAdded = score;
        for (int row = 0; row != Board2048.NUM_ROWS; row++) {
            for (int col = 1; col != Board2048.NUM_COLS; col++) {
                Tile2048 prevTile = tiles[row][col - 1];
                Tile2048 currTile = tiles[row][col];
                if (prevTile.getExponent() == currTile.getExponent() & prevTile.getExponent() != 0) {
                    tiles[row][col - 1] = new Tile2048(prevTile.getExponent() + 1);
                    tiles[row][col] = new Tile2048(0);
                    addScore(Math.pow(2, prevTile.getExponent()+1));
                }
            }
        }
        scoreAdded = getScore() - tempAdded;
        pushLeft();
        if (isSpawnable(temp1, tiles)) {spawnTile();}
        setChanged();
        notifyObservers();
    }

    /**
     * Helper function for mergeLeft() that pushes all tile to as far left as possible
     */
    private void pushLeft() {
        for (int row = 0; row < Board2048.NUM_ROWS; row++) {
            int farthest = 0;
            for (int col = 0; col < Board2048.NUM_COLS; col++) {
                // if it's not a blank tile
                if (tiles[row][col].getExponent() != 0) {
                    Tile2048 tempTile = tiles[row][farthest];
                    // swap the tiles
                    tiles[row][farthest] = tiles[row][col];
                    tiles[row][col] = tempTile;
                    farthest++;
                }
            }
        }
    }

    /**
     * Merges tiles together when a right swipe is intitiated
     */
    public void mergeRight() {
        Tile2048[][] temp1 = makeTempCopy(tiles);
        pushRight();
        int tempAdded = score;
        for (int row = 0; row < Board2048.NUM_ROWS; row++) {
            for (int col = tiles[row].length - 2; col >= 0; col--) {
                Tile2048 prevTile = tiles[row][col + 1];
                Tile2048 currTile = tiles[row][col];
                if (prevTile.getExponent() == currTile.getExponent() & prevTile.getExponent() != 0) {
                    tiles[row][col + 1] = new Tile2048(prevTile.getExponent()+1);
                    tiles[row][col] = new Tile2048(0);
                    addScore(Math.pow(2, prevTile.getExponent()+1));
                }
            }
        }
        scoreAdded = getScore() - tempAdded;
        pushRight();
        if (isSpawnable(temp1, tiles)) {
            spawnTile();
        }
        setChanged();
        notifyObservers();
    }

    /**
     * Helper function for mergeRight to swap the blank tile with the tile furthest to the right
     * that is not blank
     */
    private void pushRight() {
        for (int row = 0; row < Board2048.NUM_ROWS; row++) {
            int farthest = tiles[row].length-1;
            for (int col = tiles[row].length - 1; col >= 0; col--) {
                if (tiles[row][col].getExponent() != 0) {
                    Tile2048 tempTile = tiles[row][farthest];
                    tiles[row][farthest] = tiles[row][col];
                    tiles[row][col] = tempTile;
                    farthest--;
                }
            }
        }
    }

    /**
     * Merges tiles together when a upwards swipe is intitiated
     */
    public void mergeUp() {
        Tile2048[][] temp1 = makeTempCopy(tiles);
        pushUp();
        int tempAdded = score;
        for (int col = 0; col < Board2048.NUM_COLS; col++) {
            for (int row = 1; row < Board2048.NUM_ROWS; row++) {
                Tile2048 prevTile = tiles[row - 1][col];
                Tile2048 currTile = tiles[row][col];
                if (prevTile.getExponent() == currTile.getExponent() & prevTile.getExponent() != 0) {
                    tiles[row-1][col] = new Tile2048(prevTile.getExponent()+1);
                    tiles[row][col] = new Tile2048(0);
                    addScore(Math.pow(2, prevTile.getExponent()+1));
                }
            }
        }
        scoreAdded = getScore() - tempAdded;
        pushUp();
        if (isSpawnable(temp1, tiles)) {
            spawnTile();
        }
        setChanged();
        notifyObservers();
    }

    /**
     * Helper function for mergeUp() to merge together two tiles
     */
    private void pushUp() {
        for (int col = 0; col < Board2048.NUM_COLS; col++) {
            int farthest = 0;
            for (int row = 0; row < Board2048.NUM_ROWS; row++) {
                if (tiles[row][col].getExponent() != 0) {
                    Tile2048 tempTile = tiles[farthest][col];
                    tiles[farthest][col] = tiles[row][col];
                    tiles[row][col] = tempTile;
                    farthest++;
                }
            }
        }
    }

    /**
     * Merges tiles together when a down swipe is intitiated
     */
    public void mergeDown() {
        // make a copy of the current state as to not mutate it
        Tile2048[][] temp1 = makeTempCopy(tiles);
        pushDown();
        int tempAdded = score;
        for (int col = 0; col < Board2048.NUM_COLS; col++) {
            for (int row = Board2048.NUM_ROWS - 2; row >= 0; row--) {
                Tile2048 prevTile = tiles[row + 1][col];
                Tile2048 currTile = tiles[row][col];
                if (prevTile.getExponent() == currTile.getExponent() & prevTile.getExponent() != 0) {
                    tiles[row+1][col] = new Tile2048(prevTile.getExponent() + 1);
                    tiles[row][col] = new Tile2048(0);
                    addScore(Math.pow(2, prevTile.getExponent()+1));
                }
            }
        }
        scoreAdded = getScore() - tempAdded;
        pushDown();
        if (isSpawnable(temp1, tiles)) {
            spawnTile();
        }
        setChanged();
        notifyObservers();
    }

    private void addScore(double pow) {
        Board2048.score += pow;
    }

    /**
     * Helper function for mergeDown() to merge together two tiles in a vertical direction
     */
    private void pushDown() {
        for (int col = 0; col < Board2048.NUM_COLS; col++) {
            int farthest = Board2048.NUM_ROWS - 1;
            for (int row = Board2048.NUM_ROWS - 1; row >= 0; row--) {
                if (tiles[row][col].getExponent() != 0) {
                    Tile2048 tempTile = tiles[farthest][col];
                    tiles[farthest][col] = tiles[row][col];
                    tiles[row][col] = tempTile;
                    farthest--;
                }
            }
        }
    }

    /**
     * Randomly spawn a new tile with 80% it's a 2 and 20% it's a 4
     */
    private void spawnTile() {
        ArrayList<int[]> emptySpots = getEmptySpots();
        double ran = Math.random();
        Tile2048 newTile = ran >= 0.8 ? new Tile2048(2) : new Tile2048(1);
        int ranIndex = new Random().nextInt(emptySpots.size());
        this.tiles[emptySpots.get(ranIndex)[0]][emptySpots.get(ranIndex)[1]] = newTile;
        setChanged();
        notifyObservers();
    }

    /**
     * Helper method for checking if a new tile should be spawned
     */
    private boolean isSpawnable (Tile2048[][] temp1, Tile2048[][] tiles) {
        for (int row = 0; row < Board2048.NUM_ROWS; row++) {
            for (int col = 0; col < Board2048.NUM_COLS; col++) {
                if (temp1[row][col].getExponent() != tiles[row][col].getExponent()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns an array of all of the empty spots in the board
     * @return ArrayList
     */
    private ArrayList<int[]> getEmptySpots() {
        ArrayList empty = new ArrayList();
        for (int row = 0; row != Board2048.NUM_ROWS; row++) {
            for (int col = 0; col != Board2048.NUM_COLS; col++) {
                if (this.tiles[row][col].getExponent() == 0) {
                    int[] pos = {row, col};
                    empty.add(pos);
                }
            }
        }
        return empty;
    }

//    /**
//     * Checks to see if a merge action was made from the previous to current board state
//     * @return boolean
//     */
//    public boolean madeMerge(Tile2048[][] temp1, Tile2048[][] tiles) {
//        int t1Count = 0;
//        int tCount = 0;
//        for (int row = 0; row < Board2048.NUM_ROWS; row++) {
//            for (int col = 0; col < Board2048.NUM_COLS; col++) {
//                // if it is not an empty tile
//                if (temp1[row][col].getExponent() != 0) {
//                    t1Count++;
//                }
//                if (tiles[row][col].getExponent() != 0) {
//                    tCount++;
//                }
//            }
//        }
//        return isSpawnable(temp1, tiles) && t1Count == tCount;
//    }

    /**
     * Checks if there are any holes in the board
     * @return boolean
     */
    private boolean hasHoles() {
        boolean flag = false;
        outer:
        for (int row = 0; row != Board2048.NUM_ROWS; row++) {
            for (int col = 0; col != Board2048.NUM_COLS; col++) {
                if (tiles[row][col].getExponent() == 0) {
                    flag = true;
                    break outer;
                }
            }
        }
        return  flag;
    }

    /**
     * Returns whether or not the board is in a state of no more moves
     * @return boolean
     */
    public boolean isStuck() {
        return !hasHoles() && isStuckHorizontal() && isStuckVertical();
    }

    /**
     * Checks whether there are any ways to merge horizontal tiles
     * @return boolean
     */
    private boolean isStuckHorizontal() {
        boolean stuck = true;
        outer:
        for (int row = 0; row != Board2048.NUM_ROWS; row++) {
            for (int col = 0; col != Board2048.NUM_COLS - 1; col++) {
                if (tiles[row][col].getExponent() == tiles[row][col + 1].getExponent()
                        || tiles[row][col].getExponent() == 0 || tiles[row][col + 1].getExponent() == 0) {
                    stuck = false;
                    break outer;
                }
            }
        }
        return stuck;
    }

    /**
     * Checks whether there are ways to merge vertical tiles
     * @return boolean
     */
    private boolean isStuckVertical() {
        boolean stuck = true;
        outer:
        for (int col = 0; col != Board2048.NUM_COLS; col++) {
            for (int row = 0; row != Board2048.NUM_ROWS - 1; row++) {
                if (tiles[row][col].getBackground() == tiles[row + 1][col].getBackground()
                        || tiles[row][col].getExponent() == 0 || tiles[row + 1][col].getExponent() == 0) {
                    stuck = false;
                    break outer;
                }
            }
        }
        return stuck;
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder();
        ret.append("[");
        for (int row = 0; row < 4; row ++) {
            ret.append("[");
            for (int col = 0; col < 4; col ++) {
                ret.append(Math.pow(2, tiles[row][col].getExponent()));
            }
            ret.append("]");
        }
        ret.append("]");
        return ret.toString();
    }

    /**
     * Return an iterator for this BoardSlidingTiles.
     *
     * @return iterator for this BoardSlidingTiles.
     */
    @NonNull
    public Iterator<Tile2048> iterator() {
        return new TileIterator(this.tiles);
    }

    /**
     * Iterator for BoardSlidingTiles. Iterates over the tiles on the board in row major order.
     */
    public class TileIterator implements Iterator<Tile2048> {

        private int row;

        private int col;

        private Tile2048[][] tiles;

        TileIterator(Tile2048[][] tiles) {
            this.row = 0;
            this.col = 0;
            this.tiles = tiles;
        }

        @Override
        public boolean hasNext() {
            return !(row == Board2048.NUM_ROWS);
        }

        @Override
        public Tile2048 next() {
            Tile2048 returnTile = this.tiles[row][col];
            boolean colInvalid = col == Board2048.NUM_COLS - 1;
            if (!hasNext()) {
                throw new NoSuchElementException("At the end of the 2048 board!");
            }
            if (colInvalid) {
                col = 0;
                row++;
            } else {
                col++;
            }
            return returnTile;
        }
    }
}