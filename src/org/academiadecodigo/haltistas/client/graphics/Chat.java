package org.academiadecodigo.haltistas.client.graphics;

import org.academiadecodigo.haltistas.client.Client;
import org.academiadecodigo.simplegraphics.graphics.Text;

import java.util.LinkedList;
import java.util.List;

//TODO get ride of magic numbers
//TODO remove special keys characters

public class Chat {

    private Client client;

    private String messageToSend;

    private Text sendMessage;

    private List<Text> history;

    private boolean canWrite;


    public Chat(Client client) {

        this.client = client;
        this.messageToSend = "";

        this.sendMessage = new Text(420, 390, messageToSend);
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

        for (Text text : history) {
            text.translate(0, -20);
        }

        Text receivedMessage = new Text(420, 370, message);
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
