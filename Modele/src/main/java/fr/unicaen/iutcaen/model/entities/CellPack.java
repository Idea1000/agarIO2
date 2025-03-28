package fr.unicaen.iutcaen.model.entities;

import fr.unicaen.iutcaen.model.Point;
import fr.unicaen.iutcaen.model.factories.IdDistributor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * a cell pack is a cell that contains cells
 */
public class CellPack extends Cell{
    private ObservableList<Cell> allCells = FXCollections.observableArrayList();
    public CellPack(int id, Point position, double mass, Color color) {
        super(id, position, mass, color);
        Cell cell = new Cell(IdDistributor.getInstance().getNextId(), position, mass, Color.color(Math.random(), Math.random(), Math.random()));
        cell.setUnsplit(true);
        allCells.add(cell);
    }



    /**
     * @return the list of all the cells without any cellpack
     */
    public ObservableList<Cell> getAllCells(){
        return allCells;
    }

    /**
     * moves all the cells towards the designated Point
     * @param direction A point that defines the position desired
     */
    public void move(Point direction){
        for (int i = 0; i < allCells.size(); i++) {
            allCells.get(i).move(direction);
        }

        position = getCenter();
    }

    /**
     * Splits all the cells
     * if a cell can't be split it won't be
     */
    public void splitCells(){
        int t = allCells.size();
        for (int i = 0; i < t; i++) {
                Cell splittedCell = allCells.get(i).split();
                if (splittedCell != null){
                    allCells.add(splittedCell);
                }
                setNeighbor();
        }
    }

    /**
     * sets the neighbors of all the contains cells
     * this way the cells contain a copy of the array of the cellPack
     */
    private void setNeighbor(){
        for (Cell cell : allCells) {
            cell.setNeighbor(allCells);
        }
    }

    /**
     * checks if one cell collides with the entity
     * @param e The entity that may touch the cell
     * @return true if one cell touches the cell false otherwise
     */
    @Override
    public boolean colide(Entity e){
        for(Cell cell : allCells) {
        	if(cell.colide(e)) return true; 
        }
        return false; 
    }

    /**
     * checks if the entity is inside one of the cells
     * @param e the entity we check if it's inside the cell
     * @return true if the entity is inside false otherwise
     */
    @Override
    public boolean isInside(Entity e){
        for(Cell cell : allCells) {
        	if(cell.isInside(e)) return true; 
        }
        return false; 
    }

    /**
     * tries to eat an entity
     * sends the order to every contained cell
     * and stops when one of them succeeds
     * @param entity the entity eaten
     * @return true if the entity is eaten false otherwise
     */
    @Override
    public Boolean absorbEntity(Entity entity) {

    	for(Cell cell : allCells) {
    		if(cell.absorbEntity(entity)) return true;  
    	}
    	return false; 
    }

    /**
     * Checks if the cellPack is empty
     * @return true if there is no cells in the array
     */
    public boolean isEmpty() {
    	return allCells.isEmpty();
    }

    /**
     * Gets a average of the center of the different cells
     * Is mainly use to know where to put the center of the camera
     * @return A Point that represents the center of the cellPack
     */
    public Point getCenter(){
        ObservableList<Cell> cells = getAllCells();
        double avgX = 0;
        double avgY = 0;

        for (Cell cell : cells) {
            avgX += cell.getPosition().getX();
            avgY += cell.getPosition().getY();
        }
        avgX /= cells.size();
        avgY /= cells.size();
        return new Point(avgX, avgY);
    }

    /**
     * Adds up the mass of all the contained cells and returns it
     * @return the sum of the mass of each cell
     */
    @Override
    public double getMass() {
        double sum = 0;
        for (Cell cell : allCells) {
            sum += cell.getMass();
        }
        return sum;
    }
}
