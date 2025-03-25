package fr.unicaen.iutcaen.agario2.model.entities;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

// Classe abstraite de base pour toutes les entités (joueur, pastille, etc.)
public abstract class Entity {
    protected int id;
    protected Point2D position;
    protected double mass;

    protected Color color;

    public Entity(int id, Point2D position, double mass, Color color) {
        // Constructeur initialisant id, position et masse
    }

    public abstract void update(); // Met à jour l'état de l'entité

    // Getters et setters
    public int getId() { return id; }
    public Point2D getPosition() { return position; }
    public double getMass() { return mass; }
    public void setPosition(Point2D position) { this.position = position; }
    public void setMass(double mass) { this.mass = mass; }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
