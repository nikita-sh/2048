package com.example.shume.game2048;

import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * A Tile in a sliding tiles puzzle.
 */
public class Tile2048 implements Comparable<Tile2048>, Serializable {

    /**
     * The background id to find the tile image.
     */
    private int background;

    /**
     * The unique id.
     */
    private int id;

    private int exponent;

    /**
     * Return the background id of the background
     *
     * @return the background id
     */
    public int getBackground() {
        return background;
    }

    /**
     * The value on the 2048 tile
     * @return int
     */
    public int getExponent() {return this.exponent;}

    /**
     * Return the tile id.
     *
     * @return the tile id
     */
    public int getId() {
        return id;
    }

    /**
     * A Tile with id and background. The background may not have a corresponding image.
     *
     * @param id         the id
     * @param background the background
     */
    Tile2048(int id, int background) {
        this.id = id;
        this.background = background;
        this.exponent = background;
    }

    /**
     * A tile with a background id; look up and set the id.
     *
     * @param exponent
     */
    Tile2048(int exponent) {
        this.exponent = exponent;
        this.id = (int) Math.pow(2, exponent);
        tile(exponent);
    }

    /**
     * Corresponding tile for a 4x4 board.
     *
     * @param backgroundId
     */
    private void tile(int backgroundId) {
        Object[] pics = {R.drawable.block_blank, R.drawable.block_2, R.drawable.block_4, R.drawable.block_8,
                R.drawable.block_16, R.drawable.block_32, R.drawable.block_64, R.drawable.block_128, R.drawable.block_256,
                R.drawable.block_512, R.drawable.block_1024, R.drawable.block_2048, R.drawable.block_4096};
        this.background = (int) pics[backgroundId];
    }

    @Override
    public int compareTo(@NonNull Tile2048 o) {
        return o.id - this.id;
    }
}

