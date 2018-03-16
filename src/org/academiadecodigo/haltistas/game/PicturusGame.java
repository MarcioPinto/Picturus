package org.academiadecodigo.haltistas.game;


import org.academiadecodigo.haltistas.Server.Server;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class PicturusGame {

    private Server server;
    private List<String> playerList;

    public PicturusGame(Server server) {

        this.server = server;
        this.playerList = new ArrayList<>();
    }

    public void addPlayer(String playerName) {
        playerList.add(playerName);
    }
       
}
