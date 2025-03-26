package fr.unicaen.iutcaen.model.entities;

import fr.unicaen.iutcaen.model.Player;
import fr.unicaen.iutcaen.model.Point;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

public class Pellet extends Entity {

    public Pellet(int id, Point position, double mass, Color color) {
        super(id, position, mass, color);
    }

    /**
     * Applies the effect of this Pellet on the player
     * @param player
     * @return true if the effect is added successfully
     */
    public void applyEffect(Cell cell) {
    	cell.mass.add(mass);
    }

    @Override
    public void update() {
        
    }

}
