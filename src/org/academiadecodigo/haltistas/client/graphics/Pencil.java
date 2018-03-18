package org.academiadecodigo.haltistas.client.graphics;

import org.academiadecodigo.haltistas.client.controllers.MouseController;
import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Line;

import java.util.LinkedList;
import java.util.List;

public class Pencil {


    private double XIni;
    private double YIni;

    private double XFin;
    private double YFin;
    private List<Line> lines;
    private boolean canDraw;

    public Pencil() {
        lines = new LinkedList<>();
        //TODO change to false
        canDraw = true;
    }

    public void draw(double XIni, double YIni, double XFin, double YFin) {

        if (!canDraw){
            return;
        }

        this.XIni = XIni;
        this.YIni = YIni;
        this.XFin = XFin;
        this.YFin = YFin;

        Line line = new Line(XIni + MouseController.MOUSE_ADJST_X, YIni + MouseController.MOUSE_ADJST_Y,
                XFin + MouseController.MOUSE_ADJST_X, YFin + MouseController.MOUSE_ADJST_Y);
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

    public void setCanDraw(boolean canDraw) {
        this.canDraw = canDraw;
    }
}
