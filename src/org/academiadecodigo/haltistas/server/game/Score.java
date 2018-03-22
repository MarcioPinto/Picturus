package org.academiadecodigo.haltistas.server.game;

import java.util.LinkedHashMap;

public class Score {

    LinkedHashMap<String, Integer> scoreKeeper = new LinkedHashMap<>();

    public void addNameScore(String name) {
        scoreKeeper.put(name, 0);
    }

    public void changeScore(String name, int number) {

        scoreKeeper.replace(name, number);

        //method to replace integer
    }

    public int getScore(String name) {
        return scoreKeeper.get(name);
    }

    public int additionDrawer(String name) {
        int score = getScore(name) + 50;
        return score;
    }

    public int additionGuess(String name) {
        int score = getScore(name) + 100;
        return score;
    }

    public void test() {
        for (Integer s : scoreKeeper.values()) {
            System.out.println(" score " + s);
        }
    }
}
