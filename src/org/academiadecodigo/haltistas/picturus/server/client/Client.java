package org.academiadecodigo.haltistas.picturus.server.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {


    private String hostName;
    private int portNumber;

    private Socket socket;

    private BufferedReader fromServer;
    private PrintWriter toServer;

    public Client(String hostName, int portNumber) {
        this.hostName = hostName;
        this.portNumber = portNumber;
    }

    //init communication
    public void init() throws IOException {

        socket = new Socket(hostName, portNumber);

        toServer = new PrintWriter(socket.getOutputStream(), true);
        fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));

    }

}
