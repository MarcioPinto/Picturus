package org.academiadecodigo.haltistas.server;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NameGenerator {


    public NameGenerator() {
    }

    public static String getName() {
        List<String> name = new ArrayList<>();

        try {

            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(Thread.currentThread().getContextClassLoader().
                            getResourceAsStream(GameStrings.NAME_GENERATOR)));

            String line ;

            while ((line = bufferedReader.readLine()) != null) {
                name.add(line);
            }

        } catch (IOException e) {
            System.err.println(GameStrings.ERROR);
        }

        Collections.shuffle(name);
        return name.get(0);
    }
}
