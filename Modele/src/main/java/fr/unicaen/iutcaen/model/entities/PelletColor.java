package fr.unicaen.iutcaen.model.entities;

import fr.unicaen.iutcaen.model.Point;
import javafx.scene.paint.Color;

import java.util.Random;

public class PelletColor extends Pellet{
    public PelletColor(int id, Point position, double mass, Color color) {
        super(id, position, mass, color);
    }

    @Override
    public void applyEffect(Cell cell) {
        Random r = new Random();
        Color c = new Color(0.0,r.nextDouble(),r.nextDouble(),r.nextDouble());
        cell.setColor(c);
    }
}
