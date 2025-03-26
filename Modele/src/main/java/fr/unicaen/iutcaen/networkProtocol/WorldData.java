package fr.unicaen.iutcaen.networkProtocol;

import fr.unicaen.iutcaen.model.World;

public class WorldData extends ProtocolData{
	
	private World world; 
	
	public WorldData(World world) {
		this.world = world; 
	}
	
	public World getWorld() {
		return world; 
	}

}
