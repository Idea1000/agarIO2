
package fr.unicaen.iutcaen.view;


import javafx.scene.layout.Pane;
import fr.unicaen.iutcaen.model.World;
import fr.unicaen.iutcaen.model.Entity;
import javafx.scene.shape.Circle;

public class GameView extends Pane {
    private World world;

    public GameView(World world) {
        this.world = world;
        // Initialisation de l'affichage (création des éléments graphiques)
    }

    // Met à jour l'affichage en fonction de l'état du monde
    public void update() {
        // Parcourt les entités du monde et met à jour leurs représentations graphiques (Circle, etc.)
    }

    // Crée une représentation graphique pour une entité donnée
    public Circle createEntityCircle(Entity entity) {
        return new Circle();
    }
}
