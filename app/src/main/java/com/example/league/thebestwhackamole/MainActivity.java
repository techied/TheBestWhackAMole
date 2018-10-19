package com.example.league.thebestwhackamole;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    ArrayList<Button> moles = new ArrayList<>();
    Random r = new Random();
    int score = 0;
    TextView text;
    int loop = 0;
    Timer moleClock;
    Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startButton = findViewById(R.id.start);
        startButton.setOnClickListener(v -> {
            startButton.setVisibility(View.INVISIBLE);
            startWhackAMole();
        });
        moles.add(findViewById(R.id.mole1));
        moles.add(findViewById(R.id.mole2));
        moles.add(findViewById(R.id.mole3));
        moles.add(findViewById(R.id.mole4));
        initializeMoles();
        text = findViewById(R.id.score);
    }

    private void startWhackAMole() {
        score = 0;
        setMoleVisibility(View.VISIBLE);
        final View v = findViewById(R.id.relLayout);
        TimerTask moveMoles = new TimerTask() {
            public void run() {
                Log.wtf("MOLES", "" + loop);

                if(loop >= 10){
                    setMoleVisibility(View.INVISIBLE);
                    runOnUiThread(() -> startButton.setVisibility(View.VISIBLE));
                    loop = 0;
                    moleClock.cancel();
                }
                loop++;
                for (final Button b : moles) {
                    if(b.getVisibility() == View.VISIBLE){
                        score -= 5;
                    }
                    runOnUiThread(() -> {
                        RelativeLayout.LayoutParams p = (RelativeLayout.LayoutParams) b.getLayoutParams();
                        p.setMargins(r.nextInt(v.getWidth() - b.getWidth()), r.nextInt(v.getHeight() - b.getHeight()), 0, 0);
                        b.setLayoutParams(p);
                    });
                }
                setMoleVisibility(View.VISIBLE);
                updateScore();
            }
        };
        moleClock = new Timer("Timer");
        moleClock.scheduleAtFixedRate(moveMoles, 0, 1000);
    }

    private void initializeMoles() {
        for (Button b : moles) {
            b.setOnClickListener((l) -> {
                l.setVisibility(View.INVISIBLE);
                score += 10;
                updateScore();
            });
        }
    }

    private void setMoleVisibility(int visibility) {
        for (Button b : moles) {
            runOnUiThread(() -> b.setVisibility(visibility));
        }
    }

    private void updateScore(){
        runOnUiThread(() -> text.setText("Score: " + score));
    }
}
