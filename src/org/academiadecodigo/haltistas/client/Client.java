package org.academiadecodigo.haltistas.client;

import org.academiadecodigo.haltistas.client.controllers.MouseController;
import org.academiadecodigo.haltistas.client.graphics.Chat;
import org.academiadecodigo.haltistas.client.graphics.Pencil;
import org.academiadecodigo.simplegraphics.graphics.Line;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    //TODO create constants class

    private final static int PADDING = 10;
    public final static int DRAWING_BOARD_X = 400;
    public final static int DRAWING_BOARD_Y = 400;
    public final static int CHAT_BOARD_X = 500;
    public final static int CHAT_BOARD_Y = 400;

    public final static int CHAT_LINE_INI_X = 410;
    public final static int CHAT_LINE_INI_Y = 390;
    public final static int CHAT_LINE_FIN_X = 910;
    public final static int CHAT_LINE_FIN_Y = 390;


    private String hostName;
    private int portNumber;

    private Socket socket;

    private BufferedReader fromServer;
    private PrintWriter toServer;

    private Chat chat;
    private Pencil pencil;

    private MouseController mouseController;


    public Client(String hostName, int portNumber) {
        this.hostName = hostName;
        this.portNumber = portNumber;

        this.chat = new Chat(this);
        this.pencil = new Pencil();
    }


    public void setMouseController(MouseController mouseController) {
        this.mouseController = mouseController;
    }


    //init communication
    public void init() throws IOException {

        canvasInit();

        socket = new Socket(hostName, portNumber);

        toServer = new PrintWriter(socket.getOutputStream(), true);
        fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        new Thread(new InputHandler()).start();
    }

    private void canvasInit() {

        Rectangle rectangle = new Rectangle(PADDING, PADDING, DRAWING_BOARD_X, DRAWING_BOARD_Y);
        rectangle.draw();
        Rectangle chatRectangle = new Rectangle(PADDING + DRAWING_BOARD_X, PADDING, CHAT_BOARD_X, CHAT_BOARD_Y);
        chatRectangle.draw();
        Line chatLine = new Line(CHAT_LINE_INI_X, CHAT_LINE_INI_Y, CHAT_LINE_FIN_X, CHAT_LINE_FIN_Y);
        chatLine.draw();
    }


    public void sendToServer(String message) {
        toServer.println(message);
    }


    public void drawToSend(char key) {
        chat.write(key);
    }


    public void drawDelete() {
        chat.delete();
    }


    public void drawSend() {
        chat.send();
    }

    public void closeSocket() {

        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private class InputHandler implements Runnable {

        @Override
        public void run() {

            while (!socket.isClosed()) {

                try {

                    String message = fromServer.readLine();

                    if (message == null) {
                        break;
                    }

                    if (message.isEmpty()) {
                        continue;
                    }

                    receivedFromServer(message);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        //TODO change this method to utility thing
        private void receivedFromServer(String message) {


            String[] str = message.split(" ");

            switch (str[0]) {

                case "/DRAW/":

                    //TODO create method to do the message replace thing

                    message = messagePeel(str[0], message);

                    String[] point = message.split(" ");

                    double IniX = Double.parseDouble(point[0]);
                    double IniY = Double.parseDouble(point[1]);
                    double FinX = Double.parseDouble(point[2]);
                    double FinY = Double.parseDouble(point[3]);

                    pencil.draw(IniX, IniY, FinX, FinY);
                    break;

                case "/CHAT/":

                    message = messagePeel(str[0], message);

                    chat.receive(message);
                    break;


                case "/ACTIVE/":

                    mouseController.setCanDraw(true);
                    chat.setCanWrite(false);

                    message = messagePeel(str[0], message);

                    chat.receive("WORD IN PLAY! DRAW THIS SHIT: " + message);
                    break;

                case "/INFO/":

                    message = messagePeel(str[0], message);

                    chat.receive(message);
                    break;

                case "/RESET/":
                    //TODO reset

                    break;
            }
        }

        private String messagePeel(String key, String message) {

            message = message.replaceFirst(key, "");
            message = message.substring(message.indexOf(" ") + 1);

            return message;
        }
    }
}
