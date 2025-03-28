package fr.unicaen.iutcaen.network;

import java.net.Socket;
import java.util.List;

import fr.unicaen.iutcaen.model.Boundary;
import fr.unicaen.iutcaen.model.Player;
import fr.unicaen.iutcaen.model.World;
import fr.unicaen.iutcaen.model.entities.Entity;
import fr.unicaen.iutcaen.model.factories.FactoryPlayer;
import fr.unicaen.iutcaen.model.factories.IdDistributor;
import fr.unicaen.iutcaen.networkProtocol.EntityData;
import fr.unicaen.iutcaen.networkProtocol.Message;
import fr.unicaen.iutcaen.networkProtocol.PlayerData;
import fr.unicaen.iutcaen.networkProtocol.RemoveEntityData;
import fr.unicaen.iutcaen.networkProtocol.TextData;
import fr.unicaen.iutcaen.networkProtocol.UpdateClientData;
import fr.unicaen.iutcaen.networkProtocol.WorldData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ClientHandler extends Thread {

    private final Socket socket;
    private final ObjectInputStream in;
    private final ObjectOutputStream out;
    private final List<ClientHandler> clientHandlers;
    private final WorldHandler worldHandler;

    private volatile boolean ready = false;
    private Player player;
    
    private double windowWidth; 
    private double windowHight; 

    public ClientHandler(Socket socket, List<ClientHandler> clientHandlers, WorldHandler worldHandler) throws IOException {
        this.socket = socket;
        this.clientHandlers = clientHandlers;
        this.worldHandler = worldHandler;

        out = new ObjectOutputStream(socket.getOutputStream());
        out.flush(); 
        in = new ObjectInputStream(socket.getInputStream());
    }
    
    /**
     *  sends all needed information (player and world instance) for the start of the game to the client. 
     *  once the client received this information, the server waits for the size of the client's window. 
     *  once the size received, a confirmation is sent then the server waits for updates from the client. 
     *  When a new message arrives, it is processed by processReceivedMessage function
     */
    @Override
    public void run() {
        try {
        	
        	//Creating an instance for the player 
        	player = FactoryPlayer.fabriquePlayer(); 
        	
        	//Sending the player instance to the client 
        	PlayerData playerData = new PlayerData(player); 
        	out.writeObject(playerData);
        	out.flush();
        	
        	boolean result = getResult(); 
        	//Sending the player instance to the client 
        	
        	//If the ID was sent successfully
        	if(result) {
        		
        		System.out.println("Client "+socket.getRemoteSocketAddress() +" : ID envoyé avec succès "+player.getId()); 
        		
        		//Sending the world information
            	WorldData worldData = new WorldData(worldHandler.getWorld());
            	out.writeObject(worldData);
            	out.flush();
            	//Sending the world information
            	
            	result = getResult();
            	
            	//If the world was sent successfully
            	if(result) {
            		
            		System.out.println("Client "+socket.getRemoteSocketAddress() +" : Monde envoyé avec succès");
            		
            		//Getting the windowSize
            		result  = receiveWindowSize(); 
            		sendResult(result); 
            		
            		
            		if(result) {
            			System.out.println("Client "+socket.getRemoteSocketAddress() +" : taille fenêtre reçu avec succès");
                		worldHandler.addPlayer(player); //Adding the player instance to the world
                		ready = true; //The client is ready
                		
                		//Waiting for updates from the client
                        while (!socket.isClosed()) {
                            Object obj = in.readObject();
                            	processMessage(obj); 

                        }//Waiting for updates from the client
                        
            		}//Getting the windowSize
            		
            		else {
            			System.err.println("impossible de recevoir la taille de la fenêtre du client : " + socket.getRemoteSocketAddress()); 
            		}
                    
            	}//If the world was sent successfully
            	
            	else {
            		System.err.println("impossible d'envoyer les informations de la partie au client : " + socket.getRemoteSocketAddress());
            	}
            	
        	}//If the ID was sent successfully
        	
        	else {
        		System.err.println("impossible d'envoyer l'ID au client : " + socket.getRemoteSocketAddress());
        	}
        	
        } catch (Exception e) {
            System.err.println("Client déconnecté : " + socket.getRemoteSocketAddress());
        } finally {
            cleanup();
        }
    }
    
    
    public void processTextData(TextData data) {
    	
    	String type = data.getType(); 
    	
		if(type.equalsIgnoreCase("WindowSize")) {
			String[] stringValues = data.getContent().split(","); 
			windowWidth = Double.parseDouble(stringValues[0]); 
			windowHight = Double.parseDouble(stringValues[2]);
		}
		
		else if (type.equalsIgnoreCase("Dead")) {
			String[] stringValues = data.getContent().split(","); 
			int playerID = Integer.parseInt(stringValues[0]); 
			worldHandler.killPlayer(playerID);
		}
    }
    
    public void processMessage(Object object) {
    	
    	if(object instanceof UpdateClientData) {
    		UpdateClientData update = (UpdateClientData) object; 
    		worldHandler.updateWorld(update); 
    	}
    	
    	if(object instanceof TextData) {
    		TextData textData = (TextData) object;
    		processTextData(textData); 
    	}
    	
    	if(object instanceof PlayerData) { 
    		PlayerData playerData = (PlayerData) object; 
    		worldHandler.updatePlayer(player, playerData); 
    	}
    	
    	if(object instanceof EntityData) {
    		EntityData data = (EntityData) object; 
    		worldHandler.absorbEntity(player, data); 
    	}
    	
    	else if (object instanceof RemoveEntityData) {
    		RemoveEntityData data = (RemoveEntityData) object; 
    		worldHandler.removeEntity(data); 
    	}
    	
    }
    
    
    
    public synchronized void sendUpdate(UpdateClientData update) {
    	try {
			out.writeObject(update);
			out.flush();
		} catch (IOException e) {
			System.err.println("Impossible d'envoyer la MAJ  au client  "+socket.getRemoteSocketAddress()+" : " + e.getMessage());
		}
    }
    
    /**
     * waits for a result message (ok or failed) from the client
     * @return the result (true = ok, false = fail)
     */
    public boolean getResult() {
    	try {
			Object object = in.readObject();
			if(object instanceof TextData) {
				TextData data = (TextData)object; 
				if(data.getType().equalsIgnoreCase("result")) {
					if(data.getContent().equalsIgnoreCase("ok")) return true; 
				}
			}
		} catch (ClassNotFoundException | IOException e) {
			System.err.println("Impossible de recevoir le résultat du client  "+socket.getRemoteSocketAddress()+" : " + e.getMessage());
		} 
    	return false; 
    }
    
    /**
     * sends a result to the client.
     * @param ok the result to send (true or false).
     */
    public void sendResult(boolean ok) {
        try {
        	
        	String content; 
        	if(ok)
        		content = "ok"; 
        	else
        		content = "fail"; 
        	
            TextData data = new TextData("result", content);  
            out.writeObject(data);  
            out.flush();
     
        } catch (IOException e) {
            System.err.println("Impossible d'envoyer le résultat au client  "+socket.getRemoteSocketAddress()+" : " + e.getMessage());
        }

    }
    
    /**
     * waits for a message containing the window size of the client and stores it in the needed attributes. 
     * @return true if the size was received successfully
     */
    public boolean receiveWindowSize() {
    	try {
			Object object = in.readObject();
			if(object instanceof TextData) {
				TextData data = (TextData)object; 
				
				if(data.getType().equalsIgnoreCase("WindowSize")) {
					processTextData(data); 
					return true; 
				}		

			}
			
		} catch (ClassNotFoundException | IOException e) {
			System.err.println("Impossible de recevoir la taille du fenêtre du client  "+socket.getRemoteSocketAddress()+" : " + e.getMessage());
		} 
    	return false; 
    }
    
    
    /**
     * Cleans up the connection with the client by closing the socket and removing the thread from the list.
     */
    private void cleanup() {
        try {
            socket.close();
            worldHandler.removePlayer(player); 
        } catch (IOException ignored) {}
        clientHandlers.remove(this);
        System.out.println("Client " + socket.getRemoteSocketAddress() + " supprimé.");
    }

    
    /**
     * checks weather the client is ready to play or not. 
     * @return true if the client s ready
     */
    public synchronized boolean isReady() {
        return ready;
    }
    
    
    public synchronized void setReady(boolean value) {
        this.ready = value;
    }
    
    public synchronized Player getPlayer() {
    	return player; 
    }
    
    public double getWindowWidth() {
    	return windowWidth; 
    }
    
    public double getWindowHight() {
    	return windowHight; 
    }
}
