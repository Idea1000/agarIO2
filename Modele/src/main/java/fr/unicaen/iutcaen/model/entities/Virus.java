package fr.unicaen.iutcaen.model.entities;

import fr.unicaen.iutcaen.config.Config;
import fr.unicaen.iutcaen.model.Player;
import fr.unicaen.iutcaen.model.Point;
import fr.unicaen.iutcaen.model.factories.IdDistributor;
import javafx.scene.paint.Color;

public class Virus extends Entity{
    public Virus(int id, Point position, double mass, Color color) {
        super(id, position, Config.VIRUS_MASS, color);
    }

    public void applyEffect(Player player) {
        player.split();
    }

    @Override
    public void update() {

    }
}
