package org.academiadecodigo.haltistas.server;

public class Decoder {

    private String message;
    private String command;
    private String information;

    public Decoder(String message) {
        this.message = message;
    }

    public void decode() {
        System.out.println(message.substring(1, 5));
        command = message.substring(1, 5);

        information = message.substring(6);
        System.out.println("this is the message: " + information);
    }

    public String sendAnswer() {
        switch (command) {
            case "chat":
                //TODO this need revision to verify
                return information;


            case "draw":
                //TODO this need revision to verify
                return information;

            default:
                System.err.println("the command on the message is fuck up");
        }
        return null;
    }
}
