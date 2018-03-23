package org.academiadecodigo.haltistas.server.game;

import org.academiadecodigo.haltistas.GameStrings;


public class Encoder {

    public static String draw(String message) {

        return GameStrings.DRAW + message;
    }

    public static String chat(String message) {

        return GameStrings.CHAT + message + "\n";
    }

    public static String activePlayer(String message) {

        return GameStrings.ACTIVE_PLAYER + message;
    }

    public static String info(String message) {

        return GameStrings.INFO + message + "\n";
    }

    public static String reset(){
        return GameStrings.RESET ;
    }

    public static String score(String message){
        return  "/SCORE/"+ message;
    }

}
