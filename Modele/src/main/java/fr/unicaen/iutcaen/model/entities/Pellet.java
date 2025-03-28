package fr.unicaen.iutcaen.model.entities;

import fr.unicaen.iutcaen.config.Config;
import fr.unicaen.iutcaen.model.Point;
import javafx.scene.paint.Color;

/**
 * A pellet is a small circle that may be eaten by a cell
 * It doesn't move and is food for the player in the beginning
 * It may be referred as food or chip
 */
public class Pellet extends Entity {

    public Pellet(int id, Point position, double mass, Color color) {
        super(id, position, mass, color);
    }

    /**
     * Applies the effect of this Pellet on the player
     * @param cell
     * @return true if the effect is added successfully
     */
    public void applyEffect(Cell cell) {

    	cell.mass.set(cell.mass.doubleValue() + mass.doubleValue());

    }

    @Override
    public void update() {
        
    }
    @Override
    public double getSize(){
        return Config.SIZE_RATIO_PELLET *Math.sqrt(this.getMass());
    }
}
