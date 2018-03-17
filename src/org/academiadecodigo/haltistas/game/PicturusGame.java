package org.academiadecodigo.haltistas.game;


import org.academiadecodigo.haltistas.Server.Server;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class PicturusGame {

    private Server server;
    private List<String> playerList;
    private String gameWord;

    public PicturusGame(Server server) {

        this.server = server;
        this.playerList = new ArrayList<>();
    }

    /**
     * adds the players to the playerList
     * @param playerName
     */
    public void addPlayer(String playerName) {
        playerList.add(playerName);
    }

    /**
     * gets the word from the list and sends it to the Drawing Player
     * sends it through server whisper
     */
    public void wordToDraw() {
        // invoke bruno method
    }

    /**
     * compares the gameWord with the words sent by the chat with /CHAT/
     */
    public void wordCheck(String wordGuess) {


    }





}
