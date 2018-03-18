package org.academiadecodigo.haltistas.client;

import org.academiadecodigo.haltistas.client.controllers.KeyboardController;
import org.academiadecodigo.haltistas.client.controllers.MouseController;

import java.io.IOException;

public class ClientLauncher {

    public static void main(String[] args) {

        String hostName = "localhost";
        int portNumber = 55555;

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
