package fr.unicaen.iutcaen.model.entities;

import fr.unicaen.iutcaen.model.Point;
import fr.unicaen.iutcaen.model.factories.IdDistributor;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class CellPack extends Cell{

    private ArrayList<Cell> cells;
    public CellPack(int id, Point position, double mass, Color color) {
        super(id, position, mass, color);
        cells = new ArrayList<>();
        cells.add(new Cell(IdDistributor.getInstance().getNextId(), position, mass, Color.RED));
        /*cells.add(new Cell(IdDistributor.getInstance().getNextId(), new Point(position.getX() + 100, position.getY() + 100), mass - 70, Color.BLACK));
        cells.add(new Cell(IdDistributor.getInstance().getNextId(), new Point(position.getX() + 100, position.getY() + 100), mass +50, Color.RED));*/
    }

    public void addCell(Cell cell){
        this.cells.add(cell);
    }

    public void removeCell(Cell cell){
        this.cells.remove(cell);
    }

    protected void getCells(ArrayList<Cell> cells){
        for (Cell cell : this.cells) {
            cell.getCell(cells);
        }
    }

    /**
     *
     *
     * @return the list of all the cells without any cellpack
     */
    public ArrayList<Cell> getAllCells(){
        ArrayList<Cell> a = new ArrayList<>();
        getCells(a);
        return a;
    }


    public void move(Point direction){
        for (Cell cell : cells) {
            cell.move(direction);
        }

        position = getCenter();
    }


    public void split(){
        for (Cell cell : getAllCells()){
            cell.setNeighbor(getAllCells());
        }
    }
    
    /**
     * returns the cell (and not the cell pack) that has this entity inside of it, null if none of the cells has it.  
     * @param entity
     * @return the cell needed or null
     */
    public Cell getCellWhoHasInside(Entity entity) {
    	
    	for(Cell cell : cells) {
    		
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
    	
    	for(Cell cell : cells) {
    		
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
        for(Cell cell : cells) {
        	if(cell.isInside(e)) return true; 
        }
        return false; 
    }
    
    @Override
    public Boolean absorbEntity(Entity entity) {
    	for(Cell cell : cells) {
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
        ArrayList<Cell> cells = getAllCells();
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


}
