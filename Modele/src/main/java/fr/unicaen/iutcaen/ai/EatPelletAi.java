package fr.unicaen.iutcaen.ai;

import fr.unicaen.iutcaen.model.Point;

/**
 * Define the AI behavior as eat a pellet
 */
public class EatPelletAi implements AIBehavior{

    /**
     * the X position of the target
     */
    private double targetX = 0;

    /**
     * the Y position of the target
     */
    private double targetY = 0;

    /**
     * Move the IA to the desired target
     * @param ia
     */
    @Override
    public void move(IA ia) {
        Point direction = new Point(targetX, targetY);
        ia.getCells().move(direction);
    }

    /**
     * Update the IA target
     * @param ia
     */
    @Override
    public void update(IA ia) {
        Point pos = ia.getTarget().getPosition();
        targetX = pos.getX();
        targetY = pos.getY();
    }
}
