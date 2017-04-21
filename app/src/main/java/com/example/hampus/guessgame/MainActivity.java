package com.example.hampus.guessgame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<EditText> mTextBoxes;
    LinearLayout baseLayout;
    final private boolean DEBUG = true;
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
        addPlayer.setTextSize(40);
        addPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPlayer();
            }
        });
        Button startGame = new Button(this);
        startGame.setText("START!");
        startGame.setTextSize(40);
        startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame();
            }
        });

        TextView playerTextHeader = new TextView(this);
        playerTextHeader.setText("Players: ");
        playerTextHeader.setGravity(Gravity.CENTER);
        playerTextHeader.setTextSize(20);
        baseLayout.addView(addPlayer);
        baseLayout.addView(startGame);
        baseLayout.addView(playerTextHeader);
    }
    private void addPlayer(){
        EditText newPlayerField = new EditText(this);
        mTextBoxes.add(newPlayerField);
        baseLayout.addView(newPlayerField);
    }
    private void startGame(){
        if(!DEBUG && mTextBoxes.size()==0){
            Toast toast = Toast.makeText(this,"No players",Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        ArrayList<String> playerNames = new ArrayList<>();
        for(int i=0;i<mTextBoxes.size();i++){
            playerNames.add(mTextBoxes.get(i).getText().toString());
        }

        if(DEBUG){
            for(int i=0;i<6;i++){
                playerNames.add("PLAYER: "+Integer.toString(i));
            }
        }

        Intent i = new Intent(this, GameActivity.class);
        i.putExtra("playerNames",playerNames);
        startActivity(i);
    }
}
