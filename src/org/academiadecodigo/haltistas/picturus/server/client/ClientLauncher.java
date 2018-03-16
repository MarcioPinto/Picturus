package org.academiadecodigo.haltistas.picturus.server.client;

import java.io.IOException;

public class ClientLauncher {

    public static void main(String[] args) {

        String hostName = "localhost";
        int portNumber = 5556;

        Client client = new Client(hostName, portNumber);

        try {
            client.init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
