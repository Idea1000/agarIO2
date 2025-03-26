package fr.unicaen.iutcaen.model.factories;

import fr.unicaen.iutcaen.ai.AI;
import fr.unicaen.iutcaen.ai.EatPelletAi;
import fr.unicaen.iutcaen.config.Config;
import fr.unicaen.iutcaen.model.Point;
import javafx.scene.paint.Color;

import java.util.Random;

public class FactoryAI {

    public AI fabriqueAI(){
        Random r = new Random();

        double randomX = r.nextDouble(Config.MAP_WIDTH);
        double randomY = r.nextDouble(Config.MAP_HEIGHT);

        Point p = new Point(randomX, randomY);

        double randomMass = r.nextDouble(25, 50);

        Color color = Color.color(r.nextDouble(), r.nextDouble(), r.nextDouble());

        return new AI(p, randomMass, color);
    }
}
