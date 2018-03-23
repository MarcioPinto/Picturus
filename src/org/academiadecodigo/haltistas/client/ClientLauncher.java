package org.academiadecodigo.haltistas.client;

import org.academiadecodigo.haltistas.GameStrings;
import org.academiadecodigo.haltistas.client.controllers.KeyboardController;
import org.academiadecodigo.haltistas.client.controllers.MouseController;
import org.academiadecodigo.haltistas.client.utils.Constants;
import org.academiadecodigo.simplegraphics.pictures.Picture;

import java.io.IOException;

public class ClientLauncher {


    public static void main(String[] args) {

        String hostName = "localhost";
        int portNumber = 55555;

        Picture picture = new Picture(Constants.PADDING,Constants.PADDING,"resources/canvas.png");
        picture.draw();

        Client client = new Client(hostName, portNumber);

        Thread keyboard = new Thread(new KeyboardController(client));
        keyboard.start();

        MouseController mouseController = new MouseController(client);
        mouseController.init();

        client.setMouseController(mouseController);

        try {

            client.init();

        } catch (IOException e) {
            System.err.println(GameStrings.CONNECTION_ERROR);

        }
    }
}
