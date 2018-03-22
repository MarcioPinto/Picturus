package org.academiadecodigo.haltistas.client.utils;

import org.academiadecodigo.haltistas.GameStrings;
import org.academiadecodigo.haltistas.client.Client;
import org.academiadecodigo.haltistas.client.controllers.MouseController;
import org.academiadecodigo.haltistas.client.graphics.Chat;
import org.academiadecodigo.haltistas.client.graphics.Pencil;

public class Receive {

    public static void receivedFromServer(String message, Pencil pencil, Chat chat, MouseController mouseController, Client client) {

        String[] str = message.split(" ");

        switch (str[0]) {

            case "/DRAW/":

                message = messagePeel(str[0], message);

                String[] point = message.split(" ");

                double IniX = Double.parseDouble(point[0]);
                double IniY = Double.parseDouble(point[1]);
                double FinX = Double.parseDouble(point[2]);
                double FinY = Double.parseDouble(point[3]);

                pencil.draw(IniX, IniY, FinX, FinY);
                break;

            case "/CHAT/":

                message = messagePeel(str[0], message);

                chat.receive(message);
                break;


            case "/ACTIVE/":

                mouseController.setCanDraw(true);
                chat.setCanWrite(false);

                message = messagePeel(str[0], message);

                chat.receive(GameStrings.DRAW_THIS + message);
                break;

            case "/INFO/":

                message = messagePeel(str[0], message);

                chat.receive(message);
                break;

            case "/RESET/":
                client.reset();
                break;
        }
    }

    private static String messagePeel(String key, String message) {

        message = message.replaceFirst(key, "");
        message = message.substring(message.indexOf(" ") + 1);

        return message;
    }
}

