package org.academiadecodigo.haltistas.server.game;

import java.util.Timer;

public class MainTestTimer {

    public static void main(String[] args) {
        Timer timer = new Timer();
        Time time = new Time(timer);

        //time.roundTimer();

        time.waitingTimer();
    }

}
