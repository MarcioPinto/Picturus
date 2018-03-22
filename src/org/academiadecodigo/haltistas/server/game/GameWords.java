package org.academiadecodigo.haltistas.server.game;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class GameWords {

    private static final String PATH = "resources/gameWords.txt";

    public GameWords() {
    }


    public static String getWord() {
        List<String> listWord = new ArrayList<>();

        try {
            FileReader reader = new FileReader(PATH);
            BufferedReader bufferedReader = new BufferedReader(reader);

            String line = "";

            while ((line = bufferedReader.readLine()) != null) {
                listWord.add(line);
            }

            /*
            for (String s: listWord) {//TODO delete just to see
                System.out.println(s);
            }
            */

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Collections.shuffle(listWord);
        System.out.println(listWord.get(0));
        return listWord.get(0);
    }

}
