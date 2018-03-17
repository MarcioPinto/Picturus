package org.academiadecodigo.haltistas.client.controllers;

import org.academiadecodigo.haltistas.client.Client;
import org.academiadecodigo.simplegraphics.keyboard.Keyboard;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEvent;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEventType;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardHandler;

public class KeyboardController implements KeyboardHandler, Runnable {


    private Client client;

    public KeyboardController(Client client) {
        this.client = client;
    }


    @Override
    public void run() {

        Keyboard k = new Keyboard(this);

        KeyboardEvent event = new KeyboardEvent();
        event.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        k.addEventListener(event);


    }

    @Override
    public void keyPressed(char c) {
        client.drawToSend(c);
    }

    @Override
    public void keyReleased(KeyboardEvent keyboardEvent) {

    }

    @Override
    public void backspace() {
        client.drawDelete();
    }

    @Override
    public void enter() {
        client.drawSend();
    }

}
