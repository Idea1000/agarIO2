package fr.unicaen.iutcaen.ai;

import fr.unicaen.iutcaen.model.Point;
import fr.unicaen.iutcaen.Config;


import java.util.Random;

/**
 * Define the AI behavior as move randomly
 * @author courtoi223, Idea1000
 */
public class RandomMovementAI implements AIBehavior {

    /**
     * the X position of the target
     */
    private double targetX = 0;

    /**
     * the Y position of the target
     */
    private double targetY = 0;

    private Random random = new Random();

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
        Point actualD = ia.getCells().getPosition();
        targetX = Math.abs(random.nextDouble(-Config.BASESPEED*70,Config.BASESPEED*70) + actualD.getX());
        targetY = Math.abs(random.nextDouble(-Config.BASESPEED*70,Config.BASESPEED*70) +actualD.getY());
    }
}

