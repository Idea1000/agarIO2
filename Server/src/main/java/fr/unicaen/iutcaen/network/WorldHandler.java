package fr.unicaen.iutcaen.network;

import java.util.List;

import fr.unicaen.iutcaen.model.World;

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
				this.sendUpdate(); 
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	
	/**
	 * Prepares the update message to send to every Client. The message is generated using the current instance of the model 
	 * @return the update message generated.
	 */
	public Message prepareUpdate() {
		return new Message("Test", "Test"); 
	}
	
	/**
	 * gets the updated message from the prepareUpdate function and sends it to each client who is ready
	 */
	public void sendUpdate() {
		
		Message updateMessage = prepareUpdate(); 
		for(ClientHandler client : clientHandlers) {
			if(client.isReady()) {
				client.sendMessage(updateMessage);
			}
		}
		
	}
	
}
