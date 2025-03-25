package fr.unicaen.iutcaen.ai;

import fr.unicaen.iutcaen.model.Player;
import fr.unicaen.iutcaen.model.World;
import javafx.geometry.Point2D;
import java.util.Random;

public class RandomMovementAI implements AIBehavior {
    private Random random = new Random();

    @Override
    public Point2D computeDirection(Player player, World world) {
        // Retourne une direction aléatoire
        return new Point2D(random.nextDouble(), random.nextDouble());
    }
}
