package org.academiadecodigo.haltistas.client.graphics;

import org.academiadecodigo.haltistas.client.controllers.MouseController;
import org.academiadecodigo.haltistas.client.utils.Constants;
import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Line;

import java.util.LinkedList;
import java.util.List;

public class Pencil {


    private List<Line> lines;


    public Pencil() {
        lines = new LinkedList<>();

    }

    public void draw(double iniX, double iniY, double finX, double finY) {

        if (iniX > Constants.DRAWING_BOARD_X) {
            iniX = Constants.BORDER_RIGTH;
        }

        if (finX > Constants.DRAWING_BOARD_X) {
            finX = Constants.BORDER_RIGTH;
        }

        if (iniY > Constants.DRAWING_BOARD_Y) {
            iniY = Constants.BORDER_DOWN;
        }

        if (finY > Constants.DRAWING_BOARD_Y) {
            finY = Constants.BORDER_DOWN;
        }

        if (iniX < Constants.PADDING) {
            iniX = Constants.BORDER_LEFT;
        }

        if (finX < Constants.PADDING) {
            finX = Constants.BORDER_LEFT;
        }

        if (iniY < Constants.PADDING) {
            iniY = Constants.BORDER_UP;
        }

        if (finY < Constants.PADDING) {
            finY = Constants.BORDER_UP;
        }

        Line line = new Line(iniX + MouseController.MOUSE_ADJST_X, iniY + MouseController.MOUSE_ADJST_Y,
                finX + MouseController.MOUSE_ADJST_X, finY + MouseController.MOUSE_ADJST_Y);
        line.draw();
        line.setColor(Color.BLUE);

        lines.add(line);

    }

    public void deleteAll() {
        for (Line line : lines) {
            line.delete();
        }
        lines.clear();
    }
}
