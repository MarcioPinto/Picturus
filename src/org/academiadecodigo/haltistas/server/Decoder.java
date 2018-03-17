package org.academiadecodigo.haltistas.server;

public class Decoder {

    private String information;
    String[] splitedMessage;


    public void decoder(String message) {
        // TODO: /DRAW/ /NAME/ MESSAGE
        splitedMessage = message.split(" ");
        information = splitedMessage[2];

        System.out.println(splitedMessage[2]);

        sendAnswer();
    }

    public String sendAnswer() {
        switch (splitedMessage[0]) {
            case "/CHAT/":
                //TODO this need revision to verify
                return information;


            case "/DRAW/":
                //TODO this need revision to verify
                return information;

            default:
                System.err.println("the command on the message is fuck up");
        }
        return null;
    }
}
