package com.example.hampus.guessgame;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

public class GameActivity extends AppCompatActivity {
    LinearLayout gameLayout;
    ArrayList<Player> players;
    ArrayList<String> words;
    ArrayList<Boolean> doneWords;
    Stack<String> gameWords;
    TextView activePlayer;
    Button start;
    TextView word;
    boolean gameOn=false;
    int playerTurn=0;
    private long startTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        gameLayout = (LinearLayout) findViewById(R.id.game_layout);
        gameLayout.setGravity(Gravity.CENTER);
        Intent intent = getIntent();
        ArrayList<String> playerNames = intent.getStringArrayListExtra("playerNames");
        players = new ArrayList<>();
        for(int i=0;i<playerNames.size();i++){
            players.add(new Player(playerNames.get(i)));
        }
        initiateGUI();
        loadWords();
    }

    private void initiateGUI(){
        start = new Button(this);
        Button endGame = new Button(this);
        Button next = new Button(this);
        word = new TextView(this);
        Button player = null;
        LinearLayout playerLayout;
        activePlayer = new TextView(this);

        word.setTextSize(110);
        start.setText("START");
        next.setText("NEXT WORD");
        word.setText("WORD");
        endGame.setText("FINISH AND SHOW POINTS");
        activePlayer.setText(players.get(playerTurn).getName());
        gameLayout.addView(start);
        gameLayout.addView(activePlayer);
        gameLayout.addView(word);
        gameLayout.addView(next);

        endGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endGame();
            }
        });
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRound();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextWord();
            }
        });
        for(int i=0;i<players.size();i++) {
            playerLayout = new LinearLayout(this);
            playerLayout.setOrientation(LinearLayout.HORIZONTAL);
            player = new Button(this);
            player.setText(players.get(i).getName());
            player.setId(i);
            Log.d("TEST",players.get(i).getName());
            player.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(gameOn) {
                        players.get(v.getId()).addPoint();
                        nextWord();
                    }
                }
            });
            playerLayout.addView(player);
            gameLayout.addView(playerLayout);
        }
        gameLayout.addView(endGame);
    }

    public class GameLoop implements Runnable {

        private TextView player;
        private Button startButton;
        public GameLoop(TextView player, Button start){
            this.player = player;
            this.startButton = start;
        }
        public void run() {
            while(startTime != System.currentTimeMillis()){
            }
            endRound();
        }

    }
    private void endGame(){
        gameLayout.setVisibility(View.INVISIBLE);

        LinearLayout ll = (LinearLayout) findViewById(R.id.gg_layout);
        ll.setOrientation(LinearLayout.VERTICAL);

        LinearLayout llP = null;

        TextView pName;
        TextView points;

        for(int i=0;i<players.size();i++){
            pName = new TextView(this);
            points = new TextView(this);
            llP = new LinearLayout(this);
            llP.setOrientation(LinearLayout.HORIZONTAL);
            pName.setText(players.get(i).getName());
            points.setText(Integer.toString(players.get(i).getPoints()));

            llP.addView(pName);
            llP.addView(points);

            ll.addView(llP);
        }

    }
    private void startRound(){
        gameOn=true;
        start.setVisibility(View.INVISIBLE);
        word.setText(gameWords.pop());

        startTime = System.currentTimeMillis() + 30000;

        //(new Thread(new GameLoop(activePlayer, start))).start();
    }
    private void nextWord(){
        if(gameOn) {
            if (startTime <= System.currentTimeMillis()) {
                if (!gameWords.isEmpty()) {
                    word.setText(gameWords.pop());
                } else {
                    Toast toast = new Toast(this);
                    toast.makeText(this, "No more words GAME OVER!", Toast.LENGTH_LONG);
                }
            }
            else{
                endRound();
            }
        }
    }

    private void endRound(){
        gameOn=false;
        playerTurn++;
        if(playerTurn>=players.size()){
            playerTurn=0;
        }
    }

    private void mixNumbers(){
        // Mix numbers
        gameWords= new Stack<>();
        int counter = words.size();
        int randomNumber =0;

        while(counter!=0) {
            randomNumber = (int) (Math.random() * 10000) % words.size();
            if(!doneWords.get(randomNumber)) {
                gameWords.push(words.get(randomNumber));
                doneWords.remove(randomNumber);
                doneWords.add(randomNumber, true);
                counter--;

            }
            Log.d("RANDOM:",randomNumber+"");
        }

    }
    private void loadWords(){
        words = new ArrayList<>();
        doneWords = new ArrayList<>();
        File file = new File("/mnt/sdcard/","file.txt");
        StringBuilder text = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                text.append(line);
                words.add(line.toString());
                doneWords.add(false);
                text.append('\n');
            }
            br.close();
        }
        catch (IOException e) {
            //You'll need to add proper error handling here
            e.printStackTrace();
        }

        mixNumbers();
    }

}