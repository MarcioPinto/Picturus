package org.academiadecodigo.haltistas.server.game;

import org.academiadecodigo.haltistas.GameCommand;
import org.academiadecodigo.haltistas.server.Server;

import java.util.*;

public class PicturusGame implements Runnable {


    private final static int ROUND_TIME = 60;
    private final static int WAIT_TIME = 5;

    private static final int FREQUENCY = 1000;

    private Server server;
    private List<String> playerList;
    private LinkedList<String> waitingQueue;
    private String gameWord;
    private int minPlayers;

    private Timer timer;
    private RoundTimer roundTime;
    private boolean gameIsRunning;


    public PicturusGame(Server server) {

        this.server = server;
        this.playerList = new ArrayList<>();
        this.waitingQueue = new LinkedList<>();
        this.minPlayers = 3;
        timer = new Timer();
    }


    @Override
    public void run() {

        while (true) {

            fillQueue();
            System.out.println("preparing the game");
            prepareGame();
        }
    }

    private void fillQueue() {

        synchronized (this) {
            while (waitingQueue.size() < minPlayers
                    || waitingQueue.isEmpty() || gameIsRunning) {

                System.out.println(gameIsRunning);
                try {
                    System.out.println("waking up...");
                    this.wait();

                    initMessages();

                } catch (InterruptedException e) {
                    //no handling is required for the interruption
                }
            }
        }
    }

    private void prepareGame() {
        synchronized (this) {
            playerOnQueue();
            server.broadcast(Encoder.info("Starting game!"), playerList);
            wordToDraw();
            roundTime = new RoundTimer();
            timer.scheduleAtFixedRate(roundTime, 0, FREQUENCY);
        }
    }

    private void playerOnQueue() {
        synchronized (this) {
            while (!waitingQueue.isEmpty()) {

                String name = waitingQueue.poll();
                server.whisper(name, Encoder.info(GameCommand.NEW_ROUND));
                playerList.add(name);
            }
        }
    }


    public void drawMessage(String message) {
        server.broadcast(Encoder.draw(message), playerList);
    }

    public void chatMessage(String message) {
        wordCheck(message);
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


    public void drawingPlayer() {

        Collections.shuffle(playerList);
        String toSend = Encoder.activePlayer(gameWord);
        server.whisper(playerList.get(0), toSend);
    }

    private void endGame() {
        synchronized (this) {
            gameIsRunning = false;
        }
    }

    /**
     * compares the gameWord with the words sent by the chat with /CHAT/
     */
    public void wordCheck(String wordGuess) {

        if (wordGuess.equals(gameWord)) {
            server.broadcast(Encoder.chat(GameCommand.CORRECT_WORD + " : " + gameWord), playerList);
            server.broadcast(Encoder.reset(), playerList);
            roundTime.cancel();
            endGame();
            restartGame();

        }
    }

    private void initMessages() {
        server.whisper(waitingQueue.get(waitingQueue.size() - 1),
                Encoder.info(GameCommand.NOT_ENOUGH_PLAYERS));
    }

    private void restartGame() {
        synchronized (this) {
            waitingQueue.addAll(playerList);
            playerList.clear();
            PicturusGame.this.notifyAll();
            System.out.println("in here");
        }
    }

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
                System.out.println("canceling");
                this.cancel();
                endGame();
                timer.scheduleAtFixedRate(new WaitingTimer(), 0, FREQUENCY);
            }
        }
    }


    private class WaitingTimer extends TimerTask {

        private int seconds = WAIT_TIME;

        @Override
        public void run() {

            seconds--;
            System.out.println(seconds);

            if (seconds < 0) {
                System.out.println("canceling");
                this.cancel();
                restartGame();
            }
        }
    }


}
