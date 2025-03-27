package fr.unicaen.iutcaen.network;

import java.util.List;

import fr.unicaen.iutcaen.model.Player;
import fr.unicaen.iutcaen.model.World;
import fr.unicaen.iutcaen.networkProtocol.Message;

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
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	
	public synchronized boolean addPlayer(Player player) {
		return this.world.addPlayer(player); 
	}
	
	public synchronized boolean removePlayer(Player player) {
		return this.world.removePlayer(player); 
	}
	
	public World getWorld() {
		return world; 
	}
	
	
	
	
	
}
