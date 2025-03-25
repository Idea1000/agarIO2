package fr.unicaen.iutcaen.model;

import javafx.geometry.Point2D;

public class Pellet extends Entity {
    public enum Type { STANDARD, SPECIAL }
    private Type type;

    public Pellet(String id, Point2D position, double mass, Type type) {
        super(id, position, mass);
        this.type = type;
    }

    // Appliquer l'effet de la pastille sur un joueur
    public void applyEffect(Player player) {
        // Logique pour modifier l'état du joueur (bonus, malus, division, etc.)
    }

    @Override
    public void update() {
        // Mise à jour éventuelle (souvent vide pour une pastille statique)
    }

    // Getter pour le type
    public Type getType() { return type; }
}