package fr.unicaen.iutcaen.ai;

import fr.unicaen.iutcaen.model.entities.Entity;
import fr.unicaen.iutcaen.Config;

import java.util.Random;

public class RandomMovementAI implements AIBehavior {
    private Random random = new Random();

    @Override
    public void update(IA ia, Entity target) {
                // Générer une direction aléatoire
                Random random = new Random();
                double dx = random.nextDouble(Config.BASESPEED) - 1; // -1, 0, 1 pour la direction X
                double dy = random.nextDouble(Config.BASESPEED) - 1; // -1, 0, 1 pour la direction Y
                ia.move(dx, dy);
    }
}

