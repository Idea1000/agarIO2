package fr.unicaen.iutcaen.ai;

import fr.unicaen.iutcaen.model.Player;
import fr.unicaen.iutcaen.model.Point;
import fr.unicaen.iutcaen.model.entities.CellPack;
import fr.unicaen.iutcaen.model.entities.Pellet;
import fr.unicaen.iutcaen.model.factories.FactoryCellPack;
import javafx.scene.paint.Color;

public class IA {

    private AIBehavior behavior; // Stratégie actuelle
    private CellPack cells;

    public IA(Point position, double mass, Color color) {
        cells = (CellPack) new FactoryCellPack().fabrique(position, mass, color);
    }

    public void setBehavior(AIBehavior behavior) {
        this.behavior = behavior;
    }


    // Déplacement de l'IA
    public void move(Point direction) {
        behavior.move(this, direction);
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
