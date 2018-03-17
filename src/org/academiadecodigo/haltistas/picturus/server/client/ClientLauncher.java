package org.academiadecodigo.haltistas.picturus.server.client;

import org.academiadecodigo.haltistas.picturus.server.client.controllers.KeyboardController;
import org.academiadecodigo.haltistas.picturus.server.client.controllers.MouseController;

import java.io.IOException;

public class ClientLauncher {

    public static void main(String[] args) {

        String hostName = "localhost";
        int portNumber = 5556;

        Client client = new Client(hostName, portNumber);

        Thread keyboard = new Thread(new KeyboardController(client));
        keyboard.start();

        MouseController mouseController = new MouseController(client);
        mouseController.init();
        try {

            client.init();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
