package com.example.shume.game2048;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

// This is our "GameActivity"
// implements Observer
public class MainActivity2048 extends AppCompatActivity {

    /**
     * The board manager for 2048.
     */
    private BoardManager2048 boardManager2048;

    /**
     * The buttons to display
     */
    private ArrayList<Button> tileButtons;

    /**
     * Constants for swiping directions. Do we even need?
     */
    public static final int UP = 1;
    public static final int DOWN = 2;
    public static final int LEFT = 3;
    public static final int RIGHT = 4;

    // Grid view and calculated column height and width based on device size
    private GestureDetectGridView2048 gridView;
    private static int columnWidth, columnHeight;

    /**
     * Set up the background image for each button based on the master list
     * of positions, and then call the adapter to set the view.
     */
    // Display
    @SuppressLint("SetTextI18n")
    public void display() {
        updateTileButtons();

//        final TextView txtValue = findViewById(R.id.NumMoves);
//        txtValue.setText(Integer.toString(getNumMoves()));
        gridView.setAdapter(new CustomAdapter2048(tileButtons, columnWidth, columnHeight));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createTileButtons(this);
        setContentView(R.layout.activity_main);

        // Add View to activity
        gridView = findViewById(R.id.grid2048);
        gridView.setNumColumns(Board2048.NUM_COLS);
        gridView.setBoardManager(boardManager2048);
        boardManager2048.getBoard().addObserver(this);

        // TODO: figure out how to deal with mismatched API version
        // Observer sets up desired dimensions as well as calls our display function
        gridView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        gridView.getViewTreeObserver().removeOnGlobalLayoutListener(
                                this);
                        int displayWidth = gridView.getMeasuredWidth();
                        int displayHeight = gridView.getMeasuredHeight();

                        columnWidth = displayWidth / Board2048.NUM_COLS;
                        columnHeight = displayHeight / Board2048.NUM_ROWS;

                        display();
                    }
                }
        );
    }

    /**
     * Create the buttons for displaying the tiles.
     *
     * @param context the context
     */
    private void createTileButtons(Context context) {
        Board2048 board = boardManager2048.getBoard();
        tileButtons = new ArrayList<>();
        for (int row = 0; row != Board2048.NUM_ROWS; row++) {
            for (int col = 0; col != Board2048.NUM_COLS; col++) {
                Button tmp = new Button(context);
                tmp.setBackgroundResource(board.getTile(row, col).getBackground());
                this.tileButtons.add(tmp);
            }
        }
    }

    /**
     * Update the backgrounds on the buttons to match the tiles.
     */
    private void updateTileButtons() {
        Board2048 board = boardManager2048.getBoard();
        int nextPos = 0;
        for (Button b : tileButtons) {
            int row = nextPos / Board2048.NUM_ROWS;
            int col = nextPos % Board2048.NUM_COLS;
            b.setBackgroundResource(board.getTile(row, col).getBackground());
            nextPos++;
        }
    }

    // TODO: Make the menu
//    /**
//     * Dispatch onPause() to fragments.
//     */
//    @Override
//    protected void onPause() {
//        super.onPause();
//        saveToFile(StartingActivity.TEMP_SAVE_FILENAME);
//    }

    /**
     * Load the board manager from fileName.
     *
     * @param fileName the name of the file
     */
    private void loadFromFile(String fileName) {

        try {
            InputStream inputStream = this.openFileInput(fileName);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                boardManager2048 = (BoardManager2048) input.readObject();
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("login activity", "File contained unexpected data type: " + e.toString());
        }
    }

    /**
     * Save the board manager to fileName.
     *
     * @param fileName the name of the file
     */
    public void saveToFile(String fileName) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(boardManager2048);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    /**
     * Auto Save the board manager to fileName every 3 moves.
     *
     * @param fileName the name of the file
     */
    public void autoSave(String fileName){
        if (boardManager2048.getSizeMoves() % 3 == 0) {
            saveToFile(fileName);
            Toast.makeText(this, "Your Game was saved!", Toast.LENGTH_SHORT).show();
        }
    }

//    @Override
//    public void update(Observable o, Object arg) {
//        autoSave(StartingActivity.SAVE_FILENAME);
//        display();
//
//    }
}
