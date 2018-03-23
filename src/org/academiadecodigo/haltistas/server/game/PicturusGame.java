package org.academiadecodigo.haltistas.server.game;

import org.academiadecodigo.haltistas.GameStrings;
import org.academiadecodigo.haltistas.server.GameConstants;
import org.academiadecodigo.haltistas.server.Server;

import java.util.*;

public class PicturusGame implements Runnable {


    private final static int ROUND_TIME = 60;
    private final static int WAIT_TIME = 30;

    private static final int FREQUENCY = 1000;

    private Server server;
    private List<String> playerList;
    private LinkedList<String> waitingQueue;
    private String gameWord;
    private int minPlayers;
    private int randomNumber;
    private Score score;

    private Timer timer;
    private RoundTimer roundTime;
    private boolean gameIsRunning;


    public PicturusGame(Server server) {

        this.server = server;
        this.playerList = new ArrayList<>();
        this.waitingQueue = new LinkedList<>();
        this.minPlayers = 3;
        timer = new Timer();
        score = new Score();
    }


    @Override
    public void run() {

        while (true) {
            fillQueue();
            prepareGame();
        }
    }

    /**
     * Doesn't allow the game to start until there is a minimum number of players
     * Doesn't allow the game to start if the game had already started
     */
    private void fillQueue() {

        synchronized (this) {
            while (waitingQueue.size() < minPlayers
                    || waitingQueue.isEmpty() || gameIsRunning) {

                try {
                    this.wait();
                    initMessages();

                } catch (InterruptedException e) {
                    //no handling is required for the interruption
                }
            }
        }
    }

    /**
     * Retrieves and removes the first player from the waiting queue
     * Tells all the the players that the game is starting
     */
    private void prepareGame() {
        synchronized (this) {
            playerOnQueue();
            server.broadcast(Encoder.info(GameStrings.START), playerList);
            wordToDraw();
            roundTime = new RoundTimer();
            timer.scheduleAtFixedRate(roundTime, 0, FREQUENCY);
        }
    }

    /**
     * Assigns a "name" to the players
     */
    private void playerOnQueue() {
        synchronized (this) {
            while (!waitingQueue.isEmpty()) {

                String name = waitingQueue.poll();
                server.whisper(name, Encoder.info(GameStrings.NEW_ROUND));
                playerList.add(name);
                score.addNameScore(name);
            }
        }
    }

    public void removePlayer(String name) {
        System.out.println("Before ");
        playerList.remove(name);
        score.removePlayer(name);
        waitingQueue.remove(name);

    }


    public void eraseDraw(String message) {
        server.broadcast(message, playerList);
    }

    /**
     * Sends the drawing to all all the players
     *
     * @param message
     */
    public void drawMessage(String message) {
        server.broadcast(Encoder.draw(message), playerList);
    }

    /**
     * Sends the messages from the players to all the players
     * Compares the words with the Game Word
     *
     * @param message
     */
    public void chatMessage(String message) {
        server.broadcast(Encoder.chat(message), playerList);
    }

    /**
     * adds the players to the playerList
     *
     * @param playerName
     */
    public void addPlayer(String playerName) {
        synchronized (this) {
            waitingQueue.offer(playerName);
            notifyAll();
        }
    }

    /**
     * gets the word from the list and sends it to the Drawing Player
     * sends it through server whisper
     */
    public void wordToDraw() {
        gameWord = GameWords.getWord();
        drawingPlayer();
    }

    /**
     * Gets a random player from the list of players
     * Sends the Game Word to the player
     */
    public void drawingPlayer() {

        randomNumber = (int) Math.floor(Math.random() * playerList.size());

        String toSend = Encoder.activePlayer(gameWord);
        server.whisper(playerList.get(randomNumber), toSend);
    }

    /**
     * Turns gameIsRunning to false
     */
    private void endGame() {
        synchronized (this) {
            gameIsRunning = false;
        }
    }

    /**
     * Compares the Game Word with the words sent by the chat with /CHAT/
     * If the word is the same, sends a message to the players
     * Stops the Round Time
     * Ends the Game
     * Restarts the Game
     */
    public void wordCheck(String wordGuess, String name) {

        System.out.println("Checking word");
        if (wordGuess.equals(gameWord)) {

            score.add(name, GameConstants.SCORE_TO_GUESS);
            score.add(playerList.get(randomNumber), GameConstants.SCORE_TO_DRAW);
            score.test();

            server.broadcast(Encoder.chat(GameStrings.CORRECT_WORD + " : " + gameWord), playerList);
            server.broadcast(Encoder.reset(), playerList);

            server.broadcast(Encoder.info(score.transform()), playerList);

            roundTime.cancel();
            endGame();
            restartGame();

        }

    }

    /**
     * Tells the players that there's not enough players for the game to start
     */
    private void initMessages() {

        server.whisper(waitingQueue.get(waitingQueue.size() - 1),
                Encoder.info(GameStrings.NOT_ENOUGH_PLAYERS));
    }

    /**
     * Restarts the Game
     * Adds all the waiting players
     */
    private void restartGame() {
        synchronized (this) {
            waitingQueue.addAll(playerList);
            playerList.clear();
            PicturusGame.this.notifyAll();
        }
    }

    /**
     * Timer for the Rounds
     */
    public class RoundTimer extends TimerTask {

        private int seconds = ROUND_TIME;

        @Override
        public void run() {

            synchronized (PicturusGame.this) {
                gameIsRunning = true;
            }

            seconds--;
            System.out.println(seconds);

            if (seconds < 0) {
                this.cancel();
                server.broadcast(Encoder.chat(GameStrings.NO_RIGHT_ANSWER), playerList);
                endGame();
                timer.scheduleAtFixedRate(new WaitingTimer(), 0, FREQUENCY);
            }
        }
    }

    /**
     * Timer for the time between the end of a round and the start of another round
     */
    private class WaitingTimer extends TimerTask {

        private int seconds = WAIT_TIME;

        @Override
        public void run() {
            seconds--;
            System.out.println(seconds);

            if (seconds < 0) {
                this.cancel();
                restartGame();
            }
        }
    }
}

