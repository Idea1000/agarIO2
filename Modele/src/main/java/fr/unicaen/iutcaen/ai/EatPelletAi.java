package fr.unicaen.iutcaen.ai;

import fr.unicaen.iutcaen.model.Point;

/**
 * Define the AI behavior as eat a pellet
 * @author courtoi223, Idea1000
 */
public class EatPelletAi implements AIBehavior {

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
     * @param AI
     */
    @Override
    public void move(AI AI) {
        Point direction = new Point(targetX, targetY);
        AI.getCells().move(direction);
    }

    /**
     * Update the IA target
     * @param AI
     */
    @Override
    public void update(AI AI) {
        Point pos = AI.getTarget().getPosition();
        targetX = pos.getX();
        targetY = pos.getY();
    }
}
