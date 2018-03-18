package org.academiadecodigo.haltistas.server.game;


public class Encoder {

    public static String draw(String message){

        return "/DRAW/ " + message;
    }

    public static String chat(String message){

        return "/CHAT/ " + message;
    }

    public static String activePlayer(String message){

        return "/ACTIVE/ " + message;
    }

}
