package com.example.eggwatch;

import android.os.CountDownTimer;
import android.os.Handler;

import java.util.ArrayList;
import java.util.List;

public class EggTimer extends Thread {


    //ArrayList of Event Listener.
    private List<EggTimerListener> listeners = new ArrayList<>();

    private CountDownTimer countDownTimer;
    private boolean isTimeRunning = false;
    private long timeToCook;
    private Handler handler = new Handler();



    //Setter for TimeToCook
    public void setTimeToCook(long timeToCook) {
        this.timeToCook = timeToCook;
    }

    //Getter for isTimeRunning
    public boolean getIsTimeRunning() {
        return isTimeRunning;
    }



    //Adds listener to the Listener List
    public void addListener(EggTimerListener listener) {
        listeners.add(listener);
    }

    //Removes listener from the Listener List
    public void removeListener(EggTimerListener listener) {
        listeners.remove(listener);
    }

    //Stops the CountDownTimer.
    public void stopCounter(){
        countDownTimer.cancel();
        timeToCook = 0;
        isTimeRunning = false;
    }

    //Starts the CountDownTimer
    public void startCounter(){

        //Updates the Listener
        onChangeNotify();

        countDownTimer = new CountDownTimer(timeToCook, 1000) {
            @Override
            public void onTick(long timeLeft) {

                onChangeNotify();
                timeToCook = timeLeft;
            }

            @Override
            public void onFinish() {
            }
        }.start();
        isTimeRunning = true;
    }

    //Updates the listeners.
    public void onChangeNotify() {
        for (EggTimerListener l : listeners) {
            l.onCountDown(showTimeLeft());
        }
    }

    //Returns the String to view.
    public String showTimeLeft() {
        //Finds Minutes from milliseconds
        int minutes = (int) timeToCook / 60000;

        //Find Seconds
        int seconds = (int) timeToCook % 60000 / 1000;

        //Generates the outputstring (Pretty formatted.)
        String timeLeftText = "";
        //sets an extra zero if minutes is less than 10
        if (minutes < 10) timeLeftText += "0";
        timeLeftText += minutes;
        timeLeftText += ":";
        //sets and extra zero if seconds is less tan 10
        if (seconds < 10) timeLeftText += "0";
        timeLeftText += seconds;
        return timeLeftText;
    }

    //Starts the thread.
    @Override
    public void run() {
        //Had to implement a handler for the CountDownTimer
        handler.post(new Runnable() {
            @Override
            public void run() {
                startCounter();
            }
        });
    }
}
