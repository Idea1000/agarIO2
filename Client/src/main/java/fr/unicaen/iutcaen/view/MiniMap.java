
package fr.unicaen.iutcaen.view;


import fr.unicaen.iutcaen.model.World;
import javafx.scene.layout.Pane;

public class MiniMap extends Pane {
    private World world;

    /**
     * Initialize the minimap of the game
     * @param world
     */
    public MiniMap(World world) {
        this.world = world;
        // Initialisation de la mini-map
    }

    // Met à jour l'affichage de la mini-map en fonction du monde

    /**
     * Updates the minimap with the position of the players
     */
    public void update() {
        // Affiche les positions des joueurs dans la zone réduite
    }
}
