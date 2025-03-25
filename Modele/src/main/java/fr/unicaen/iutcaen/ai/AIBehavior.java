package fr.unicaen.iutcaen.ai;

import fr.unicaen.iutcaen.model.Player;
import fr.unicaen.iutcaen.model.World;
import fr.unicaen.iutcaen.model.entities.Entity;
import javafx.geometry.Point2D;

public interface AIBehavior {
    static final int maxSpeed = 3;

    void update(IA ia, Entity target);
}

