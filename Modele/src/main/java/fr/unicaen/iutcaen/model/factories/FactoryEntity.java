package fr.unicaen.iutcaen.model.factories;

import fr.unicaen.iutcaen.model.Point;
import fr.unicaen.iutcaen.model.entities.Entity;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public abstract class FactoryEntity {
    public abstract Entity fabrique(Point position, double mass, Color color);
}
