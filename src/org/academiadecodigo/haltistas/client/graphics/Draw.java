package org.academiadecodigo.haltistas.client.graphics;

import org.academiadecodigo.haltistas.client.Client;
import org.academiadecodigo.simplegraphics.graphics.Text;

import java.util.LinkedList;
import java.util.List;

public class Draw {

    private Client client;

    private String messageToSend;

    private Text sendMessage;
    private Text receivedMessage;

    private List<Text> history;

    private boolean canWrite;

    public Draw(Client client) {
        this.client = client;
        this.messageToSend = "";

        sendMessage = new Text(420, 390, messageToSend);
        sendMessage.draw();

        history = new LinkedList<>();

        canWrite = true;
    }


    public void write(char key) {

        if (!canWrite){
            return;
        }
        messageToSend += key;
        sendMessage.setText(messageToSend);
    }


    public void send() {

        String finalMessage = "/CHAT/ " + messageToSend;
        client.send(finalMessage);
        messageToSend = "";
        sendMessage.setText(messageToSend);
    }


    public void receive(String message) {


        for (Text text : history) {
            text.translate(0, -20);
        }

        receivedMessage = new Text(420, 370, message);
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
