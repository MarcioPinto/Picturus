package org.academiadecodigo.haltistas.Server;

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


    public Server(int port) throws IOException {

        this.serverSocket = new ServerSocket(port);
        this.clientList = new HashSet<>();
    }


    public void start() throws IOException {

        ExecutorService service = Executors.newCachedThreadPool();

        while (true) {

            Socket clientSocket = serverSocket.accept();
            clientHandler handler = new clientHandler(clientSocket);

            clientList.add(handler);
            service.submit(handler);
            System.out.println("Connection with new client @ " + clientSocket + "\n");
        }
    }


    private void broadcast(String message) {
        for (clientHandler s : clientList) {
            s.writeMessage(message);

        }
    }


    private class clientHandler implements Runnable {

        private Socket connection;
        private PrintWriter toClients;
        private BufferedReader fromClients;

        clientHandler(Socket clientSocket) {
            this.connection = clientSocket;
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
                        broadcast("Player disconnected.");
                        break;
                    }

                    broadcast(message);
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


