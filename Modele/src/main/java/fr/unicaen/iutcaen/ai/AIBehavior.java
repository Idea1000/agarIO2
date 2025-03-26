package fr.unicaen.iutcaen.ai;

import fr.unicaen.iutcaen.model.Player;
import fr.unicaen.iutcaen.model.Point;
import fr.unicaen.iutcaen.model.World;
import javafx.geometry.Point2D;

/**
 * Define an AI behavior
 */
public interface AIBehavior {

    /**
     * Move the AI
     * @param ia
     */
    void move(IA ia);

    /**
     * Update the AI target
     * @param ia
     */
    void update(IA ia);

}

