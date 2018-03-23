package org.academiadecodigo.haltistas.client.graphics;

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


        Line line = new Line(iniX, iniY, finX, finY);
        line.setColor(Color.WHITE);
        line.draw();

        lines.add(line);
    }

    public void deleteAll() {
        for (Line line : lines) {
            line.delete();
        }
        lines.clear();
    }
}
