package org.academiadecodigo.haltistas.server.game;

import java.util.*;

public class GameWords {

    public static String getWord() {

        String[] words = {"bat", "cat", "ghost", "bug", "snake", "tree"
                , "knife", "mouse", "sun", "banana", "potato", "duck", "dog"};

        List<String> wordList = new ArrayList<>(Arrays.asList(words));
        Collections.shuffle(wordList);
        return wordList.get(0);
    }
}
