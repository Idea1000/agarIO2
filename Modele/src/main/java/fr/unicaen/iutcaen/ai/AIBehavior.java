package fr.unicaen.iutcaen.ai;

import fr.unicaen.iutcaen.model.Player;
import fr.unicaen.iutcaen.model.Point;
import fr.unicaen.iutcaen.model.World;
import javafx.geometry.Point2D;

public interface AIBehavior {
    void move(IA ia, Point direction);

}

