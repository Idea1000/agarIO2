package fr.unicaen.iutcaen.model.factories;

import fr.unicaen.iutcaen.model.Point;
import fr.unicaen.iutcaen.model.entities.Entity;
import javafx.scene.paint.Color;

/**
 * Respects the Factory patron
 */
public abstract class FactoryEntity {
    public abstract Entity build(Point position, double mass, Color color);
}
