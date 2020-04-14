package com.example.eggwatch;

import android.os.Handler;
import android.view.View;


public class EggTimerPresenter implements EggTimerListener {

    private View v;
    private EggTimer eggTimer;

    //Constructor
    EggTimerPresenter(View v) {

        Handler handler = new Handler();

        //Starts a new eggTimer Thread.
        handler.post(new EggTimer());
        eggTimer = new EggTimer();

        this.v = v;
    }

    //Starts the CountDownTimer
    public void start() {
        //Starts a listener that updates on change.
        eggTimer.addListener(this);

        eggTimer.start();
    }

    //Stops the CountDownTimer
    public void stop() {
        //Removes the (this) listener from eggTimer
        eggTimer.removeListener(this);

        eggTimer.stopCounter();
    }

    //Sets the timeToCook Property in EggTimer to the decided time.
    public void setTimeToCook(long time) {
        eggTimer.setTimeToCook(time);
    }

    //Returns TimeLeft from EggTimer
    public String showTime() {
        return eggTimer.showTimeLeft();
    }

    //Returns the boolean value from isRunning.
    public boolean getIsRunning() {
        return eggTimer.getIsTimeRunning();
    }

    //Updates view with the listener value
    @Override
    public void onCountDown(String timeLeft) {
        v.onCountDown(timeLeft);
    }

    //Interface for MVP pattern, so that we can call functions in View
    public interface View {
        public void onCountDown(String timeLeft);
        public boolean getIsRunning();
        public String showTime();
        public void setTimeToCook(long time);
        public void stop();
        public void start();
    }
}
