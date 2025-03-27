package fr.unicaen.iutcaen.networkProtocol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.unicaen.iutcaen.config.Config;
import fr.unicaen.iutcaen.model.Boundary;
import fr.unicaen.iutcaen.model.Player;
import fr.unicaen.iutcaen.model.World;
import fr.unicaen.iutcaen.model.entities.Entity;

public class WorldData extends ProtocolData{
	
	private Map<PlayerData, List<EntityData>> absorptionsData;
	private List<EntityData> entitiesData; 
	
	
	public WorldData(World world) {
		absorptionsData = new HashMap<PlayerData, List<EntityData>>(); 
		entitiesData = new ArrayList<EntityData>(); 
		
		//Transforming the absorptions map into a map of absorptionsData
		Map<Player, List<Entity>> absorptions = world.getAbsorptions();
		for(Player player : absorptions.keySet()) {
			
			List<Entity> absorbedEntities = absorptions.get(player); 
			List<EntityData> absorbedEntitiesData = new ArrayList<EntityData>(); 
			
			for(Entity absorbedEntity : absorbedEntities) {
				absorbedEntitiesData.add(new EntityData(absorbedEntity)); 
			}
			
			PlayerData playerData = new PlayerData(player); 
			
			absorptionsData.put(playerData, absorbedEntitiesData); 
		}//Transforming the absorptions map into a map of absorptionsData
		
		//Transforming the entities into EntityData
		List<Entity> entities = world.getEntitiesAround(new Boundary(0, 0, Config.MAP_WIDTH, Config.MAP_HEIGHT)); 
		for(Entity entity : entities) {
			entitiesData.add(new EntityData(entity)); 
		}//Transforming the entities into EntityData
		
	}
	
	public World convertIntoWorld() {
		
		World world = new World(); 
		
		//Transforming the absorptionsData map into a map of absorptions
		for(PlayerData playerData : absorptionsData.keySet()) {
			
			Player player = playerData.convertToPlayer(); 
			world.addPlayer(player); 
			
			List<EntityData> absorbedEntitiesData = absorptionsData.get(playerData); 
			for(EntityData absorbedEntityData : absorbedEntitiesData) {
				world.addAbsorption(player, absorbedEntityData.convertToEntity()); 
			}
			 
		}//Transforming the absorptionsData map into a map of absorptions
		
		//Transforming the entitiesData into Entities
		for(EntityData entity : entitiesData) {
			world.addEntity(entity.convertToEntity()); 
		}//Transforming the entitiesData into Entities
		
		return world; 
		
	}
	

}
