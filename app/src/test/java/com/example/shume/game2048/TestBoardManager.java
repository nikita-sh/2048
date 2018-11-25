package com.example.shume.game2048;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestBoardManager {

    @Test
    public void testGameWon() {
        BoardManager2048 boardManager = new BoardManager2048();
        boardManager.getBoard().tiles[3][1] = new Tile2048(11);
        assertTrue(boardManager.gameWon());
    }
}
