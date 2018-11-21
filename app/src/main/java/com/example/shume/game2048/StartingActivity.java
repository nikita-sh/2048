package com.example.shume.game2048;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class StartingActivity {

    public static final String name = "2048";

    // TODO: login activity activity and class here
    /**
     * The main save file.
     */
    public static String SAVE_FILENAME = LoginActivity.users.getCurrentUser() + ".ser";

    /**
     * A temporary save file.
     */
    public static String TEMP_SAVE_FILENAME = LoginActivity.users.getCurrentUser() + "_temp.ser";

    /**
     * The board manager.
     */
    protected BoardManager2048 boardManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        saveToFile(TEMP_SAVE_FILENAME);

        setContentView(R.layout.activity_starting);
        addStartButtonListener();
        addLoadButtonListener();
        addSaveButtonListener();
//        addScoreButtonListener();
    }

    /**
     * Activate the start button.
     */
    private void addStartButtonListener() {
        Button startButton = findViewById(R.id.StartButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToSettings();
                //set user score to zero
            }
        });
    }

    // TODO: Make the leaderboard so that the button does something
//    /**
//     * Activate the score button.
//     */
//    private void addScoreButtonListener() {
//        Button scoreButton = findViewById(R.id.ScoreButton);
//        scoreButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                switchToScoreboard();
//            }
//        });
//    }
//
//    private void switchToScoreboard() {
//        Intent tmp = new Intent(this, MainListView.class);
//        startActivity(tmp);
//    }

    /**
     * Activate the load button.
     */
    private void addLoadButtonListener() {
        Button loadButton = findViewById(R.id.LoadButton);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFromFile(SAVE_FILENAME);
                saveToFile(TEMP_SAVE_FILENAME);
                if (boardManager != null) {
                    makeToastLoadedText();
                    switchToGame();
                } else {
                    makeNoLoadedGameToast();
                }
            }
        });
    }

    /**
     * Display that a game was loaded successfully.
     */
    private void makeToastLoadedText() {
        Toast.makeText(this, "Loaded Game", Toast.LENGTH_SHORT).show();
    }

    /**
     * Activate the save button.
     */
    private void addSaveButtonListener() {
        Button saveButton = findViewById(R.id.SaveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToFile(SAVE_FILENAME);
                saveToFile(TEMP_SAVE_FILENAME);
                makeToastSavedText();
            }
        });
    }

    /**
     * Display that a game was saved successfully.
     */
    private void makeToastSavedText() {
        Toast.makeText(this, "Game Saved", Toast.LENGTH_SHORT).show();
    }

    /**
     * Read the temporary board from disk.
     */
    @Override
    protected void onResume() {
        super.onResume();
        loadFromFile(TEMP_SAVE_FILENAME);
    }

    /**
     * Switch to the MainActivity2048 view to play the game.
     */
    private void switchToGame() {
        Intent tmp = new Intent(this, MainActivity2048.class);
        saveToFile(StartingActivity.TEMP_SAVE_FILENAME);
        startActivity(tmp);
    }



}
