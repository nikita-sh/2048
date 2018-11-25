package com.example.shume.game2048;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Manage a board, including swapping tiles, checking for a win, and managing taps.
 */
class BoardManager2048 implements Serializable {

    public int size = Board2048.NUM_COLS;

    /**
     * The board being managed.
     */
    private Board2048 board;

    /**
     * Manage a default (3 undos allowed) board that has been pre-populated.
     *
     * @param board the board
     */
    BoardManager2048(Board2048 board) {
        this.board = board;
    }

    BoardManager2048() {
        List<Tile2048> tiles = new ArrayList<>();
        final int numTiles = Board2048.NUM_ROWS * Board2048.NUM_COLS;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new Tile2048(0));
        }
        this.board = new Board2048(tiles);
    }

    /**
     * Return the current board.
     */
    Board2048 getBoard() {
        return board;
    }

    /**
     * Return whether the tiles are in row-major order.
     *
     * @return whether the tiles are in row-major order
     */
    boolean gameWon() {
        Board2048 board = this.getBoard();
        boolean won = false;
        for (Tile2048 i : board) {
            won = i.getId() >= 2048;
        }
        return won;
    }

}
