package fr.unicaen.iutcaen.ai;

import fr.unicaen.iutcaen.model.Player;
import fr.unicaen.iutcaen.model.Point;
import fr.unicaen.iutcaen.model.entities.CellPack;
import fr.unicaen.iutcaen.model.entities.Pellet;
import fr.unicaen.iutcaen.model.factories.FactoryCellPack;
import javafx.scene.paint.Color;

public class IA {
    private int x, y; // Position de l'IA
    private AIBehavior behavior; // Stratégie actuelle
    private CellPack cells;

    public IA(Point position, double mass, Color color) {
        cells = (CellPack) new FactoryCellPack().fabrique(position, mass, color);
    }

    public void setBehavior(AIBehavior behavior) {
        this.behavior = behavior;
    }

    public void update() {
        if (behavior != null) {
            behavior.update(this); // Appliquer la stratégie
        }
    }

    // Déplacement de l'IA
    public void move(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }

    // Getter et setters
    public int getX() { return x; }
    public int getY() { return y; }

    public Pellet findClosestPellet() {
        //logiqueeeeeeee
        return null;
    }
}
