package fr.unicaen.iutcaen.model.factories;

import fr.unicaen.iutcaen.ai.AI;
import fr.unicaen.iutcaen.model.Point;
import fr.unicaen.iutcaen.model.entities.Entity;
import javafx.scene.paint.Color;

public abstract class FactoryEntity {
    public abstract Entity fabrique(Point position, double mass, Color color);
}
