package fr.unicaen.iutcaen.model;

import fr.unicaen.iutcaen.model.entities.CellPack;
import fr.unicaen.iutcaen.model.entities.Entity;
import fr.unicaen.iutcaen.model.factories.FactoryCellPack;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

public class Player {
    private double speed;
    // Pour la gestion du joueur composé de plusieurs cellules (Composite Pattern)
    // D'autres attributs : direction, état, etc.
    private CellPack cells;

    public Player(Point2D position, double mass, Color color) {
        cells = (CellPack) new FactoryCellPack().fabrique(position, mass, color);
    }

    // Déplacement du joueur
    public void move(Point2D direction) {
        // Mise à jour de la position selon la direction
    }

    // Absorption d'une entité (pastille ou autre joueur)
    public void absorb(Entity entity) {
        // Logique d'absorption (vérification de masse, fusion, etc.)
    }

    // Division cellulaire du joueur
    public void split() {
        // Diviser le joueur en deux entités de masse réduite
    }

    // Getters et setters supplémentaires
    public double getSpeed() { return speed; }
    public void setSpeed(double speed) { this.speed = speed; }
}
