package fr.unicaen.iutcaen.network;

import java.io.IOException;

import fr.unicaen.iutcaen.model.Player;
import fr.unicaen.iutcaen.model.World;
import fr.unicaen.iutcaen.networkProtocol.PlayerData;
import fr.unicaen.iutcaen.networkProtocol.UpdateClientData;

public class WorldHandler extends Thread{
	
    private volatile double width; 
    private volatile double hight; 
    
    private Client client; 
    
    public WorldHandler(Client client) {
    	this.client = client; 
    }
	
    public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHight() {
		return hight;
	}

	public void setHight(double hight) {
		this.hight = hight;
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
		//UpdateClientData EntitiesUpdate;
		PlayerData playerUpdate = new PlayerData(client.getPlayer()); 
		//EntitiesUpdate = new UpdateClientData(client.getWorld(), client.getPlayer(), width, hight); 
		//client.sendRegularUpdate(EntitiesUpdate);
		client.sendPlayerUpdate(playerUpdate);
		
	}
	

	
	

}
