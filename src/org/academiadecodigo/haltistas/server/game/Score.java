package org.academiadecodigo.haltistas.server.game;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Score {

   private HashMap<String, Integer> scoreKeeper;

    public Score() {
        this.scoreKeeper = new HashMap<>();
    }

    public void add(String name, int increment) {

        int old = 0;

        if (scoreKeeper.containsKey(name)) {
            old = scoreKeeper.get(name);
        }

        scoreKeeper.put(name, old + increment);
    }

    public String transform(){
        String safe = "";
        for (String name : scoreKeeper.keySet()) {
           safe += name + " " + scoreKeeper.get(name) + " ";
        }
        return safe;
    }

    public void removePlayer(String name) {
        scoreKeeper.remove(name);
    }
}
