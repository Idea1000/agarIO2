package fr.unicaen.iutcaen.model.factories;

import fr.unicaen.iutcaen.model.entities.Entity;
import fr.unicaen.iutcaen.model.entities.Pellet;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

public class FactoryPellet extends FactoryEntity {

    @Override
    public Entity fabrique(Point2D position, double mass, Color color) {
        return new Pellet(IdDistributor.getInstance().getNextId(), position, mass, color);
    }
}
