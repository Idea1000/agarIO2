package fr.unicaen.iutcaen.ai;

import fr.unicaen.iutcaen.model.Point;

public class EatPelletAi implements AIBehavior{

    private double targetX = 0;
    private double targetY = 0;

    @Override
    public void move(IA ia) {
        Point direction = new Point(targetX, targetY);
        ia.getCells().move(direction);
    }

    @Override
    public void update(IA ia) {
        Point pos = ia.getTarget().getPosition();
        targetX = pos.getX();
        targetY = pos.getY();
    }
}
