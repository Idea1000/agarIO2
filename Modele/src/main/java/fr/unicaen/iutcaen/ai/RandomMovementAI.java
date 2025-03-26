package fr.unicaen.iutcaen.ai;

import fr.unicaen.iutcaen.model.Point;
import fr.unicaen.iutcaen.config.Config;


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

    private Direction actualDirection = null;

    private int timeBeforeNewDirection = 5;

    private Random random = new Random();

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
        if (targetX == 0) {
            targetX = AI.getCells().getPosition().getX();
        }
        if (targetY == 0) {
            targetY = AI.getCells().getPosition().getY();
        }

        if (actualDirection == null) {
            timeBeforeNewDirection = 0;
        }

        if (timeBeforeNewDirection == 0) {
            actualDirection = Direction.values()[random.nextInt(Direction.values().length)];
            timeBeforeNewDirection = 5;
        }

        double speed = Config.BASESPEED * 10;

        double xMath = speed * Math.cos(Math.toRadians(45));
        double yMath = speed * Math.sin(Math.toRadians(45));

        switch (actualDirection) {
            case NORTH:
                targetY -= speed;  // Move up on the Y-axis
                break;
            case SOUTH:
                targetY += targetY;  // Move down on the Y-axis
                break;
            case EAST:
                targetX =+ speed;  // Move right on the X-axis
                break;
            case WEST:
                targetX -=speed;  // Move left on the X-axis
                break;
            case NORTHEAST:
                targetX += xMath;  // Move diagonally
                targetY -= yMath;  // Move diagonally
                break;
            case NORTHWEST:
                targetX -= xMath;  // Move diagonally
                targetY -= yMath;  // Move diagonally
                break;
            case SOUTHEAST:
                targetX += xMath;  // Move diagonally
                targetY += yMath;  // Move diagonally
                break;
            case SOUTHWEST:
                targetX -= xMath;  // Move diagonally
                targetY += yMath;  // Move diagonally
                break;
        }

        if (targetX < 0) {
            targetX = 0;
        } else if (targetX > 2000) {
            targetX = 2000;
        }

        if (targetY < 0) {
            targetY = 0;
        } else if (targetY > 1000) {
            targetY = 1000;
        }

        timeBeforeNewDirection-=1;
    }
}

