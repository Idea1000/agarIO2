package fr.unicaen.iutcaen.ai;

import fr.unicaen.iutcaen.model.Point;
import fr.unicaen.iutcaen.model.World;
import fr.unicaen.iutcaen.model.entities.Cell;
import fr.unicaen.iutcaen.model.entities.CellPack;
import fr.unicaen.iutcaen.model.entities.Entity;
import fr.unicaen.iutcaen.model.factories.FactoryCellPack;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Define an AI logic
 * @author courtoi223, Idea1000
 */
public class AI {

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
    protected List<Entity> entitiesInRange;

    /**
     * The current target
     */
    private Entity target = null;

    public AI(Point position, double mass, Color color) {

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
        AIBehavior newBehavior = manageState(); // Update the behavior depending on what is currently near the AI
        if (newBehavior.getClass() != behavior.getClass()) {
            setBehavior(newBehavior);
        }
        behavior.update(this);
    }

    /**
     * <h1>Updates the AI behavior</h1>
     * <br>
     * logic in order of priority:
     * <ul>
     *     <li>Chase player</li>
     *     <li>Go to nearest pellet</li>
     *     <li>Do random movement</li>
     * </ul>
     * @return the new AI behavior
     */
    private AIBehavior manageState() {
        if (behavior == null) {
            return new RandomMovementAI();
        }
        double distance = Double.POSITIVE_INFINITY;
        Entity tempTarget = null;
        if (entitiesInRange != null && entitiesInRange.size() > 0) {
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
            // If no player detected go to the closest pellet found
            if (tempTarget != null) {
                setTarget(tempTarget);
                return new EatPelletAi();
            }
        } else {
            return new RandomMovementAI();
        }
        if (target == null) {
            // If no target available in range, do random move
            return new RandomMovementAI();
        }
        return null;
    }

    //ajout de l'absorption
    public boolean absorb(Entity entity) {
        World world = World.getInstence();

            boolean absorbed = cells.absorbEntity(entity);
            if(absorbed) {
                world.removeEntity(entity);
                return true;
            }
        return false;
    }


    public boolean absorbPlayer(Entity entity) {
        if (entity instanceof Cell) {
            Cell cell = (Cell) entity;
            for (Cell mycell : cells.getAllCells()) {
                if (mycell.absorbEntity(cell)){
                    return true;
                }
            }
        }
        return false;
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
    public void setEntitiesInRange(List<Entity> entities) {
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

    public void eraseCell(){
        cells.getAllCells().removeAll();
        cells.deleteAllCells();

    }
}
