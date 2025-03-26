package fr.unicaen.iutcaen.ai;

import fr.unicaen.iutcaen.model.Player;
import fr.unicaen.iutcaen.model.Point;
import fr.unicaen.iutcaen.model.World;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import fr.unicaen.iutcaen.model.entities.Entity;
import fr.unicaen.iutcaen.Config;


import java.util.Random;

public class RandomMovementAI implements AIBehavior {
    private Random random = new Random();





    public Point update(Point actualD) {

        SimpleDoubleProperty dx = new SimpleDoubleProperty(random.nextDouble() * 2 - 1); // Valeur entre -1 et 1 pour la direction X
        SimpleDoubleProperty dy = new SimpleDoubleProperty(random.nextDouble() * 2 - 1); // Valeur entre -1 et 1 pour la direction Y
        System.out.println();
        return new Point(dx.get(), dy.get());
    }


    @Override
    public void move(IA ia, Point direction) {
        direction = update(ia.getCells().getPosition());
        ia.getCells().move(direction);

    }
}

