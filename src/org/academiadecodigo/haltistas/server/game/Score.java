package org.academiadecodigo.haltistas.server.game;

import java.util.LinkedHashMap;

public class Score {

    LinkedHashMap<String, Integer> scoreKeeper = new LinkedHashMap<>();

    public void addNameScore(String name) {
        //scoreKeeper.put(name, 0);
    }

    public void changeScore(String name, int number) {

        scoreKeeper.replace(name, number);
        //method to replace integer
    }

    public int getScore(String name) {
        System.out.println("NAME: " + name);
        return scoreKeeper.get(name);
    }

    public void test() {
        for (String name : scoreKeeper.keySet()) {
            System.out.println(name + " " + scoreKeeper.get(name));
        }
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
        System.out.println(safe);
        return safe;
    }
}
