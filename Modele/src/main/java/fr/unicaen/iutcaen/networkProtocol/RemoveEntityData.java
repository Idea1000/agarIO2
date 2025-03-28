package fr.unicaen.iutcaen.networkProtocol;

import fr.unicaen.iutcaen.model.entities.Entity;

public class RemoveEntityData extends ProtocolData{
	
	EntityData data; 
	
	public RemoveEntityData(Entity ent) {
		data = new EntityData(ent); 
	}
	
	public EntityData getData() {
		return data; 
	}
}
