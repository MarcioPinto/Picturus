package org.academiadecodigo.haltistas.picturus.server.client.controllers;

import org.academiadecodigo.haltistas.picturus.server.client.graphics.Draw;
import org.academiadecodigo.simplegraphics.keyboard.Keyboard;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEvent;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEventType;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardHandler;

public class KeyboardController implements KeyboardHandler, Runnable {


    private Draw draw;

    public KeyboardController(Draw draw) {
        this.draw = draw;
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
        draw.drawToSend(c);
    }

    @Override
    public void keyReleased(KeyboardEvent keyboardEvent) {

    }

    @Override
    public void backspace() {
        draw.delete();
    }

    @Override
    public void enter() {
        draw.send();
    }
}
