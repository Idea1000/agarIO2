package fr.unicaen.iutcaen.networkProtocol;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import fr.unicaen.iutcaen.model.Point;
import fr.unicaen.iutcaen.model.entities.*;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.paint.Color;

public class EntityData extends ProtocolData{
	
	private String type; 
    private int id;
    private double x; 
    private double y; 
    private double mass;
    private double colorR;
    private double colorB;
    private double colorG;
    
    private List<EntityData> cellsData; //Only for CellPack
    
    public EntityData(Entity entity) {
    	id = entity.getId(); 
    	x = entity.getPosition().getX();
    	y = entity.getPosition().getY(); 
    	mass = entity.getMass(); 
    	colorR = entity.getColor().getRed(); 
    	colorB = entity.getColor().getBlue();
    	colorG = entity.getColor().getGreen(); 
    	
    	type = entity.getClass().getSimpleName();  
    	
    	if(entity instanceof CellPack) {
    		cellsData = new ArrayList<EntityData>(); 
    		
    		CellPack cellpack = (CellPack) entity; 
    		List<Cell> cells =  cellpack.getCells(); 
    		for(Cell cell : cells) {
    			cellsData.add(new EntityData(cell)); 
    		}
    	}
    }
    
    public Entity convertToEntity() {
    	
    	Point point = new Point(x, y); 
    	Color color = Color.color(colorR, colorG, colorB); 
    	
    	Entity entity  = null; 
    	
    	if(type.equalsIgnoreCase("Cell"))
    		entity = new Cell(id, point, mass, color); 
    	
    	else if(type.equalsIgnoreCase("CellPack")){
    		
    		entity = new CellPack(id, point, mass, color);
    		CellPack cellPack = (CellPack) entity; 
 
    		for(EntityData entityData : this.cellsData) {
    			cellPack.addCell((Cell) entityData.convertToEntity()); 
    		}
    	
    	}

		else if (type.equalsIgnoreCase("Virus")){
			entity = new Virus(id, point, mass, color);
		}
    	
    	else if(type.equalsIgnoreCase("Pellet"))
    		entity = new Pellet(id, point, mass, color); 
    	
    	return entity; 
    		
    }
    
    public List<EntityData> getCellsData(){
    	return cellsData; 
    }

}
