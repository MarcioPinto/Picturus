package org.academiadecodigo.haltistas.server.game;

import org.academiadecodigo.haltistas.GameCommand;
import org.academiadecodigo.haltistas.server.Server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;

public class PicturusGame {

    private Server server;
    private List<String> playerList;
    private Queue<String> newPLayer;
    private String gameWord;
    private boolean correctGuess;

    public PicturusGame(Server server) {

        this.server = server;
        this.playerList = new ArrayList<>();
    }

    public void perpareGame() {

        for (String player : playerList) {
            newPLayer.addAll(Collections.singleton(player));
        }

        if (newPLayer.size() >= 3) {
            System.out.println(GameCommand.NEW_ROUND);
            startGame();
        }

        System.out.println(GameCommand.NOT_ENOUGH_PLAYERS);

    }

    public void startGame() {

        correctGuess = false;


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
