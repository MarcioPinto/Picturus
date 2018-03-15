package org.academiadecodigo.haltistas.picturus.server;

import java.util.HashSet;
import java.util.Set;

public class GameLogic {


    private Set<ClientHandler> clientList;

    public GameLogic() {
       this.clientList = new HashSet<>();
    }


    public void broadcast(String message) {
        for (ClientHandler s : clientList) {
            s.writeMessage(message);

        }
    }

    public void addClient(ClientHandler clientHandler){
        clientList.add(clientHandler);
    }

    public void removeClient(ClientHandler clientHandler){
        clientList.remove(clientHandler);
    }


}
