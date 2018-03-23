package org.academiadecodigo.haltistas.client.graphics;

import org.academiadecodigo.haltistas.client.Client;
import org.academiadecodigo.haltistas.client.utils.Constants;
import org.academiadecodigo.simplegraphics.graphics.Text;

import java.util.LinkedList;
import java.util.List;

public class Chat {

    private Client client;

    private String messageToSend;

    private Text sendMessage;

    private List<Text> history;

    private int timesTranslate;

    private boolean canWrite;


    public Chat(Client client) {

        this.client = client;
        this.messageToSend = "";

        this.sendMessage = new Text(Constants.POSX_TEXT, Constants.POSY_TEXT_TO_SEND, messageToSend);
        this.sendMessage.draw();

        this.history = new LinkedList<>();

        this.timesTranslate = 0;

        this.canWrite = true;
    }


    public void write(char key) {

        if (!canWrite) {
            return;
        }

        if (messageToSend.length() == Constants.CHAT_CHAR_LIMIT) {
            return;
        }
        messageToSend += key;
        sendMessage.setText(messageToSend);
    }


    public void send() {

        if (messageToSend.equals("")) {
            return;
        }

        String finalMessage = "/CHAT/ " + messageToSend;
        client.sendToServer(finalMessage);
        messageToSend = "";
        sendMessage.setText(messageToSend);
    }


    public void receive(String message) {

        if (timesTranslate >= Constants.CANVAS_CHAT_LIMIT) {
            history.get(0).delete();
            history.remove(0);
        }
        timesTranslate++;
        int ypixToTranslate = -20;

        int posyTextToReceive = Constants.POSY_TEXT_TO_SEND + ypixToTranslate;

        for (Text text : history) {
            text.translate(0, ypixToTranslate);
        }

        Text receivedMessage = new Text(Constants.POSX_TEXT, posyTextToReceive, message);
        history.add(receivedMessage);
        receivedMessage.draw();
        System.out.println(timesTranslate);
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

    public boolean isCanWrite() {
        return canWrite;
    }
}
