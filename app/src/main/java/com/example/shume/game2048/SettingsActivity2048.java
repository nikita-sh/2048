package com.example.shume.game2048;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SettingsActivity2048 extends StartingActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_2048);
        addStartGameButtonListener();
    }

    /**
     * Activates the Start Game button listener.
     */
    private void addStartGameButtonListener() {
        Button startGame = findViewById(R.id.startGameButton);
        startGame.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                switchToGame(true, 0, 4);
//                RadioGroup complexityGroup = findViewById(R.id.complexity);
//                CheckBox unlimitedBox = findViewById(R.id.unlimited);
//                EditText numUndo = findViewById(R.id.numUndo);
//                int maxUndo = numUndo.getText().toString().equals("") ? 3 : Integer.parseInt(numUndo.getText().toString());
//                boolean unlimited = unlimitedBox.isChecked();
//                if (complexityGroup.getCheckedRadioButtonId() == R.id.complexity5b5) {
//                    switchToGame(unlimited, maxUndo, 5);
//                } else if (complexityGroup.getCheckedRadioButtonId() == R.id.complexity4b4) {
//                    switchToGame(unlimited, maxUndo, 4);
//                } else {
//                    switchToGame(unlimited, maxUndo, 3);
//                }
            }
        });
    }

    /**
     * Switch to the GameActivity view to play the game.
     */
    private void switchToGame(boolean unlimited, int numUndo, int size) {
        Board2048.NUM_COLS = size;
        Board2048.NUM_ROWS = size;
        BoardManager2048.resetNumMoves();
        boardManager = new BoardManager2048(unlimited, numUndo);
//        Intent tmp = new Intent(View.getContext(), MainActivity2048.class);
        Intent tmp = new Intent(this, MainActivity2048.class);
        saveToFile(StartingActivity.TEMP_SAVE_FILENAME);
        startActivity(tmp);
    }

}
