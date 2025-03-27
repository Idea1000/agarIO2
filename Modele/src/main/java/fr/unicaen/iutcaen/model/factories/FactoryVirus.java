package fr.unicaen.iutcaen.model.factories;

import fr.unicaen.iutcaen.config.Config;
import fr.unicaen.iutcaen.model.Point;
import fr.unicaen.iutcaen.model.entities.Entity;
import fr.unicaen.iutcaen.model.entities.Pellet;
import fr.unicaen.iutcaen.model.entities.Virus;
import javafx.scene.paint.Color;

import java.util.Random;

public class FactoryVirus extends FactoryEntity {


    @Override
    public Entity fabrique(Point position, double mass, Color color) {
        return new Pellet(IdDistributor.getInstance().getNextId(), position, mass, color);
    }

    public Virus randomVirus() {
        Random r = new Random();

        double randomX = r.nextDouble(Config.MAP_WIDTH);
        double randomY = r.nextDouble(Config.MAP_HEIGHT);

        Point p = new Point(randomX, randomY);

        Color color = Color.color(0.0, 1.0, 0.0);

        return new Virus(IdDistributor.getInstance().getNextId(), p, 0, color);
    }
}
