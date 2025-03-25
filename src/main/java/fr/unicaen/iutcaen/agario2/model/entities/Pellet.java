package fr.unicaen.iutcaen.agario2.model.entities;

import fr.unicaen.iutcaen.agario2.model.Player;
import fr.unicaen.iutcaen.agario2.model.Point;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

public class Pellet extends Entity {

    public Pellet(int id, Point position, double mass, Color color) {
        super(id, position, mass, color);
    }

    // Appliquer l'effet de la pastille sur un joueur
    public void applyEffect(Player player) {
        // Logique pour modifier l'état du joueur (bonus, malus, division, etc.)
    }

    @Override
    public void update() {
        // Mise à jour éventuelle (souvent vide pour une pastille statique)
    }

}
