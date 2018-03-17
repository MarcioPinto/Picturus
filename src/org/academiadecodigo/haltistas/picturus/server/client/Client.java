package org.academiadecodigo.haltistas.picturus.server.client;

import org.academiadecodigo.haltistas.picturus.server.client.controllers.KeyboardController;
import org.academiadecodigo.haltistas.picturus.server.client.graphics.Draw;
import org.academiadecodigo.simplegraphics.graphics.Canvas;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    private final static int PADDING = 10;

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

        Rectangle rectangle = new Rectangle(PADDING, PADDING, 400, 400);
        rectangle.draw();

        new Thread(new InputHandler()).start();

    }


    public void send(String message) {
        toServer.println(message);
    }


    private String checkMessage(String message) {
        //checks how to interpret message

        return message;
    }

    public void drawToSend(char key) {
        draw.drawToSend(key);
    }

    public void drawDelete() {
        draw.delete();
    }

    public void drawSend() {
        draw.send();
    }


    private class InputHandler implements Runnable {


        @Override
        public void run() {

            while (!socket.isClosed()) {


                try {

                    String message = fromServer.readLine();
                    System.out.println("Shapes in Canvas: " + Canvas.getInstance().getShapes().size());

                    if (message == null) {
                        break;
                    }

                    if (message.isEmpty()) {
                        continue;
                    }

                    receivedMessage(message);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public void receivedMessage(String message) {

            String[] point = message.split(" ");

            double IniX = Double.parseDouble(point[0]);
            double IniY = Double.parseDouble(point[1]);
            double FinX = Double.parseDouble(point[2]);
            double FinY = Double.parseDouble(point[3]);

            startDrawing(IniX, IniY, FinX, FinY);


            checkMessage(message);

            draw.receive(message);

        }
    }


}
