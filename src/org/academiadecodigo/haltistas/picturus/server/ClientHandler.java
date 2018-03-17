package org.academiadecodigo.haltistas.picturus.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class ClientHandler implements Runnable {

    private Socket connection;
    private PrintWriter toClients;
    private BufferedReader fromClients;
    private GameLogic gameLogic;

    ClientHandler(Socket clientSocket, GameLogic gameLogic) {
        this.connection = clientSocket;
        this.gameLogic = gameLogic;
    }

    @Override
    public void run() {

        try {

            fromClients = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            toClients = new PrintWriter(connection.getOutputStream(), true);

            while (true) {

                String message = fromClients.readLine();
                System.err.println("MESSAGE: " + message);

                if (message.equalsIgnoreCase("/quit")) {
                    System.err.println("CLIENT DISCONNECTED");
                    gameLogic.broadcast("Player disconnected.");
                    break;
                }

                gameLogic.broadcast(message);
            }

        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            stop();
        }
    }


    private void stop() {

        try {
            gameLogic.removeClient(this);
            connection.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void writeMessage(String message) {
        toClients.println(message);
    }
}

