package org.academiadecodigo.haltistas.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private ServerSocket serverSocket;
    private Set<VerySpecialHandler> clientList;


    public Server(int port) throws IOException {

        this.serverSocket = new ServerSocket(port);
        this.clientList = new HashSet<>();
    }


    public void start() throws IOException {

        ExecutorService service = Executors.newCachedThreadPool();

        while(true){

            Socket clientSocket = serverSocket.accept();
            VerySpecialHandler handler = new VerySpecialHandler(clientSocket);

            clientList.add(handler);
            service.submit(handler);
            System.out.println("Connection with new client @ " + clientSocket + "\n");
        }
    }


    private void broadcast() {
        for (VerySpecialHandler s : clientList) {
            s.writeMessage(message);

        }
    }


    private class VerySpecialHandler implements Runnable {

        private Socket connection;
        private PrintWriter toClients;
        private BufferedReader fromClients;

        VerySpecialHandler(Socket clientSocket) {
            this.connection = clientSocket;
        }

        @Override
        public void run() {

        }

        void stop(){
            try{
                clientList.remove(this);
                connection.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        void writeMessage(String message){
            toClients.println(message);
        }
    }

}


