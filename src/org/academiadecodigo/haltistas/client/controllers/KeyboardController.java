package org.academiadecodigo.haltistas.client.controllers;

import com.sun.org.apache.regexp.internal.RE;
import org.academiadecodigo.haltistas.client.Client;
import org.academiadecodigo.simplegraphics.keyboard.Keyboard;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEvent;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEventType;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardHandler;

import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.List;


public class KeyboardController implements KeyboardHandler, Runnable {


    private Client client;
    private List<Character> prohibitedChar;

    public KeyboardController(Client client) {
        this.client = client;
        this.prohibitedChar = new LinkedList<>();
    }


    public void addProhibitedChars() {
        prohibitedChar.add(KeyEvent.CHAR_UNDEFINED);
        prohibitedChar.add('´');
        prohibitedChar.add('`');
        prohibitedChar.add('~');
        prohibitedChar.add('^');
    }


    @Override
    public void run() {

        addProhibitedChars();

        Keyboard k = new Keyboard(this);

        KeyboardEvent event = new KeyboardEvent();
        event.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        k.addEventListener(event);


    }

    @Override
    public void keyPressed(char c) {
        if (prohibitedChar.contains(c)) {
            return;
        }
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
