package fr.unicaen.iutcaen.agario2.model;

import fr.unicaen.iutcaen.agario2.model.entities.CellPack;
import fr.unicaen.iutcaen.agario2.model.entities.Entity;
import fr.unicaen.iutcaen.agario2.model.factories.FactoryCellPack;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class Player {
    private double speed;
    // Pour la gestion du joueur composé de plusieurs cellules (Composite Pattern)
    // D'autres attributs : direction, état, etc.
    private CellPack cells;

    public Player(Point position, double mass, Color color) {
        cells = (CellPack) new FactoryCellPack().fabrique(position, mass, color);
    }

    // Déplacement du joueur
    public void movePlayer(Point direction) {
        cells.move(direction);
    }

    // Absorption d'une entité (pastille ou autre joueur)
    public void absorb(Entity entity) {
        // Logique d'absorption (vérification de masse, fusion, etc.)
    }

    // Division cellulaire du joueur
    public void split() {
        // Diviser le joueur en deux entités de masse réduite
    }

    public CellPack getCells() {
        return cells;
    }

    public Point getPosition(){
        return cells.getPosition();
    }

    // Getters et setters supplémentaires
}
