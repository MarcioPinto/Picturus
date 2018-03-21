package org.academiadecodigo.haltistas.client.graphics;

import org.academiadecodigo.haltistas.client.Client;
import org.academiadecodigo.simplegraphics.graphics.Text;

import java.util.LinkedList;
import java.util.List;

public class Chat {

    private final int POSX_TEXT = 420;
    private final int POSY_TEXT_TO_SEND = 390;


    private Client client;

    private String messageToSend;

    private Text sendMessage;

    private List<Text> history;

    private boolean canWrite;


    public Chat(Client client) {

        this.client = client;
        this.messageToSend = "";

        this.sendMessage = new Text(POSX_TEXT, POSY_TEXT_TO_SEND, messageToSend);
        this.sendMessage.draw();

        this.history = new LinkedList<>();

        this.canWrite = true;
    }


    public void write(char key) {

        if (!canWrite) {
            return;
        }
        messageToSend += key;
        sendMessage.setText(messageToSend);
    }


    public void send() {

        String finalMessage = "/CHAT/ " + messageToSend;
        client.sendToServer(finalMessage);
        messageToSend = "";
        sendMessage.setText(messageToSend);
    }


    public void receive(String message) {

        int ypixToTranslate = -20;

        int posyTextToReceive = POSY_TEXT_TO_SEND - ypixToTranslate;

        for (Text text : history) {
            text.translate(0, ypixToTranslate);
        }

        Text receivedMessage = new Text(POSX_TEXT, posyTextToReceive, message);
        history.add(receivedMessage);
        receivedMessage.draw();
    }


    public void delete() {
        if (messageToSend.equals("")) {
            return;
        }

        messageToSend = messageToSend.substring(0, messageToSend.length() - 1);
        sendMessage.setText(messageToSend);
    }

    public void setCanWrite(boolean canWrite) {
        this.canWrite = canWrite;
    }
}
