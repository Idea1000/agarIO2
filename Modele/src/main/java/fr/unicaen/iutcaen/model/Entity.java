package fr.unicaen.iutcaen.model;

import javafx.geometry.Point2D;

// Classe abstraite de base pour toutes les entités (joueur, pastille, etc.)
public abstract class Entity {
    protected String id;
    protected Point2D position;
    protected double mass;

    public Entity(String id, Point2D position, double mass) {
        // Constructeur initialisant id, position et masse
    }

    public abstract void update(); // Met à jour l'état de l'entité

    // Getters et setters
    public String getId() { return id; }
    public Point2D getPosition() { return position; }
    public double getMass() { return mass; }
    public void setPosition(Point2D position) { this.position = position; }
    public void setMass(double mass) { this.mass = mass; }
}