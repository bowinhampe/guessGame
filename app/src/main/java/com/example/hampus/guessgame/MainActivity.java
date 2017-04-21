package com.example.hampus.guessgame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<EditText> mTextBoxes;
    LinearLayout baseLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextBoxes = new ArrayList<>();
        initiateGUI();
    }

    private void initiateGUI(){
        baseLayout = (LinearLayout) findViewById(R.id.layout_main);
        baseLayout.setOrientation(LinearLayout.VERTICAL);

        // Buttons
        Button addPlayer = new Button(this);
        addPlayer.setText("ADD PLAYER");
        addPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPlayer();
            }
        });
        Button startGame = new Button(this);
        startGame.setText("START!");
        startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame();
            }
        });

        baseLayout.addView(addPlayer);
        baseLayout.addView(startGame);
    }
    private void addPlayer(){
        EditText newPlayerField = new EditText(this);
        mTextBoxes.add(newPlayerField);
        baseLayout.addView(newPlayerField);
    }
    private void startGame(){
        ArrayList<String> playerNames = new ArrayList<>();
        for(int i=0;i<mTextBoxes.size();i++){
            playerNames.add(mTextBoxes.get(i).getText().toString());
        }
        Intent i = new Intent(this, GameActivity.class);
        i.putExtra("playerNames",playerNames);
        startActivity(i);
    }
}
