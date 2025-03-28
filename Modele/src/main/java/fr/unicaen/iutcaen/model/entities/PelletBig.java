package fr.unicaen.iutcaen.model.entities;

import fr.unicaen.iutcaen.model.Point;
import javafx.scene.paint.Color;

public class PelletBig extends Pellet{
    public PelletBig(int id, Point position, double mass, Color color) {
        super(id, position, mass, color);
    }

    @Override
    public void applyEffect(Cell cell) {
        cell.mass.set(cell.mass.doubleValue() + mass.doubleValue()+100);

    }
}
