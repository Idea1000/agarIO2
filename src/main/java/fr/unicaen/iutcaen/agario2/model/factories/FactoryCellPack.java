package fr.unicaen.iutcaen.agario2.model.factories;

import fr.unicaen.iutcaen.agario2.model.Point;
import fr.unicaen.iutcaen.agario2.model.entities.CellPack;
import fr.unicaen.iutcaen.agario2.model.entities.Entity;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

public class FactoryCellPack extends FactoryEntity{
    @Override
    public Entity fabrique(Point position, double mass, Color color) {
        return new CellPack(IdDistributor.getInstance().getNextId(), position, mass, color);
    }
}
