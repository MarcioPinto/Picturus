package org.academiadecodigo.haltistas.server.game;

import org.academiadecodigo.haltistas.server.GameStrings;

import java.io.*;
import java.util.*;

public class GameWords {

    private static final String PATH = GameStrings.WORD_LIST;

    public GameWords() {
    }

    public static String getWord() {
        List<String> listWord = new ArrayList<>();

        try {



            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                    Thread.currentThread().getContextClassLoader().getResourceAsStream(PATH)));

            String line ;

            while ((line = bufferedReader.readLine()) != null) {
                listWord.add(line);
            }

        } catch (IOException e) {
            System.err.println(GameStrings.ERROR);
        }

        Collections.shuffle(listWord);
        return listWord.get(0);
    }

}
