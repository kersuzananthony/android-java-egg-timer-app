package com.kersuzan.eggtimer;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private SeekBar seekBar;
    private Button startButton;
    private TextView timerTextView;
    boolean counterIsActive = false;
    CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Link UI
        this.seekBar = (SeekBar) findViewById(R.id.seekBar);
        this.timerTextView = (TextView) findViewById(R.id.textView);
        this.startButton = (Button) findViewById(R.id.button);

        this.seekBar.setMax(600);
        this.seekBar.setProgress(60);

        updateTimer(60);

        // Listeners
        this.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        this.startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!counterIsActive) {
                    // Begin the timer
                    counterIsActive = true;
                    seekBar.setEnabled(false);
                    startButton.setText("Stop");

                    countDownTimer = new CountDownTimer(MainActivity.this.seekBar.getProgress() * 1000 + 100, 1000) {

                        @Override
                        public void onTick(long millisUntilFinished) {
                            updateTimer((int) millisUntilFinished / 1000);
                        }

                        @Override
                        public void onFinish() {
                            updateTimer(0);
                            MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.sound);
                            mediaPlayer.start();
                            resetTimer();
                        }

                    }.start();
                } else {
                    // Stop the timer
                    resetTimer();
                }
            }
        });
    }

    public void resetTimer() {
        seekBar.setEnabled(true);
        updateTimer(60);
        seekBar.setProgress(60);
        countDownTimer.cancel();
        startButton.setText("Start");
        counterIsActive = false;
    }

    public void updateTimer(int secondsLeft) {
        int minutes = (int) (secondsLeft / 60);
        int seconds = secondsLeft - minutes * 60;
        String secondsString = "";

        if (seconds < 10) {
            secondsString = "0" + Integer.toString(seconds);
        } else {
            secondsString = Integer.toString(seconds);
        }

        timerTextView.setText(Integer.toString(minutes) + ":" + secondsString);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
