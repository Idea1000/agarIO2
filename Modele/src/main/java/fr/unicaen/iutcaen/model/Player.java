package fr.unicaen.iutcaen.model;

import fr.unicaen.iutcaen.model.entities.Cell;
import fr.unicaen.iutcaen.model.entities.CellPack;
import fr.unicaen.iutcaen.model.entities.Entity;
import fr.unicaen.iutcaen.model.entities.Pellet;
import fr.unicaen.iutcaen.model.factories.FactoryCellPack;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class Player {

    private CellPack cells;

    public Player(Point position, double mass, Color color) {
        cells = (CellPack) new FactoryCellPack().fabrique(position, mass, color);
    }

    /**
     * Moves the player.
     * @param direction
     */
    public void movePlayer(Point direction) {
        cells.move(direction);
    }

    /**
     * tries to absorb the entity by removing it from the world and adding it's size to the current cell size
     * @param entity
     */
    public boolean absorb(Entity entity) {
    	World world = World.getInstence();
    	
    	if(world.containsEntity(entity) && world.containsPlayer(this)) {

    		boolean absorbed = cells.absorbEntity(entity); 
    		if(absorbed) {
    			world.removeEntity(entity);
                return true;
    		}
    	}
        return false;
    }

    public void eraseCell(){
        cells.getAllCells().removeAll();
        cells.deleteAllCells();

    }

    public boolean isDead(){
        if (getCells().getAllCells().isEmpty()){
            return true;
        }
        return false;
    }

    // Division cellulaire du joueur
    public void split() {
        cells.splitCells();
    }

    public CellPack getCells() {
        return cells;
    }

    public Point getPosition(){
        return cells.getPosition();
    }
    


    public Point getCenter(){return cells.getCenter(); }

    public void moveWithvector(Point vector){
        this.movePlayer(new Point(this.getCenter().getX() + vector.getX(), this.getCenter().getY() + vector.getY()));
    }

    // Getters et setters supplémentaires
}
