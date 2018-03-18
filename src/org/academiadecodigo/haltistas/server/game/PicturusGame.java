package org.academiadecodigo.haltistas.server.game;

import org.academiadecodigo.haltistas.GameCommand;
import org.academiadecodigo.haltistas.server.Server;

import java.util.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PicturusGame {

    private Server server;
    private List<String> playerList;
    private Queue<String> newPLayer;
    private String gameWord;
    private boolean correctGuess;

    public PicturusGame(Server server) {

        this.server = server;
        this.playerList = new ArrayList<>();
        this.newPLayer = new PriorityQueue<>();
        this.correctGuess = false;
    }

    public void prepareGame() {

        for (String player : playerList) {
            newPLayer.addAll(Collections.singleton(player));
        }
        System.out.println(playerList);

        if (newPLayer.size() < 4) {
            System.out.println(GameCommand.NOT_ENOUGH_PLAYERS);
            return;
        }
        //TODO Implement clock to wait 30 seconds until round starts.
        System.out.println(GameCommand.NEW_ROUND);
        startGame();

    }
    //TODO Synchronize method, make players that enter after round start wait , and notify them when round ends.
    public void startGame() {

    //TODO add second condition when time hits 0 .
        if (correctGuess ) {
            correctGuess = false;
            notifyAll();
            prepareGame();
        }
    }


    public void drawMessage(String message) {
        server.broadcast(Encoder.draw(message));
    }


    public void chatMessage(String message) {

        //TODO make the condition with the word starting with ! to differentiate from regular chat message
        // wordCheck(message);

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
//TODO HERE IS WHERE THE WORD IS COMPARED FROM !<word>
        for (String aPlayerList : playerList) {

            if (gameWord.equalsIgnoreCase(wordGuess)) {
                System.out.println("PLAYER: " + aPlayerList + GameCommand.CORRECT_WORD);
                correctGuess = true;
            }

        }
    }
}
