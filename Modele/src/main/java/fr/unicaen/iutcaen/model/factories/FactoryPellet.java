package fr.unicaen.iutcaen.model.factories;

import java.util.Random;

import fr.unicaen.iutcaen.Config;
import fr.unicaen.iutcaen.model.Point;
import fr.unicaen.iutcaen.model.entities.Entity;
import fr.unicaen.iutcaen.model.entities.Pellet;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

public class FactoryPellet extends FactoryEntity {

    @Override
    public Entity fabrique(Point position, double mass, Color color) {
        return new Pellet(IdDistributor.getInstance().getNextId(), position, mass, color);
    }
    
    /**
     * Generates and returns a pellet with random values.
     * @param mapLength the length of the map of the game
     * @param mapWidth this width of the map of the game
     * @return Pellet the generated pellet
     */
    public Entity randomFabtique() {
    	Random r = new Random(); 
    	
    	double randomX = r.nextDouble(Config.MAP_WIDTH); 
    	double randomY = r.nextDouble(Config.MAP_LENGTH);
    	
    	Point p = new Point(randomX, randomY); 
    	
    	double randomMass = r.nextDouble(10); 
    	
    	Color color = Color.color(r.nextDouble(255), r.nextDouble(255), r.nextDouble(255)); 
    	
    	return new Pellet(IdDistributor.getInstance().getNextId(), p, randomMass, color); 
    }
}
