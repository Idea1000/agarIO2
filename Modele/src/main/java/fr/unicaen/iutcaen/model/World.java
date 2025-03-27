package fr.unicaen.iutcaen.model;

import fr.unicaen.iutcaen.ai.AI;
import fr.unicaen.iutcaen.config.Config;
import fr.unicaen.iutcaen.model.entities.Cell;
import fr.unicaen.iutcaen.model.entities.Entity;
import fr.unicaen.iutcaen.model.entities.Pellet;
import fr.unicaen.iutcaen.model.factories.FactoryAI;
import fr.unicaen.iutcaen.model.factories.FactoryPellet;
import fr.unicaen.iutcaen.model.quadtree.QuadTree;
import javafx.geometry.Point2D;


import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.Serializable;
import java.util.*;



public class World {
	
	private static World instance;
	
    private QuadTree quadTree;
    private Map<Player, List<Entity>> absorptions;

    protected List<AI> AIList;
    
    public static World getInstence() {
    	if(instance == null) {
            instance = new World();
    	}
    	return instance;
    }
    
    /**
     * Crates a new World.
     * The world is composed of a quadTree that has a main boundary. 
     * The main boundary's size values are created using the config file values for map's length and width values.
     */
    public World() {
    	quadTree = new QuadTree(new Boundary(0, 0, Config.MAP_WIDTH, Config.MAP_HEIGHT), 0);
    	absorptions = new HashMap<Player, List<Entity>>(); 
    	
    	FactoryPellet factory = new FactoryPellet(); 
    	for(int i = 0 ; Config.PELLETS_NUM > i ; i++ ) {
            Pellet pellet = factory.randomPellet();
    		this.addEntity(pellet);
    	}
    }

    // Met à jour l'état global du monde (mouvements, collisions, etc.)
    public void update() {
        // Mise à jour du QuadTree et vérification des interactions
        HashMap<Player, List<Entity>> up;

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
    	if( y >= Config.MAP_HEIGHT) {
    		entity.getPosition().setY(y%Config.MAP_HEIGHT);
    	}
        return quadTree.insert(entity);
    }

    public boolean removeEntity(Entity entity) {
    	return quadTree.removeEntity(entity);
    }


    public void addPellet(Pellet pellet) {
        // Ajoute la pastille à la liste et au QuadTree
        pellet.setMass(1);

        Random xpos = new Random();
        Random ypos = new Random();

        Point pos = new Point(xpos.nextDouble(0, 6), ypos.nextDouble(0, 6));
        pellet.setPosition(pos);

        quadTree.insert(pellet);
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


	public List<Entity> getEntitiesAround(Boundary boundary){
		return quadTree.query(boundary);
	}

    public boolean containsEntity(Entity entity) {
        return quadTree.contains(entity);
    }

    public boolean containsPlayer(Player player) {
        return absorptions.containsKey(player);
    }
    
    public boolean removePlayer(Player player) {
    	boolean result = true; 
    	absorptions.remove(player); 
    	for(Cell cell : player.getCells().getAllCells()) {
    		result = quadTree.removeEntity(cell);
    		if(result == false)
    			return false; 
    	}
    	return true; 
    }

}