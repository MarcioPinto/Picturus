package org.academiadecodigo.haltistas.server.game;

import org.academiadecodigo.haltistas.server.Server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PicturusGame {

    private Server server;
    private List<String> playerList;
    private String gameWord;

    public PicturusGame(Server server) {

        this.server = server;
        this.playerList = new ArrayList<>();
    }

    public synchronized void start() {

        while (true) {



        }
    }


    public void drawMessage(String message) {
        server.broadcast(Encoder.draw(message));
    }


    public void chatMessage(String message) {

        wordCheck(message);

        server.broadcast(Encoder.chat(message));
    }

    /**
     * adds the players to the playerList
     *
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
        gameWord = GameWords.getWord();
        drawingPlayer();
    }

    public void drawingPlayer() {
        Collections.shuffle(playerList);
        server.whisper(playerList.get(0), gameWord);
    }


    /**
     * compares the gameWord with the words sent by the chat with /CHAT/
     */
    public void wordCheck(String wordGuess) {


    }


}
