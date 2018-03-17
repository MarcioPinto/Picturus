package org.academiadecodigo.haltistas.game;

import java.util.*;

public class GameWords {

    public static String getWord() {

        String[] words = {"bat", "cat", "ghost", "bug", "snake", "tree"
                , "knife", "mouse", "sun", "banana", "potato", "duck", "dog"};

        List<String> wordList = new ArrayList<>(Arrays.asList(words));
        Collections.shuffle(wordList);
        System.out.println(wordList.get(0));
        return wordList.get(0);
    }
}
