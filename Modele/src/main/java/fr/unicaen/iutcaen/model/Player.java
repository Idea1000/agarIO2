package fr.unicaen.iutcaen.model;

import javafx.geometry.Point2D;
import java.util.List;

public class Player extends Entity {
    private double speed;
    // Pour la gestion du joueur composé de plusieurs cellules (Composite Pattern)
    private List<Player> cells;
    // D'autres attributs : direction, état, etc.

    public Player(String id, Point2D position, double mass, double speed) {
        super(id, position, mass);
        this.speed = speed;
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

    @Override
    public void update() {
        // Met à jour la position, la vitesse ou autres propriétés dynamiques
    }

    // Getters et setters supplémentaires
    public double getSpeed() { return speed; }
    public void setSpeed(double speed) { this.speed = speed; }
}