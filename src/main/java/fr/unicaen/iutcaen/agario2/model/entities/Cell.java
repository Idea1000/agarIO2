package fr.unicaen.iutcaen.agario2.model.entities;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

public class Cell extends Entity{

    public Cell(int id, Point2D position, double mass, Color color) {
        super(id, position, mass, color);
    }

    @Override
    public void update() {

    }
}
