package fr.unicaen.iutcaen.ai;

import fr.unicaen.iutcaen.model.Point;
import fr.unicaen.iutcaen.model.entities.CellPack;
import fr.unicaen.iutcaen.model.entities.Entity;
import fr.unicaen.iutcaen.model.entities.Pellet;
import fr.unicaen.iutcaen.model.factories.FactoryCellPack;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class IA {

    private AIBehavior behavior; // Stratégie actuelle
    private CellPack cells;
    protected ArrayList<Entity> entitiesInRange;

    private Entity target;

    public IA(Point position, double mass, Color color) {
        cells = (CellPack) new FactoryCellPack().fabrique(position, mass, color);
    }

    public void setBehavior(AIBehavior behavior) {
        this.behavior = behavior;
    }

    // Déplacement de l'IA
    public void move() {
        behavior.move(this);
    }

    public Entity getTarget() {
        return target;
    }

    public void setTarget(Entity target) {
        this.target = target;
    }


    public void setEntitiesInRange(ArrayList<Entity> entities) {
        entitiesInRange = entities;
    }

    private double euclidean(Point target) {
        Point pos = cells.getPosition();
        double dx = Math.abs(target.getX() - pos.getX());
        double dy = Math.abs(target.getY() - pos.getY());
        return Math.sqrt(dx * dx + dy * dy);
    }

    public void update() {
        setBehavior(manageState());
        behavior.update(this);

    }
    public AIBehavior manageState() {
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
                    double euclide = euclidean(e.getPosition());
                    if (euclide < distance) {
                        tempTarget = e;
                        distance = euclide;
                    }
                }
            }
            target = tempTarget;
            return new EatPelletAi();
        }
        if (target == null) {
            return new RandomMovementAI();
        }
        return null;
    }

    // Getter et setters
    public CellPack getCells() {
        return cells;
    }

    public Pellet findClosestPellet() {
        //logiqueeeeeeee
        return null;
    }
}
