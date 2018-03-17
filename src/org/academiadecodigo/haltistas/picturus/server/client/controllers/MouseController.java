package org.academiadecodigo.haltistas.picturus.server.client.controllers;

import org.academiadecodigo.haltistas.picturus.server.client.Client;
import org.academiadecodigo.simplegraphics.mouse.Mouse;
import org.academiadecodigo.simplegraphics.mouse.MouseEvent;
import org.academiadecodigo.simplegraphics.mouse.MouseEventType;
import org.academiadecodigo.simplegraphics.mouse.MouseHandler;

public class MouseController implements MouseHandler {


    public static final double MOUSE_ADJST_X = -7;
    public static final double MOUSE_ADJST_Y = -30;


    private Client client;

    private double XIni;
    private double YIni;

    private double XFin;
    private double YFin;
    private int count;


    public MouseController(Client client) {
        this.client = client;
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

        XIni = mouseEvent.getX();
        YIni = mouseEvent.getY();

        System.out.println(mouseEvent);
    }


    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

        XFin = mouseEvent.getX();
        YFin = mouseEvent.getY();

    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {

        count++;

        if (count <= 0 || count % 3 != 0) {
            System.out.println("in here");
            return;
        }

        String str = "/DRAW/" + " " + String.valueOf(XIni) + " " + String.valueOf(YIni) + " " + String.valueOf(mouseEvent.getX()) + " " + String.valueOf(mouseEvent.getY()) + "\n";
        System.out.println(str);

        client.send(str);

        XIni = mouseEvent.getX();
        YIni = mouseEvent.getY();


        //System.out.println(mouseEvent);
    }


}
