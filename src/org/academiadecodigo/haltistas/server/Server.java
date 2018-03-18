package org.academiadecodigo.haltistas.server;

import org.academiadecodigo.haltistas.GameCommand;
import org.academiadecodigo.haltistas.server.game.PicturusGame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private ServerSocket serverSocket;
    private Map<String, ClientHandler> clientList;
    private PicturusGame game;
    private int index;


    public Server(int port) throws IOException {

        this.serverSocket = new ServerSocket(port);
        this.clientList = new HashMap<>();
        this.game = new PicturusGame(this);
    }


    public void start() throws IOException {

        ExecutorService service = Executors.newCachedThreadPool();

        while (true) {

            Socket clientSocket = serverSocket.accept();

            String clientName = generateName();

            ClientHandler handler = new ClientHandler(clientSocket);

            clientList.put(clientName, handler);

            game.addPlayer(clientName);

            System.out.println(clientName);
            service.submit(handler);

            System.out.println("Connection with new client @ " + clientSocket + "\n");
            game.prepareGame();
        }
    }

    private String generateName() {
        index++;
        return "Guest " + index;
    }

    public void broadcast(String message) {

        for (ClientHandler client : clientList.values()) {
            client.writeMessage(message);
        }
    }

    public void whisper(String name, String word) {

        ClientHandler client = clientList.get(name);
        client.writeMessage(word);
    }


    private class ClientHandler implements Runnable {

        private Socket connection;
        private PrintWriter toClients;
        private BufferedReader fromClients;
        private Decoder decoder;

        private String name;
        private String word;

        ClientHandler(Socket clientSocket) {
            this.connection = clientSocket;
            decoder = new Decoder(game);
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

                    decoder.decoder(message);
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


