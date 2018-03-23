package org.academiadecodigo.haltistas.client;

import org.academiadecodigo.haltistas.client.controllers.MouseController;
import org.academiadecodigo.haltistas.client.graphics.Chat;
import org.academiadecodigo.haltistas.client.graphics.Pencil;
import org.academiadecodigo.haltistas.client.utils.Constants;
import org.academiadecodigo.haltistas.client.utils.Receive;
import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Line;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;

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
        new Thread(new InputHandler(this)).start();
    }

    private void canvasInit() {

        Rectangle chatRectangle = new Rectangle(Constants.CHAT_PADDING_X, Constants.PADDING,
                Constants.CHAT_BOARD_X, Constants.CHAT_BOARD_Y);
        chatRectangle.setColor(Color.WHITE);
        chatRectangle.draw();

        Line chatLine = new Line(Constants.CHAT_LINE_INI_X, Constants.CHAT_LINE_INI_Y,
                Constants.CHAT_LINE_FIN_X, Constants.CHAT_LINE_FIN_Y);
        chatLine.setColor(Color.WHITE);
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

    public void reset() {
        mouseController.setCanDraw(false);
        chat.setCanWrite(true);
        pencil.deleteAll();

    }

    public void eraseDraw() {
        pencil.deleteAll();
    }

    public void sendToEraseAll() {
        if (chat.isCanWrite()) {
            return;
        }
        sendToServer("/ERASE/");
    }

    private class InputHandler implements Runnable {

        private Client client;

        public InputHandler(Client client) {
            this.client = client;
        }

        @Override
        public void run() {

            try {

                while (socket.isConnected()) {


                    String message = fromServer.readLine();

                    if (message == null) {
                        break;
                    }

                    if (message.isEmpty()) {
                        continue;
                    }

                    Receive.receivedFromServer(message, pencil, chat, mouseController, client);

                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                closeSocket();
            }
        }
    }
}