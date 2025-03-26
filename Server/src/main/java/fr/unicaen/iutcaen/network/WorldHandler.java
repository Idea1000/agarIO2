package fr.unicaen.iutcaen.network;

import java.util.List;

import fr.unicaen.iutcaen.model.World;
import fr.unicaen.iutcaen.networkProtocol.Message;

public class WorldHandler extends Thread{
	
	
	private World world; 
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
	
	
	
	
}
