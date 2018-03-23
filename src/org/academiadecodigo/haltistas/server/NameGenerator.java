package org.academiadecodigo.haltistas.server;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NameGenerator {


    public NameGenerator() {
    }

    public static String getName() {
        List<String> name = new ArrayList<>();

        try {
            FileReader reader = new FileReader(GameStrings.NAME_GENERATOR);
            BufferedReader bufferedReader = new BufferedReader(reader);

            String line ;

            while ((line = bufferedReader.readLine()) != null) {
                name.add(line);
            }

        } catch (IOException e) {
            System.err.println(GameStrings.ERROR);
        }

        Collections.shuffle(name);
        System.out.println(name.get(0));
        return name.get(0);
    }
}
