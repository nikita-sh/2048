package com.example.shume.game2048;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class GamesActivity extends AppCompatActivity {

    public static String currGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games);
        currGame = null;
        addStart2048Listener();
    }

    /**
     * Activates the Start2048 button.
     */
    private void addStart2048Listener() {
        Button Start2048 = findViewById(R.id.Start2048);
        Start2048.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currGame = "2048";
                switchTo2048();
            }
        });
    }

    /**
     * Switches to 2048 Activity
     */
    private void switchTo2048() {
        Intent tmp = new Intent(this, StartingActivity2048.class);
        startActivity(tmp);
    }

}
