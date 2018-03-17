package org.academiadecodigo.haltistas.picturus.server.client;

import org.academiadecodigo.haltistas.picturus.server.client.controllers.KeyboardController;
import org.academiadecodigo.haltistas.picturus.server.client.graphics.Draw;

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

    private Draw draw;

    public Client(String hostName, int portNumber) {
        this.hostName = hostName;
        this.portNumber = portNumber;

        draw = new Draw(this);
    }

    //init communication
    public void init() throws IOException {

        socket = new Socket(hostName, portNumber);

        toServer = new PrintWriter(socket.getOutputStream(), true);
        fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));

    }


    public void send(String message) {
        toServer.println(message);
    }


    public void receive() {
        try {

            String message = fromServer.readLine();

            checkMessage(message);

            draw.receive(message);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private String checkMessage(String message) {
        //checks how to interpret message

        return message;
    }

    public void drawTosend(char key) {
        draw.drawToSend(key);
    }

    public void drawDelete() {
        draw.delete();
    }

    public void drawSend() {
        draw.send();
    }

}
