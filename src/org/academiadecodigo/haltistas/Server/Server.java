package org.academiadecodigo.haltistas.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class Server {

    private ServerSocket serverSocket;
    private Set<verySpecialHandler> clientList;


    public Server(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
        this.clientList = new HashSet<>();
    }

    public void start() {

    }

    private void broadcast() {
        for (verySpecialHandler c : clientList) {

        }
    }


    private class verySpecialHandler implements Runnable {
        private Socket connection;
        private PrintWriter toClients;
        private BufferedReader fromClients;

        @Override
        public void run() {

        }
    }
}


