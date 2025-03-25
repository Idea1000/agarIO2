package fr.unicaen.iutcaen.agario2.model;

import javafx.geometry.Point2D;

public class EntityFactory {
    public static Player createPlayer(String id, Point2D position, double mass, double speed) {
        return new Player(id, position, mass, speed);
    }

    public static Pellet createPastille(String id, Point2D position, double mass, Pellet.Type type) {
        return new Pellet(id, position, mass, type);
    }
}

