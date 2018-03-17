package org.academiadecodigo.haltistas.server;

import org.academiadecodigo.haltistas.GameCommand;
import org.academiadecodigo.haltistas.server.game.PicturusGame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private ServerSocket serverSocket;
    private Set<clientHandler> clientList;
    private PicturusGame game;


    public Server(int port) throws IOException {

        this.serverSocket = new ServerSocket(port);
        this.clientList = new HashSet<>();
        this.game = new PicturusGame(this);
    }


    public void start() throws IOException {

        ExecutorService service = Executors.newCachedThreadPool();

        while (true) {

            Socket clientSocket = serverSocket.accept();
            String clientName = "Guest";
            clientHandler handler = new clientHandler(clientSocket, clientName);

            clientList.add(handler);
            service.submit(handler);
            System.out.println("Connection with new client @ " + clientSocket + "\n");

        }
    }


    public void broadcast(String message) {
        for (clientHandler client : clientList) {
            client.writeMessage(message);

        }
    }

    public void whisper(String name, String word) {
        for (clientHandler client : clientList) {
            if (client.name.equalsIgnoreCase(name)) {
                client.writeMessage(word);
            }

        }
    }


    private class clientHandler implements Runnable {

        private Socket connection;
        private PrintWriter toClients;
        private BufferedReader fromClients;
        private String name;
        private String word;

        clientHandler(Socket clientSocket, String clientName) {
            this.connection = clientSocket;
            this.name = clientName;
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

                    if (message.equalsIgnoreCase(GameCommand.QUIT)) {
                        broadcast("Player disconnected.");
                        break;
                    }

                    if (message.equalsIgnoreCase(GameCommand.CHANGE_NAME)) {
                        name = fromClients.readLine();
                        continue;
                    }

                    broadcast(name + " : " + message);

                }
            } catch (IOException e) {
                e.printStackTrace();

            } finally {
                stop();
            }
        }


        void stop() {

            try {
                clientList.remove(this);
                connection.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        void writeMessage(String message) {
            toClients.println(message);
        }

    }

}


