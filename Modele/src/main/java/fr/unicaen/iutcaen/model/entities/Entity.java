package fr.unicaen.iutcaen.model.entities;

import fr.unicaen.iutcaen.config.Config;
import fr.unicaen.iutcaen.model.Point;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.paint.Color;

// Classe abstraite de base pour toutes les entités (joueur, pastille, etc.)
public abstract class Entity {
    protected int id;
    protected Point position;
    protected SimpleDoubleProperty mass = new SimpleDoubleProperty();

    protected Color color;

    public Entity(int id, Point position, double mass, Color color) {
        this.id = id;
        this.position = position;
        this.mass.set(mass);
        this.color = color;
    }

    public abstract void update(); // Met à jour l'état de l'entité

    // Getters et setters
    public int getId() { return id; }
    public Point getPosition() { return position; }
    public double getMass() { return mass.getValue(); }
    public void setPosition(Point position) { this.position = position; }
    public void setMass(double mass) { this.mass.set(mass); }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public SimpleDoubleProperty getMassProperty(){
        return mass;
    }

    public double getSize(){
        return Config.SIZE_RATIO *Math.sqrt(this.getMass());
    }
    
    public void setId(int id) {
    	this.id = id; 
    }
}
