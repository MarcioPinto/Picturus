package org.academiadecodigo.haltistas.client.graphics;

import org.academiadecodigo.haltistas.GameCommand;
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

    private boolean canDraw;

    public Draw(Client client) {
        this.client = client;
        this.messageToSend = "";

        sendMessage = new Text(420, 400, messageToSend);
        sendMessage.draw();

        history = new LinkedList<>();

        canDraw = true;
    }


    public void drawToSend(char key) {
        messageToSend += key;
        sendMessage.setText(messageToSend);
    }


    public void send() {

        String finalMessage = GameCommand.CHAT + messageToSend;
        client.send(finalMessage);
        messageToSend = "";
    }


    public void receive(String message) {


        for (Text text : history) {
            text.translate(0, -20);
        }

        receivedMessage = new Text(420, 380, message);
        history.add(receivedMessage);
        receivedMessage.draw();
    }


    //TODO corrigir backspace para quando nao tem nada para apagar

    public void delete() {
        messageToSend = messageToSend.substring(0, messageToSend.length() - 1);
        sendMessage.setText(messageToSend);
    }

    public void setCanDraw(boolean canDraw) {
        this.canDraw = canDraw;
    }
}
