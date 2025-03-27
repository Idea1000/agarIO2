package fr.unicaen.iutcaen.viewTest;

import fr.unicaen.iutcaen.config.Config;
import fr.unicaen.iutcaen.model.Boundary;
import fr.unicaen.iutcaen.model.Player;
import fr.unicaen.iutcaen.model.Point;
import fr.unicaen.iutcaen.model.World;
import fr.unicaen.iutcaen.model.entities.Entity;
import fr.unicaen.iutcaen.model.entities.Pellet;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.List;

public class WorldView extends AbstractView{



    public WorldView(Player player, Pane root){
        World world = World.getInstence();
        world.addPlayer(player);

       /* world.addEntity(new Pellet(55, new Point(400.0, 400.0), 10, Color.BLUE));
        world.addEntity(new Pellet(55, new Point(400.0, 600.0), 10, Color.BLUE));*/


        List<Entity> entities = world.getEntitiesAround(new Boundary(0, 0, root.getWidth(), root.getHeight()));

        for(Entity entitie : entities){
            if(entitie instanceof Pellet) {
                Pellet pellet = (Pellet) entitie;
                new PelletView(pellet, root);
            }
        }
        new PlayerView(player, root);
    }

    @Override
    public void delete(Pane root) {
        root.getChildren().remove(this);
    }
}
