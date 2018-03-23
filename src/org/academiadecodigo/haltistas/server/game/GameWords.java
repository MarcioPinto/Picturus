package org.academiadecodigo.haltistas.server.game;

import org.academiadecodigo.haltistas.server.GameStrings;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class GameWords {

    private static final String PATH = GameStrings.WORD_LIST;

    public GameWords() {
    }

    public static String getWord() {
        List<String> listWord = new ArrayList<>();

        try {
            FileReader reader = new FileReader(PATH);
            BufferedReader bufferedReader = new BufferedReader(reader);

            String line ;

            while ((line = bufferedReader.readLine()) != null) {
                listWord.add(line);
            }

        } catch (IOException e) {
            System.err.println(GameStrings.ERROR);
        }

        Collections.shuffle(listWord);
        System.out.println(listWord.get(0));
        return listWord.get(0);
    }

}
