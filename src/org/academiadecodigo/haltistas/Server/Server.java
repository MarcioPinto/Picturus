package org.academiadecodigo.haltistas.Server;

import org.academiadecodigo.haltistas.GameLogic;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private ServerSocket serverSocket;
    private GameLogic game;

    public Server(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
        this.game = new GameLogic();

    }

    public void start() throws IOException {
        ExecutorService service = Executors.newCachedThreadPool();

        while (true) {

            Socket clientSocket = serverSocket.accept();

            ClientHandler clientHandler = new ClientHandler(clientSocket,game);
            game.addClient(clientHandler);
            service.submit(clientHandler);
            System.out.println("Connection with new client @ " + clientSocket + "\n");
        }
    }

}


