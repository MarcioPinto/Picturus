package org.academiadecodigo.haltistas.server.game;

import java.util.Timer;
import java.util.TimerTask;

public class Time {

    private int seconds;
    private Timer timer;
    private TimerTask timerTask;

    private final static int ROUND_TIME = 60;
    private final static int WAIT_TIME = 30;

    Time() {
        timer = new Timer();

        timerTask = new TimerTask() {

            @Override
            public void run() {

                System.out.println(seconds);
                seconds--;

                if (seconds < 0) {
                    this.cancel();
                    return;
                }
            }
        };
    }

    public void roundTimer() {
        this.seconds = ROUND_TIME;
        timer.schedule(timerTask, 1000, 1000);
    }

    public void waitingTimer(){
        this.seconds = WAIT_TIME;
        timer.schedule(timerTask, 1000, 1000);
    }
}
