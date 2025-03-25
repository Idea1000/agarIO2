package fr.unicaen.iutcaen.ai;

import fr.unicaen.iutcaen.model.Player;
import fr.unicaen.iutcaen.model.Point;
import fr.unicaen.iutcaen.model.World;
import fr.unicaen.iutcaen.model.entities.Entity;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

import java.util.Random;

public class RandomMovementAI implements AIBehavior {
    private Random random = new Random();

    @Override
    public void update(IA ia, Entity target) {
                // Générer une direction aléatoire
                Random random = new Random();
                int dx = random.nextInt(maxSpeed) - 1; // -1, 0, 1 pour la direction X
                int dy = random.nextInt(maxSpeed) - 1; // -1, 0, 1 pour la direction Y
                ia.move(dx, dy);
    }
}

