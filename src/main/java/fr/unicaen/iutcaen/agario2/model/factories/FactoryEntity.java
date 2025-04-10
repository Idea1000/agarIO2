package fr.unicaen.iutcaen.agario2.model.factories;

import fr.unicaen.iutcaen.agario2.model.Point;
import fr.unicaen.iutcaen.agario2.model.entities.Entity;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public abstract class FactoryEntity {
    public abstract Entity fabrique(Point position, double mass, Color color);
}
