package fr.unicaen.iutcaen.viewTest;

import fr.unicaen.iutcaen.model.Player;
import fr.unicaen.iutcaen.model.World;
import fr.unicaen.iutcaen.model.entities.Entity;
import fr.unicaen.iutcaen.model.entities.Pellet;
import javafx.scene.layout.Pane;

import java.util.List;

public class WorldView {



    public WorldView(Player player, Pane root){
        World world = World.getInstence();

        if(world.containsPlayer(player)){
            new PlayerView(player, root);
        }

        List<Entity> entities = world.getEntitiesAround(player.getCells().getBiggestCell());
        for(Entity entitie : entities){
            if(entitie instanceof Pellet){
                Pellet pellet = (Pellet) entitie;
                new PelletView(pellet, root);
            }
        }
    }

}
