package fr.unicaen.iutcaen.model.entities;

import fr.unicaen.iutcaen.model.Point;
import javafx.scene.paint.Color;

import java.util.Random;

public class PelletInvisible extends Pellet{

    public PelletInvisible(int id, Point position, double mass, Color color) {
        super(id, position, mass, color);
    }
    @Override
    public void applyEffect(Cell cell) {

        Color c = new Color(0,0,0,0.0);
        cell.setColor(c);
    }
}
