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
     * The sequence of moves made.
     */
    private ArrayList<Integer> moves;

    /**
     * Indicates whether unlimited moves are allowed.
     */
    private boolean unlimitedMoves;

    /**
     * The number of undo moves allowed
     */
    private int maxUndos;

    /**
     * The number of undos used.
     */
    private int undosLeft = 0;

    /**
     * Total count of moves, including undos.
     */
    private static int numMoves = 0;

    /**
     * Manage a default (3 undos allowed) board that has been pre-populated.
     *
     * @param board the board
     */

    BoardManager2048(Board2048 board) {
        this.board = board;
        this.maxUndos = 3;
        this.unlimitedMoves = false;
    }

    /**
     * Manage a new shuffled board with options to select number of undos.
     */
    BoardManager2048(boolean unlimited, int maxUndos) {
        List<Tile2048> tiles = new ArrayList<>();
        final int numTiles = Board2048.NUM_ROWS * Board2048.NUM_COLS;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new Tile2048(tileNum));
        }
        Collections.shuffle(tiles);
        this.board = new Board2048(tiles);
        this.moves = new ArrayList<>();
        this.maxUndos = maxUndos;
        this.unlimitedMoves = unlimited;
    }

    /**
     * The number of total moves made including undos.
     */
    public static int getNumMoves() {
        return numMoves;
    }

    public static void resetNumMoves() {
        numMoves = 0;
    }

    public static void setNumMoves(int numMoves) {
        BoardManager2048.numMoves = numMoves;
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
            won = i.getBackground() >= 2048;
        }
        return won;
    }

    // TODo: Do we even need this?
    /**
     * Return whether any of the four surrounding tiles is the blank tile.
     *
     * @param position the tile to check
     * @return whether the tile at position is surrounded by a blank tile
     */
//    boolean isValidTap(int position) {
//
//        int row = position / Board2048.NUM_COLS;
//        int col = position % Board2048.NUM_COLS;
//        int blankId = board.numTiles();
//        // Are any of the 4 the blank tile?
//        Tile2048 above = row == 0 ? null : board.getTile(row - 1, col);
//        Tile2048 below = row == Board2048.NUM_ROWS - 1 ? null : board.getTile(row + 1, col);
//        Tile2048 left = col == 0 ? null : board.getTile(row, col - 1);
//        Tile2048 right = col == Board2048.NUM_COLS - 1 ? null : board.getTile(row, col + 1);
//        return (below != null && below.getId() == blankId)
//                || (above != null && above.getId() == blankId)
//                || (left != null && left.getId() == blankId)
//                || (right != null && right.getId() == blankId);
//    }

    /**
     * Process a touch at position in the board, swapping tiles as appropriate.
     *
     * @param position the position
     */
    // TODO: Finish touchMove()
    void touchMove(int position) {
        int row = position / Board2048.NUM_ROWS;
        int col = position % Board2048.NUM_COLS;
    }


    /**
     * Process an undo command.
     */
    // TODO: Get Jamal or Pavel to update undoMove() for 2048
//    void undoMove() {
//        setNumMoves(getNumMoves() + 1);
//        if (unlimitedMoves || undosLeft > 0) {
//            int colUndo = this.moves.remove(this.moves.size() - 1);
//            int rowUndo = this.moves.remove(this.moves.size() - 1);
//            this.board.swapTiles(this.board.getBlankRow(), this.board.getBlankCol(),
//                    rowUndo, colUndo);
//            if (!unlimitedMoves) {
//                undosLeft--;
//            }
//        } else {
//            //throw out of bounds exception as if we were trying to remove element from empty list
//            throw new IndexOutOfBoundsException();
//        }
//    }

    /**
     * Return the number of Moves made
     */
    public int getSizeMoves(){
        return this.moves.size();
    }
}
