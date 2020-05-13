package com.hfad.stopwatch;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.hfad.stopwatch.R;

public class StopwatchActivity extends AppCompatActivity {
    //use seconds and running to record the number of seconds passed
    //and whether the stop watch stopped.
    private int seconds = 0;
    private boolean running;
    private boolean wasRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);
        if (savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }
//        we are using a separate method to update the stopwatch.
//        we are starting it when the activity is created
        runTimer();
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("seconds", seconds);
        savedInstanceState.putBoolean("running", running);
        savedInstanceState.putBoolean("wasRunning", wasRunning);
    }
    @Override
    protected void onPause(){
        super.onPause();
        wasRunning = running;
        running = false;
    }
    @Override
    protected void onResume(){
        super.onResume();
//Implement the onStart() method. If the stop Watch was running set it running again
        if (wasRunning){
            running=true;
        }
    }
    //start the stopwatch whe start button is clicked
    public void onClickStart(View view){
        running = true;
    }
    //stop stopwatch running when stop button is clicked
    public void onClickStop(View view) {
        running = false;
    }
    //Reset stopwatch when Reset button is clicked
    public void onClickReset(View view) {
        running = false;
        seconds = 0;
    }

    private  void runTimer() {
        final TextView timeView = (TextView) findViewById(R.id.time_view);
        //create new handler
        final Handler handler = new Handler();
        //call the post() method, passing in a  new Runnable. The post()
        //method processes codes without a delay, so the code in the runnable
        //will run almost immediately
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;
                String time = String.format("%d:%02d:%02d",
                        hours, minutes, secs);
                timeView.setText(time);
                if (running) {
                    seconds++;
                }
//post the code in the Runnable to be run again after a delay of 1000 milliseconds, or
//1 second. As this line of code is included in the Runnable run() method, this will keep getting called
                handler.postDelayed(this, 1000);

            }
        });
    }
}