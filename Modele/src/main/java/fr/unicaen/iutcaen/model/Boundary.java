package fr.unicaen.iutcaen.model;

import fr.unicaen.iutcaen.model.entities.Entity;

public class Boundary {
    private double x, y, width, height;

    public Boundary(double x, double y, double width, double height) {
        // Initialisation des coordonnées et dimensions
    }

    // Vérifie si l'entité se trouve dans cette zone
    public boolean contains(Entity entity) {
        return false;
    }

    // Vérifie si cette zone intersecte une autre zone
    public boolean intersects(Boundary other) {
        return false;
    }
}
