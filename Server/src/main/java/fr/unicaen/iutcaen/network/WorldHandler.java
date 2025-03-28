package fr.unicaen.iutcaen.network;

import java.util.List;

import fr.unicaen.iutcaen.model.Boundary;
import fr.unicaen.iutcaen.model.Player;
import fr.unicaen.iutcaen.model.World;
import fr.unicaen.iutcaen.model.entities.Entity;
import fr.unicaen.iutcaen.networkProtocol.EntityData;
import fr.unicaen.iutcaen.networkProtocol.Message;
import fr.unicaen.iutcaen.networkProtocol.PlayerData;
import fr.unicaen.iutcaen.networkProtocol.RemoveEntityData;
import fr.unicaen.iutcaen.networkProtocol.UpdateClientData;

public class WorldHandler extends Thread{
	
	
	private volatile World world; 
	private List<ClientHandler> clientHandlers; 
	
	public WorldHandler(List<ClientHandler> clientHandlers) {
		this.world = World.getInstence(); 
		this.clientHandlers = clientHandlers; 
	}
	
	@Override
	public void run() {
		while(!isInterrupted()) {
			
			try {
				this.sleep(33);
				sendUpdate(); 
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	
	
	public void sendUpdate() {
		UpdateClientData update; 
		for(ClientHandler client : clientHandlers) {
			if(client.isReady()) {
				update = new UpdateClientData(this.getWorld(), client.getPlayer(), client.getWindowWidth(), client.getWindowHight()); 
				client.sendUpdate(update);
			}
		}
	}
	
	
	
	public synchronized boolean addPlayer(Player player) {
		return this.world.addPlayer(player); 
	}
	
	public synchronized boolean removePlayer(Player player) {
		return this.world.removePlayer(player); 
	}
	
	public synchronized World getWorld() {
		return world; 
	}
	
    public void updatePlayer(PlayerData playerData) {
		Player player = playerData.convertToPlayer(); 
		getWorld().getPlayer(player.getId()).setCellPack(player.getCells());
	}

	public void updateWorld(UpdateClientData update) {
		List<Entity> entities = update.getUpdateedList(); 
		Boundary boundary = update.getBoundary(); 
		getWorld().updateEntitesAround(boundary, entities);
	}
	
	public void killPlayer(int playerID) {
		Player player = getWorld().getPlayer(playerID); 
		getWorld().removePlayer(player); 
	}

	public void absorbEntity(Player player, EntityData data) {
		Entity entity = data.convertToEntity(); 
		boolean absorbed = player.absorb(world, entity); 
	}

	public void removeEntity(RemoveEntityData data) {
		Entity ent = data.getData().convertToEntity(); 
		world.removeEntity(ent); 
	}
	
	
	
	
}
