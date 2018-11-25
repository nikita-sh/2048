package com.example.shume.game2048;

import android.service.quicksettings.Tile;
import android.support.annotation.NonNull;

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
     * The number of rows in Board.
     */
    static int NUM_ROWS = 4;

    /**
     * The number of rows.
     */
    static int NUM_COLS = 4;

    /**
     * The tiles on the board in row-major order.
     */
    public Tile2048[][] tiles = new Tile2048[NUM_ROWS][NUM_COLS];

    /**
     * A new board of tiles in row-major order.
     * Precondition: len(tiles) == NUM_ROWS * NUM_COLS
     *
     * @param tiles the tiles for the board
     */
    Board2048(List<Tile2048> tiles) {
//        Iterator<Tile2048> tIterator = tiles.iterator();
//
//        for (int row = 0; row != Board2048.NUM_ROWS; row++) {
//            for (int col = 0; col != Board2048.NUM_COLS; col++) {
//                Tile2048 tile = tIterator.next();
//                this.tiles[row][col] = tile;
//            }
//        }

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
     * Return the number of tiles on the board.
     *
     * @return the number of tiles on the board
     */
    int numTiles() {
        return NUM_COLS * NUM_ROWS;
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
        for (int row = 0; row != Board2048.NUM_ROWS; row++) {
            for (int col = 1; col != Board2048.NUM_COLS; col++) {
                Tile2048 prevTile = tiles[row][col - 1];
                Tile2048 currTile = tiles[row][col];
                if (prevTile.getId() == currTile.getId() & prevTile.getId() != 1) {
                    tiles[row][col - 1] = new Tile2048(prevTile.getExponent() + 1);
                    tiles[row][col] = new Tile2048(0);
                }
            }
        }
        pushLeft();
        if (isSpawnable(temp1, tiles)) {
            spawnTile();
        }
    }

    /**
     * Helper function for mergeLeft() that pushes all tile to as far left as possible
     */
    private void pushLeft() {
        for (int row = 0; row < Board2048.NUM_ROWS; row++) {
            int farthest = 0;
            for (int col = 0; col < Board2048.NUM_COLS; col++) {
                // if it's not a blank tile
                if (tiles[row][col].getId() != 1) {
                    Tile2048 tempTile = tiles[row][farthest];
                    // swap the tiles
                    tiles[row][farthest] = tiles[row][col];
                    tiles[row][col] = tempTile;
                    farthest++;
                }
            }
        }
        setChanged();
        notifyObservers();
    }

    /**
     * Merges tiles together when a right swipe is intitiated
     */
    public void mergeRight() {
        Tile2048[][] temp1 = makeTempCopy(tiles);
        pushRight();
        for (int row = 0; row < Board2048.NUM_ROWS; row++) {
            for (int col = tiles[row].length - 2; col >= 0; col--) {
                Tile2048 prevTile = tiles[row][col + 1];
                Tile2048 currTile = tiles[row][col];
                if (prevTile.getId() == currTile.getId() & prevTile.getId() != 1) {
                    tiles[row][col + 1] = new Tile2048(prevTile.getExponent()+1);
                    tiles[row][col] = new Tile2048(0);
                }
            }
        }
        pushRight();
        if (isSpawnable(temp1, tiles)) {
            spawnTile();
        }
    }

    /**
     * Helper function for mergeRight to swap the blank tile with the tile furthest to the right
     * that is not blank
     */
    private void pushRight() {
        for (int row = 0; row < Board2048.NUM_ROWS; row++) {
            int farthest = tiles[row].length-1;
            for (int col = tiles[row].length - 1; col >= 0; col--) {
                if (tiles[row][col].getId() != 1) {
                    Tile2048 tempTile = tiles[row][farthest];
                    tiles[row][farthest] = tiles[row][col];
                    tiles[row][col] = tempTile;
                    farthest--;
                }
            }
        }
        setChanged();
        notifyObservers();
    }

    /**
     * Merges tiles together when a upwards swipe is intitiated
     */
    public void mergeUp() {
        Tile2048[][] temp1 = makeTempCopy(tiles);
        pushUp();
        for (int col = 0; col < Board2048.NUM_COLS; col++) {
            for (int row = 1; row < Board2048.NUM_ROWS; row++) {
                Tile2048 prevTile = tiles[row - 1][col];
                Tile2048 currTile = tiles[row][col];
                if (prevTile.getId() == currTile.getId() & prevTile.getId() != 1) {
                    tiles[row-1][col] = new Tile2048(prevTile.getExponent()+1);
                    tiles[row][col] = new Tile2048(0);
                }
            }
        }
        pushUp();
        if (isSpawnable(temp1, tiles)) {
            spawnTile();
        }
    }

    /**
     * Helper function for mergeUp() to merge together two tiles
     */
    private void pushUp() {
        for (int col = 0; col < Board2048.NUM_COLS; col++) {
            int farthest = 0;
            for (int row = 0; row < Board2048.NUM_ROWS; row++) {
                if (tiles[row][col].getId() != 1) {
                    Tile2048 tempTile = tiles[farthest][col];
                    tiles[farthest][col] = tiles[row][col];
                    tiles[row][col] = tempTile;
                    farthest++;
                }
            }
        }
        setChanged();
        notifyObservers();
    }

    /**
     * Merges tiles together when a down swipe is intitiated
     */
    public void mergeDown() {
        // make a copy of the current state as to not mutate it
        Tile2048[][] temp1 = makeTempCopy(tiles);
        pushDown();
        for (int col = 0; col < Board2048.NUM_COLS; col++) {
            for (int row = Board2048.NUM_ROWS - 2; row >= 0; row--) {
                Tile2048 prevTile = tiles[row + 1][col];
                Tile2048 currTile = tiles[row][col];
                if (prevTile.getId() == currTile.getId() & prevTile.getId() != 1) {
                    tiles[row+1][col] = new Tile2048(prevTile.getExponent() + 1);
                    tiles[row][col] = new Tile2048(0);
                }
            }
        }
        pushDown();
        if (isSpawnable(temp1, tiles)) {
            spawnTile();
        }
    }

    /**
     * Helper function for mergeDown() to merge together two tiles in a vertical direction
     */
    private void pushDown() {
        for (int col = 0; col < Board2048.NUM_COLS; col++) {
            int farthest = Board2048.NUM_ROWS - 1;
            for (int row = Board2048.NUM_ROWS - 1; row >= 0; row--) {
                if (tiles[row][col].getId() != 1) {
                    Tile2048 tempTile = tiles[farthest][col];
                    tiles[farthest][col] = tiles[row][col];
                    tiles[row][col] = tempTile;
                    farthest--;
                }
            }
        }
        setChanged();
        notifyObservers();
    }

    /**
     * Randomly spawn a new tile with 80% it's a 2 and 20% it's a 4
     */
    public void spawnTile() {
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
                if (temp1[row][col].getBackground() != tiles[row][col].getBackground()) {
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
                if (this.tiles[row][col].getId() == 1) {
                    int[] pos = {row, col};
                    empty.add(pos);
                }
            }
        }
        return empty;
    }

    /**
     * Returns whether or not a 2048 (or higher) is reached
     * @return boolean
     */
    public boolean gameWon() {
        boolean won = false;
        outer:
        for (int row = 0; row != Board2048.NUM_ROWS; row++) {
            for (int col = 0; col != Board2048.NUM_COLS; col++) {
                if (tiles[row][col].getBackground() >= 2048) {
                    won = true;
                    break outer;
                }
            }
        }
        return won;
    }

    /**
     * Checks if there are any holes in the board
     * @return boolean
     */
    public boolean hasHoles() {
        boolean flag = true;
        outer:
        for (int row = 0; row != Board2048.NUM_ROWS; row++) {
            for (int col = 0; col != Board2048.NUM_COLS; col++) {
                if (tiles[row][col].getBackground() == 1) {
                    flag = false;
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
                if (tiles[row][col].getBackground() == tiles[row][col + 1].getBackground()) {
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
                if (tiles[row][col].getBackground() == tiles[row + 1][col].getBackground()) {
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
                ret.append(tiles[row][col].getId());
            }
            ret.append("]");
        }
        ret.append("]");
        return ret.toString();
    }

    /**
     * Return an iterator for this Board.
     *
     * @return iterator for this Board.
     */
    @NonNull
    public Iterator<Tile2048> iterator() {
        return new TileIterator(this.tiles);
    }

    /**
     * Iterator for Board. Iterates over the tiles on the board in row major order.
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
        public boolean hasNext() {return row < Board2048.NUM_ROWS - 1 & col < Board2048.NUM_COLS - 1;}

        @Override
        public Tile2048 next() {
            Tile2048 returnTile = this.tiles[row][col];
            if (!hasNext()) {
                throw new NoSuchElementException();
            } else if (col == Board2048.NUM_COLS) {
                row++;
                col = 0;
            } else {
                col++;
            }
            return returnTile;
        }
    }
}
