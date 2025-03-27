package fr.unicaen.iutcaen.model.entities;

import fr.unicaen.iutcaen.model.Point;
import fr.unicaen.iutcaen.model.factories.IdDistributor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class CellPack extends Cell{

    private ArrayList<Cell> cells;
    private ObservableList<Cell> allCells = FXCollections.observableArrayList();
    public CellPack(int id, Point position, double mass, Color color) {
        super(id, position, mass, color);
        cells = new ArrayList<>();
        Cell cell = new Cell(IdDistributor.getInstance().getNextId(), position, mass+400, Color.color(Math.random(), Math.random(), Math.random()));
        cell.setUnsplit(true);
        allCells.add(cell);
        cells.add(cell); 
//        allCells.add(new Cell(IdDistributor.getInstance().getNextId(), position, mass, Color.ALICEBLUE));
//        allCells.add(new Cell(IdDistributor.getInstance().getNextId(), new Point(position.getX() + 100, position.getY() + 100), mass - 70, Color.BLACK));

//        allCells.add(new Cell(IdDistributor.getInstance().getNextId(), new Point(position.getX() + 100, position.getY() + 100), mass +50, Color.RED));
    }

    public void addCell(Cell cell){
        this.cells.add(cell);
    }

    public void removeCell(Cell cell){
        this.allCells.remove(cell);
    }



    public void deleteAllCells(){
        cells = null;
    }


    /**
     *
     *
     * @return the list of all the cells without any cellpack
     */
    public ObservableList<Cell> getAllCells(){
        return allCells;
    }
    
    public List<Cell> getCells(){
    	return cells; 
    }


    public void move(Point direction){
        for (int i = 0; i < allCells.size(); i++) {
            allCells.get(i).move(direction);
        }

        position = getCenter();
    }

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

    private void setNeighbor(){
        for (Cell cell : allCells) {
            cell.setNeighbor(allCells);
        }
    }
    
    /**
     * returns the cell (and not the cell pack) that has this entity inside of it, null if none of the cells has it.  
     * @param entity
     * @return the cell needed or null
     */
    public Cell getCellWhoHasInside(Entity entity) {
    	
    	for(Cell cell : allCells) {
    		
    		if(cell.isInside(entity)) {
    			
    			if(cell instanceof CellPack)
    				return ((CellPack) cell).getCellWhoHasInside(entity); 
    			else
    				return cell; 	
    		}	
    	}
    	return null; 
    }
    
    /**
     * returns the cell (and not the cell pack) that colides with this entity, null if none of the cells colides with it.  
     * @param entity
     * @return
     */
    public Cell getCellWhoColides(Entity entity) {
    	
    	for(Cell cell : allCells) {
    		
    		if(cell.colide(entity)) {
    			
    			if(cell instanceof CellPack)
    				return ((CellPack) cell).getCellWhoColides(entity); 
    			else
    				return cell; 
    		}	
    	}
    	return null; 
    }
    
    @Override
    public boolean colide(Entity e){
        for(Cell cell : cells) {
        	if(cell.colide(e)) return true; 
        }
        return false; 
    }
    
    @Override
    public boolean isInside(Entity e){
        for(Cell cell : allCells) {
        	if(cell.isInside(e)) return true; 
        }
        return false; 
    }
    
    @Override
    public Boolean absorbEntity(Entity entity) {

    	for(Cell cell : allCells) {

    		if(cell.absorbEntity(entity)) return true;  
    	}
    	return false; 
    }
    
    public boolean isEmpty() {
    	return cells.size() == 0; 
    }

    public Cell getBiggestCell(){
        double max = 0;
        Cell biggest = null;
        double size;
        for(Cell cell : cells){
            if(cell instanceof CellPack){
                Cell cell2 = ((CellPack) cell).getBiggestCell();
                size = cell2.getSize();
                if(size > max){
                    max = size;
                    biggest = cell2;
                }
            }
            else {
                size = cell.getSize();
                if(size > max){
                    max = size;
                    biggest = cell;
                }
            }
        }
        return biggest;
    }


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


    @Override
    public double getMass() {
        double sum = 0;
        for (Cell cell : allCells) {
            sum += cell.getMass();
        }
        return sum;
    }

    @Override
    public double getDiameter(){
        double sum = 0;
        for (Cell cell : cells) {
            sum += cell.getDiameter();
        }
        return sum;
    }
}
