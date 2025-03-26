package fr.unicaen.iutcaen.model;

import fr.unicaen.iutcaen.model.entities.Entity;

public class Boundary {
    private double x;
    private double y;
    private double width;
    private double height;

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public Boundary(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    // Vérifie si l'entité se trouve dans cette zone
    public boolean contains(Entity entity) {
        return entity.getPosition().getX() > x &&
                entity.getPosition().getX() <= x+width &&
                entity.getPosition().getY() > y &&
                entity.getPosition().getY() <= y + height;
    }

    // Vérifie si cette zone intersecte une autre zone
    public boolean intersects(Boundary other) {
        return this.x < other.x + other.width &&
                this.x + this.width > other.x &&
                this.y < other.y + other.height &&
                this.y + this.height > other.y;
    }
}
