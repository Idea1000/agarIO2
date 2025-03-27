package fr.unicaen.iutcaen.networkProtocol;

import java.util.ArrayList;
import java.util.List;

import fr.unicaen.iutcaen.model.Boundary;
import fr.unicaen.iutcaen.model.Player;
import fr.unicaen.iutcaen.model.World;
import fr.unicaen.iutcaen.model.entities.Entity;

public class UpdateClientData extends ProtocolData{
	
	List<EntityData> entitiesAroundData; 
	
	double posCameraX; 
	double posCameraY; 
	double windowWidth; 
	double windowHight; 
	
	public UpdateClientData(World world, Player player, double windowWidth, double windowHight) {
		entitiesAroundData = new ArrayList<EntityData>(); 
		this.windowWidth = windowWidth; 
		this.windowHight = windowHight; 
		
		posCameraX = player.getPosition().getX() - windowWidth/2; 
		posCameraY = player.getPosition().getY() - windowHight/2; 
		List<Entity> entitiesAround = world.getEntitiesAround(new Boundary(posCameraX, posCameraY, windowWidth, windowHight)); 
		
		for(Entity entity : entitiesAround) {
			entitiesAroundData.add(new EntityData(entity)); 
		}
	}
	
	
	public List<Entity> getUpdateedList(){
		
		List<Entity> entitiesAround = new ArrayList<Entity>(); 
		for(EntityData entityData : entitiesAroundData) {
			entitiesAround.add(entityData.convertToEntity()); 
		}
		
		return entitiesAround; 
	}
	
	public Boundary getBoundary() {
		return new Boundary(posCameraX, posCameraY, windowWidth, windowHight);  
	}
	
}
