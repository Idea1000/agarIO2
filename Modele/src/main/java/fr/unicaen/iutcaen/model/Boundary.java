package fr.unicaen.iutcaen.model;

import fr.unicaen.iutcaen.model.entities.Entity;
import javafx.beans.property.SimpleDoubleProperty;

public class Boundary {
    private SimpleDoubleProperty x;
    private SimpleDoubleProperty y;
    private double width;
    private double height;

    public SimpleDoubleProperty xProperty() {
        return x;
    }

    public void setX(double x) {
        this.x.set(x);
    }

    public SimpleDoubleProperty yProperty() {
        return y;
    }

    public void setY(double y) {
        this.y.set(y);
    }

    public double getX() {
        return x.get();
    }

    public double getY() {
        return y.get();
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public Boundary(double x, double y, double width, double height) {
        this.x = new SimpleDoubleProperty(x);
        this.y = new SimpleDoubleProperty(y);
        this.width = width;
        this.height = height;
    }

    // Vérifie si l'entité se trouve dans cette zone
    public boolean contains(Entity entity) {
        return entity.getPosition().getX() > x.get() &&
                entity.getPosition().getX() <= x.get()+width &&
                entity.getPosition().getY() > y.get() &&
                entity.getPosition().getY() <= y.get() + height;
    }

    // Vérifie si cette zone intersecte une autre zone
    public boolean intersects(Boundary other) {
        return this.x.get() < other.x.get() + other.width &&
                this.x.get() + this.width > other.x.get() &&
                this.y.get() < other.y.get() + other.height &&
                this.y.get() + this.height > other.y.get();
    }
}
