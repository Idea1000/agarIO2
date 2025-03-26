package fr.unicaen.iutcaen.viewTest;

import javafx.scene.layout.Pane;
import fr.unicaen.iutcaen.model.World;

public class MiniMap extends Pane {
    private World world;

    public MiniMap(World world) {
        this.world = world;
        // Initialisation de la mini-map
    }

    // Met à jour l'affichage de la mini-map en fonction du monde
    public void update() {
        // Affiche les positions des joueurs dans la zone réduite
    }
}
