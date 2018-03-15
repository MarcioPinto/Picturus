package org.academiadecodigo.haltistas;

import org.academiadecodigo.haltistas.Server.Server;
import java.io.IOException;

public class ServerLauncher {

    public static void main(String[] args) {

        if (args.length != 1) {
            System.out.println("Usage: java -jar ServerLauncher <PortNumber>");
            return;
        }

        int port = Integer.parseInt(args[0]);

        try {
            Server server = new Server(port);
            server.start();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
