package fr.unicaen.iutcaen.model;

import fr.unicaen.iutcaen.Config;
import fr.unicaen.iutcaen.model.entities.Entity;
import fr.unicaen.iutcaen.model.entities.Pellet;
import fr.unicaen.iutcaen.model.factories.FactoryPellet;
import fr.unicaen.iutcaen.model.quadtree.QuadTree;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

public class World {
	
	private static World instence; 
	
    private QuadTree quadTree;
    private Map<Player, List<Entity>> absorptions; 
    
    public static World getInstence() {
    	if(instence == null) {
    		instence = new World(); 
    	}
    	return instence; 
    }
    
    /**
     * Crates a new World.
     * The world is composed of a quadTree that has a main boundary. 
     * The main boundary's size values are created using the config file values for map's length and width values.
     */
    private World() {
    	quadTree = new QuadTree(new Boundary(Config.MAP_WIDTH%2, Config.MAP_LENGTH%2, Config.MAP_WIDTH, Config.MAP_LENGTH), 0);  
    	absorptions = new HashMap<Player, List<Entity>>(); 
    	
    	FactoryPellet factory = new FactoryPellet(); 
    	for(int i = 0 ; Config.PELLETS_NUM > i ; i++ ) {
    		this.addEntity(factory.randomFabtique());
    	}
    }

    // Met à jour l'état global du monde (mouvements, collisions, etc.)
    public void update() {
        // Mise à jour du QuadTree et vérification des interactions
    }
    
    
    public Map<Player, List<Entity>> getAbsorptions() {
        return absorptions; 
    }

    /**
     * adds a player to the world by adding it to the quadTree
     * @param entity
     */
    public boolean addEntity(Entity entity) {
    	double x = entity.getPosition().getX();
    	double y = entity.getPosition().getY();

    	//Position normalization
    	if( x >= Config.MAP_WIDTH) {
    		entity.getPosition().setX(x%Config.MAP_WIDTH);
    	}
    	if( y >= Config.MAP_LENGTH) {
    		entity.getPosition().setY(y%Config.MAP_LENGTH);
    	}

        return quadTree.insert(entity);
    }

    public boolean removeEntity(Entity entity) {
    	return quadTree.removeEntity(entity);
    }

    /**
     * adds a player to this world
     * @param player
     * @return true if the adding is done successfully or not
     */
    public boolean addPlayer(Player player) {
    	boolean cellInserted = addEntity(player.getCells());
    	if(cellInserted) {
    		absorptions.put(player, new ArrayList<Entity>());
    	}
    	return cellInserted;
    }

    /**
     * adds an absorption associated to the player.
     * @param player
     * @param entity
     * @return true if the adding is done successfully
     */
    public boolean addAbsorption(Player player, Entity entity) {
    	return absorptions.get(player).add(entity);
    }


	public List<Entity> getEntitiesAround(Entity entity){
		return quadTree.getEntitiesAround(entity);
	}

    public boolean containsEntity(Entity entity) {
        return quadTree.contains(entity);
    }

    public boolean containsPlayer(Player player) {
        return absorptions.containsKey(player);
    }

}