package fr.unicaen.iutcaen.model.factories;

import fr.unicaen.iutcaen.model.Point;
import fr.unicaen.iutcaen.model.entities.CellPack;
import fr.unicaen.iutcaen.model.entities.Entity;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

public class FactoryCellPack extends FactoryEntity{
    @Override
    public Entity build(Point position, double mass, Color color) {
        return new CellPack(IdDistributor.getInstance().getNextId(), position, mass, color);
    }
}
