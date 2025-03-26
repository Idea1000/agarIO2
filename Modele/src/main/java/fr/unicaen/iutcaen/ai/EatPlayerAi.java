package fr.unicaen.iutcaen.ai;

import fr.unicaen.iutcaen.Config;
import fr.unicaen.iutcaen.model.Point;
import fr.unicaen.iutcaen.model.entities.Entity;

import java.util.Random;

public class EatPlayerAi implements AIBehavior{

    private double euclidean(IA ia, Point pos) {
        double dx = ia.getX() - pos.getX();
        double dy = ia.getY() - pos.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }
    @Override
    public void update(IA ia, Entity target) {
        Point pos = target.getPosition();
        double distance = euclidean(ia, pos);  // Get the distance to the target
        double dx = ((pos.getX() - ia.getX())/distance)*Config.BASESPEED;
        double dy = ((pos.getY() - ia.getY())/distance)*Config.BASESPEED;
        ia.move(dx, dy);
    }
}
