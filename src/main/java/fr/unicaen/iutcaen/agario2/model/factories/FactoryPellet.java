package fr.unicaen.iutcaen.agario2.model.factories;

import fr.unicaen.iutcaen.agario2.model.Point;
import fr.unicaen.iutcaen.agario2.model.entities.Entity;
import fr.unicaen.iutcaen.agario2.model.entities.Pellet;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

public class FactoryPellet extends FactoryEntity {

    @Override
    public Entity fabrique(Point position, double mass, Color color) {
        return new Pellet(IdDistributor.getInstance().getNextId(), position, mass, color);
    }
}
