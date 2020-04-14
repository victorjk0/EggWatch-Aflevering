package com.example.eggwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements EggTimerPresenter.View {

    private TextView countdown;
    private ImageButton start;
    EggTimerPresenter eggTimerPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        countdown = findViewById(R.id.timer);
        start = findViewById(R.id.start);
        eggTimerPresenter = new EggTimerPresenter(this);

    }

    //Toggle for start / stop button
    public void onButtonStartStop(View v) {
        //checks if running if true reset timer to 0 else start.
        if (getIsRunning()) {
            stop();
            start.setImageResource(R.drawable.play_black);
            countdown.setText("00:00");
            start.setEnabled(false);
        } else {
            start();
            start.setImageResource(R.drawable.stop_black);
        }
    }

    public void onButtonEggSelector(View v) {
        switch (v.getId()) {
            case R.id.soft:
                //sets time to 5 min
                prepareCountDownTimer(300000);
                break;
            case R.id.medium:
                //sets time to 7 min
                prepareCountDownTimer(420000);
                break;
            case R.id.hard:
                //sets time to 10 min
                prepareCountDownTimer(600000);
                break;
            default:
                //Throw exception if unknown button is pressed.
                throw new RuntimeException("Unknown Button");
        }
    }

    //Prepares the CountDownTimer.
    public void prepareCountDownTimer(long time) {
        start.setEnabled(true);
        setTimeToCook(time);
        countdown.setText(showTime());
    }

    //methods from EggTimerPresenter.View interface.

    @Override
    public void onCountDown(String timeLeft) {
        countdown.setText(timeLeft);
    }

    @Override
    public boolean getIsRunning() {
        return eggTimerPresenter.getIsRunning();
    }

    @Override
    public String showTime() {
        return eggTimerPresenter.showTime();
    }

    @Override
    public void setTimeToCook(long time) {
        eggTimerPresenter.setTimeToCook(time);
    }

    @Override
    public void stop() {
        eggTimerPresenter.stop();
    }

    @Override
    public void start() {
        eggTimerPresenter.start();
    }
}
