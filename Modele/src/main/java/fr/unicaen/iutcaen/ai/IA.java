package fr.unicaen.iutcaen.ai;

import fr.unicaen.iutcaen.model.Point;
import fr.unicaen.iutcaen.model.entities.CellPack;
import fr.unicaen.iutcaen.model.entities.Entity;
import fr.unicaen.iutcaen.model.entities.Pellet;
import fr.unicaen.iutcaen.model.factories.FactoryCellPack;
import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * Define an AI logic
 *
 * @author courtoi223 & Idea1000
 */
public class IA {

    /**
     * Actual behavior of the AI
     */
    private AIBehavior behavior;

    /**
     * Cells of the AI
     */
    private CellPack cells;

    /**
     * List of entities in range
     */
    protected ArrayList<Entity> entitiesInRange;

    /**
     * The current target
     */
    private Entity target;

    public IA(Point position, double mass, Color color) {
        cells = (CellPack) new FactoryCellPack().fabrique(position, mass, color);
    }

    /**
     * Move the AI according to it's behavior
     */
    public void move() {
        behavior.move(this);
    }

    /**
     * Update the AI behavior and target
     */
    public void update() {
        setBehavior(manageState()); // Update the behavior depending on what is currently near the AI
        behavior.update(this);
    }

    /**
     * Update the AI behavior
     * @return the new AI behavior
     */
    private AIBehavior manageState() {
        if (behavior == null) {
            return new RandomMovementAI();
        }
        double distance = Double.POSITIVE_INFINITY;
        Entity tempTarget = null;
        if (entitiesInRange != null) {
            for (Entity e : entitiesInRange) {
                if (e instanceof CellPack && e != cells) {
                    // If target detected go chase him
                    target = e;
                    return new EatPlayerAi();
                } else{
                    // Find the closest pellet
                    double euclide = euclidean(e.getPosition());
                    if (euclide < distance) {
                        tempTarget = e;
                        distance = euclide;
                    }
                }
            }
            setTarget(tempTarget);
            return new EatPelletAi();
        }
        if (target == null) {
            return new RandomMovementAI();
        }
        return null;
    }

    /**
     * Get what is the current target of the AI
     * @return the target
     */
    public Entity getTarget() {
        return target;
    }

    /**
     * Get the cells of the AI
     * @return the cells
     */
    public CellPack getCells() {
        return cells;
    }

    /**
     * Set what is the current target of the AI
     * @param target
     */
    private void setTarget(Entity target) {
        this.target = target;
    }

    /**
     * Set what are the new entities in range
     * @param entities
     */
    public void setEntitiesInRange(ArrayList<Entity> entities) {
        entitiesInRange = entities;
    }

    /**
     * Set the behavior of the AI
     * @param behavior
     */
    public void setBehavior(AIBehavior behavior) {
        this.behavior = behavior;
    }

    /**
     * Compute the euclidean distance between the AI and the target
     * @param target the target position
     * @return the euclidean distance
     */
    private double euclidean(Point target) {
        Point pos = cells.getPosition();
        double dx = Math.abs(target.getX() - pos.getX());
        double dy = Math.abs(target.getY() - pos.getY());
        return Math.sqrt(dx * dx + dy * dy);
    }
}
