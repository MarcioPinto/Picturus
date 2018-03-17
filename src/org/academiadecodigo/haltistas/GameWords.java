package org.academiadecodigo.haltistas;

import java.util.*;

public class GameWords {

    private List<String> ListWords = new ArrayList<>();

    public String getWord() {

        String[] words = {"bat", "cat", "ghost", "bug", "snake", "tree"
                , "knife", "mouse", "sun", "banana", "potato", "duck", "dog"};

        for (int i = 0; i < words.length; i++) {

            ListWords.add(words[i]);
        }
        Collections.shuffle(ListWords);

        String finalWorld = ListWords.get(0);

        return finalWorld;
    }
}
