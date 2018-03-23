package org.academiadecodigo.haltistas.client.controllers;

import org.academiadecodigo.haltistas.GameStrings;
import org.academiadecodigo.haltistas.client.Client;
import org.academiadecodigo.haltistas.client.utils.Constants;
import org.academiadecodigo.simplegraphics.mouse.Mouse;
import org.academiadecodigo.simplegraphics.mouse.MouseEvent;
import org.academiadecodigo.simplegraphics.mouse.MouseEventType;
import org.academiadecodigo.simplegraphics.mouse.MouseHandler;


public class MouseController implements MouseHandler {


    private Client client;

    private MouseEvent event;

    private int count;

    private boolean canDraw;

    public MouseController(Client client) {
        this.client = client;
        this.canDraw = false;
    }


    public void init() {

        Mouse mouse = new Mouse(this);

        mouse.addEventListener(MouseEventType.MOUSE_PRESSED);
        mouse.addEventListener(MouseEventType.MOUSE_RELEASE);
        mouse.addEventListener(MouseEventType.MOUSE_DRAGGED);
    }


    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

        if (!canDraw){
            return;
        }

        event = mouseEvent;
    }


    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

        if(!canDraw){
            return;
        }

        sendLine(event, mouseEvent);
        event = null;
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {

        if (!canDraw) {
            return;
        }

        if (!controlBorders(mouseEvent)){
            event = null;
            return;
        }

        if (event == null) {
            event = mouseEvent;
            return;
        }

        count++;

        if (count <= 0 || count % 3 != 0) {
            return;
        }
        sendLine(event, mouseEvent);

        event = mouseEvent;
    }

    private void sendLine(MouseEvent initial, MouseEvent end) {

        String lineCoordinates = GameStrings.DRAW + initial.getX() + " " + initial.getY() + " " +
               end.getX() + " " + end.getY() + "\n";

        client.sendToServer(lineCoordinates);
    }

    private boolean controlBorders(MouseEvent mouseEvent) {

        if (mouseEvent.getX() < Constants.PADDING || mouseEvent.getX() > Constants.BORDER_RIGHT||
                mouseEvent.getY() < Constants.PADDING || mouseEvent.getY() > Constants.BORDER_DOWN) {

            return false;
        }
        return true;
    }

    public void setCanDraw(boolean canDraw) {
        this.canDraw = canDraw;
    }

}
