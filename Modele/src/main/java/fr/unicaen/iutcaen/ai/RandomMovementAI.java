package fr.unicaen.iutcaen.ai;

import fr.unicaen.iutcaen.model.Player;
import fr.unicaen.iutcaen.model.Point;
import fr.unicaen.iutcaen.model.World;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import fr.unicaen.iutcaen.model.entities.Entity;
import fr.unicaen.iutcaen.Config;


import java.util.Random;

public class RandomMovementAI implements AIBehavior {

    private double targetX = 0;
    private double targetY = 0;
    private Random random = new Random();

    @Override
    public void move(IA ia) {
        Point direction = new Point(targetX, targetY);
        ia.getCells().move(direction);
    }

    @Override
    public void update(IA ia) {
        Point actualD = ia.getCells().getPosition();
        targetX = Math.abs(random.nextDouble(-Config.BASESPEED*70,Config.BASESPEED*70) + actualD.getX());
        targetY = Math.abs(random.nextDouble(-Config.BASESPEED*70,Config.BASESPEED*70) +actualD.getY());
    }
}

