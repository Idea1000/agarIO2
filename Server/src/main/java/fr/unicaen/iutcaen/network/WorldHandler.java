package fr.unicaen.iutcaen.network;

import java.util.List;

import fr.unicaen.iutcaen.model.Boundary;
import fr.unicaen.iutcaen.model.Player;
import fr.unicaen.iutcaen.model.Point;
import fr.unicaen.iutcaen.model.World;
import fr.unicaen.iutcaen.model.entities.Cell;
import fr.unicaen.iutcaen.model.entities.Entity;
import fr.unicaen.iutcaen.networkProtocol.EntityData;
import fr.unicaen.iutcaen.networkProtocol.Message;
import fr.unicaen.iutcaen.networkProtocol.PlayerData;
import fr.unicaen.iutcaen.networkProtocol.RemoveEntityData;
import fr.unicaen.iutcaen.networkProtocol.UpdateClientData;
import javafx.collections.ObservableList;

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
		int nb = clientHandlers.size(); 
		
		for(int i = 0 ; nb > i ; i++) {
			
			ClientHandler client = clientHandlers.get(i); 
			
			if(client.isReady()) {
				if(client.getPlayer().isDead()) {
					clientHandlers.remove(client); 
				}
				else {
					update = new UpdateClientData(this.getWorld(), client.getPlayer(), client.getWindowWidth(), client.getWindowHight()); 
					client.sendUpdate(update);
				}
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
	
    public void updatePlayer(Player player2, PlayerData playerData) {
		Player player = playerData.convertToPlayer(); 
		
		ObservableList<Cell> cells = player.getCells().getAllCells(); 
		
		ObservableList<Cell> cells2 = player2.getCells().getAllCells(); 
		
		cells2.clear();
		
		for(Cell cell : cells) {
			cells2.add(cell); 
		}
		
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
