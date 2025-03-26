package fr.unicaen.iutcaen.ai;

import fr.unicaen.iutcaen.Config;
import fr.unicaen.iutcaen.model.Player;
import fr.unicaen.iutcaen.model.Point;
import fr.unicaen.iutcaen.model.World;
import javafx.geometry.Point2D;

public class EatPlayerAi implements AIBehavior{

    private double targetX = 0;
    private double targetY = 0;

    @Override
    public void update(IA ia) {
        Point pos = ia.getTarget().getPosition();
        targetX = pos.getX();
        targetY = pos.getY();
    }

    @Override
    public void move(IA ia) {
        Point direction = new Point(targetX, targetY);
        ia.getCells().move(direction);
    }
}
