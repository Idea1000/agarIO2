package fr.unicaen.iutcaen.model.entities;

import fr.unicaen.iutcaen.model.Player;
import fr.unicaen.iutcaen.model.Point;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

public class Pellet extends Entity {

    public Pellet(int id, Point position, double mass, Color color) {
        super(id, position, mass, color);
    }

    // Appliquer l'effet de la pastille sur un joueur
    public void applyEffect(Player player) {
        player.absorb(this);
    }

    @Override
    public void update() {
        
    }

}
