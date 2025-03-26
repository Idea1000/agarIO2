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
    private ArrayList<Entity> entitiesInRange;

    public IA(Point position, double mass, Color color) {
        cells = (CellPack) new FactoryCellPack().fabrique(position, mass, color);
    }

    public void setBehavior(AIBehavior behavior) {
        this.behavior = behavior;
    }

<<<<<<< HEAD

    // Déplacement de l'IA
    public void move(Point direction) {
        behavior.move(this, direction);
=======
    public void setEntitiesInRange(ArrayList<Entity> entities) {
        entitiesInRange = entities;
    }

    public void update() {
        if (behavior == null) {
            behavior = new RandomMovementAI();
        }
        Entity targetVisible = null;
        for (Entity e : entitiesInRange) {
            if (e instanceof CellPack && e != cells) {
                // If target detected go chase him
                setBehavior(new EatPlayerAi());
                targetVisible = e;
                break;
            }
        }
        if (targetVisible == null) {
            setBehavior(new RandomMovementAI());
        }
        behavior.update(this, targetVisible);
    }

    // Déplacement de l'IA
    public void move(double dx, double dy) {
        cells.move(new Point(dx, dy));
>>>>>>> ba6cd46483753db33fdb32e8460d86bc0391571c
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
