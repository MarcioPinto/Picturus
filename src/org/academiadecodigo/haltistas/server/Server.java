package org.academiadecodigo.haltistas.server;

import org.academiadecodigo.haltistas.GameStrings;
import org.academiadecodigo.haltistas.server.game.PicturusGame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private ServerSocket serverSocket;
    private Map<String, ClientHandler> clientList;
    private final PicturusGame game;
    private int index;
    private String clientName;



    public Server(int port) throws IOException {

        this.serverSocket = new ServerSocket(port);
        this.clientList = new HashMap<>();
        this.game = new PicturusGame(this);
    }


    public void start() throws IOException {

        ExecutorService service = Executors.newCachedThreadPool();
        new Thread(game).start();

        while (true) {

            Socket clientSocket = serverSocket.accept();
            clientName = generateName();

            ClientHandler handler = new ClientHandler(clientSocket, clientName);
            clientList.put(clientName, handler);

            service.submit(handler);
            game.addPlayer(clientName);
        }
    }


    private String generateName() {

        return NameGenerator.getName();
    }

    public void broadcast(String message, List<String> names) {

        for (String name : names) {
            clientList.get(name).writeMessage(message);
        }
    }

    public void whisper(String name, String word) {

        ClientHandler client = clientList.get(name);
        client.writeMessage(word);
    }


    class ClientHandler implements Runnable {

        private String clientName;
        private Socket connection;
        private PrintWriter toClients;
        private BufferedReader fromClients;
        private Decoder decoder;


        ClientHandler(Socket clientSocket, String clientName) throws IOException {
            this.clientName = clientName;
            this.connection = clientSocket;
            this.decoder = new Decoder(game);
            this.toClients = new PrintWriter(connection.getOutputStream(), true);
        }

        public String getClientName() {
            return clientName;
        }

        @Override
        public void run() {

            try {

                fromClients = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                while (true) {

                    String message = fromClients.readLine();

                    if (message == null || message.isEmpty()) {
                        continue;
                    }

                    decoder.decoder(message,this);
                }

            } catch (IOException e) {
                System.err.println(GameStrings.ERROR);

            } finally {
                stop();
            }
        }


        void stop() {

            try {
                clientList.remove(this);
                connection.close();

            } catch (IOException e) {
                System.err.println(GameStrings.ERROR);
            }
        }

        void writeMessage(String message) {
            toClients.println(message);
        }
    }
}


