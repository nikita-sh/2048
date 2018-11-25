package com.example.shume.game2048;

import org.junit.Test;

import java.util.ArrayList;
import static org.junit.Assert.*;

public class TestBoard2048 {
    private Board2048 board;

    public void setUp() {
        this.board = new Board2048();
    }

    public void tearDown() {
        this.board = new Board2048();
    }

    @Test
    public void testMergeLeft() {
        setUp();
        Tile2048[] temp = {new Tile2048(1),
                new Tile2048(1),
                new Tile2048(0),
                new Tile2048(0)};
        this.board.tiles[0] = temp;
        this.board.mergeLeft();
        boolean exp = (this.board.tiles[0][0].getExponent() == 2) &
                (this.board.tiles[0][1].getExponent() == 0) &
                (this.board.tiles[0][2].getExponent() == 0) &
                (this.board.tiles[0][3].getExponent() == 0);
        assertTrue(exp);
        tearDown();
    }

    @Test
    public void testMergeUp() {
        setUp();
        this.board.tiles[0][0] = new Tile2048(1);
        this.board.tiles[1][0] = new Tile2048(1);
        this.board.tiles[2][0] = new Tile2048(1);
        this.board.tiles[3][0] = new Tile2048(1);
        this.board.mergeUp();
        boolean exp = (this.board.tiles[0][0].getExponent() == 2) &
                (this.board.tiles[1][0].getExponent() == 2) &
                (this.board.tiles[2][0].getExponent() == 0) &
                (this.board.tiles[3][0].getExponent() == 0);
        assertTrue(exp);
        tearDown();
    }

    @Test
    public void testMergeRight() {
        setUp();
        Tile2048[] temp = {new Tile2048(1),
                new Tile2048(1),
                new Tile2048(0),
                new Tile2048(0)};
        this.board.tiles[0] = temp;
        this.board.mergeRight();
        boolean exp = (this.board.tiles[0][0].getExponent() == 0) &
                (this.board.tiles[0][1].getExponent() == 0) &
                (this.board.tiles[0][2].getExponent() == 0) &
                (this.board.tiles[0][3].getExponent() == 2);
        assertTrue(exp);
        tearDown();
    }

    @Test
    public void testMergeDown() {
        setUp();
        this.board.tiles[0][0] = new Tile2048(1);
        this.board.tiles[1][0] = new Tile2048(1);
        this.board.tiles[2][0] = new Tile2048(1);
        this.board.tiles[3][0] = new Tile2048(1);
        this.board.mergeUp();
        boolean exp = (this.board.tiles[0][0].getExponent() == 0) &
                (this.board.tiles[1][0].getExponent() == 0) &
                (this.board.tiles[2][0].getExponent() == 2) &
                (this.board.tiles[3][0].getExponent() == 2);
        assertTrue(exp);
        tearDown();
    }

    @Test
    public void testNumTiles() {}

    @Test
    public void testGetTile() {}

    @Test
    public void testMakeTempCopy() {}

    @Test
    public void testPushLeft() {}

    @Test
    public void testPushUp() {}

    @Test
    public void testPushRight() {}

    @Test
    public void testPushDown() {}

    @Test
    public void testSpawnTile() {}

    @Test
    public void testIsSpawnable() {}

    @Test
    public void testGetEmptySpots() {}

    @Test
    public void testHasHoles() {}

    @Test
    public void testIsStuck() {}

    @Test
    public void testIsStuckHoriontal() {}

    @Test
    public void testIsStuckVertical() {}

    @Test
    public void testIterator() {}
}
