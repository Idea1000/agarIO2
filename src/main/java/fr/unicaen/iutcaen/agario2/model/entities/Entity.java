package fr.unicaen.iutcaen.agario2.model.entities;

import fr.unicaen.iutcaen.agario2.Config;
import fr.unicaen.iutcaen.agario2.model.Point;
import javafx.beans.property.Property;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

// Classe abstraite de base pour toutes les entités (joueur, pastille, etc.)
public abstract class Entity {
    protected int id;
    protected Point position;
    protected double mass;

    protected Color color;

    public Entity(int id, Point position, double mass, Color color) {
        this.id = id;
        this.position = position;
        this.mass = mass;
        this.color = color;
    }

    public abstract void update(); // Met à jour l'état de l'entité

    // Getters et setters
    public int getId() { return id; }
    public Point getPosition() { return position; }
    public double getMass() { return mass; }
    public void setPosition(Point position) { this.position = position; }
    public void setMass(double mass) { this.mass = mass; }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getSize(){
        return (int)(Config.SIZERATIO*Math.sqrt(mass));
    }
}
