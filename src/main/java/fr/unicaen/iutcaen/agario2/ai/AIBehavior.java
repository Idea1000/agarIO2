package fr.unicaen.iutcaen.agario2.ai;

import fr.unicaen.iutcaen.agario2.model.Player;
import fr.unicaen.iutcaen.agario2.model.World;
import javafx.geometry.Point2D;

public interface AIBehavior {
    // Calcule la direction ou le mouvement à adopter par l'IA pour un joueur donné dans le monde
    Point2D computeDirection(Player player, World world);
}

