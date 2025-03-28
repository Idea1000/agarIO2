package fr.unicaen.iutcaen.network;

import java.io.*;
import java.net.*;
import java.util.List;
import java.util.Scanner;

import fr.unicaen.iutcaen.config.Config;
import fr.unicaen.iutcaen.model.Boundary;
import fr.unicaen.iutcaen.model.Player;
import fr.unicaen.iutcaen.model.World;
import fr.unicaen.iutcaen.model.entities.Entity;
import fr.unicaen.iutcaen.networkProtocol.EntityData;
import fr.unicaen.iutcaen.networkProtocol.PlayerData;
import fr.unicaen.iutcaen.networkProtocol.TextData;
import fr.unicaen.iutcaen.networkProtocol.UpdateClientData;
import fr.unicaen.iutcaen.networkProtocol.WorldData;
import fr.unicaen.iutcaen.view.Game;
import javafx.application.Platform;

public class Client extends Thread{

    public static String SERVERIP = "10.42.17.154";
    public static int PORT = 8000;
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    
    private volatile World world; 
    private volatile Player player; 
    
    private WorldHandler worldHandler; 


    public Client() {
        try {
            socket = new Socket(SERVERIP, PORT);
            
            out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(socket.getInputStream());
            
            worldHandler = new WorldHandler(this); 
            
            System.out.println("connecté au server: " + SERVERIP);
        } catch (IOException e) {
        	System.err.println("Erreur de connection au serveur : " + e.getMessage()); 
        }

    }
    
    /**
     * Starts a thread that waits for updates from the server.
     */
    @Override
    public void run() {
    	Object object; 
    	try {
    		//Waiting for updates from the server
            while (!socket.isClosed()) {
            	object = in.readObject(); 
            	processMessage(object); 
            }//Waiting for updates from the server
            
	    } catch (Exception e) {
	        System.err.println("déconnecté du serveur : " + socket.getRemoteSocketAddress());
			e.printStackTrace();
			System.exit(0);
	    } finally {
	        cleanup();
	    }
    }
    
    
    public void processMessage(Object object) {
    	
    	if(object instanceof UpdateClientData) {
    		UpdateClientData update = (UpdateClientData) object; 
    		updateWorld(update); 
    	}
    	
    }
    
    
    private void updateWorld(UpdateClientData update) {
		List<Entity> entities = update.getUpdateedList(); 
		Boundary boundary = update.getBoundary(); 
		world.updateEntitesAround(boundary, entities);
	}

	/**
     * closes the socket
     */
    public void cleanup() {
        try {
            socket.close();
            worldHandler.stop();
			this.stop();
			Platform.runLater(() -> {
				Game.stopCurrentGame();
			});
			System.exit(0);
        } catch (IOException ignored) {}
        System.out.println("Déconnexion du serveur.");
    }
    
    /**
     * waits for the WorldData sent by the server.
     * once received, it's transformed into an instance of World and stored in the attribute world 
     * @return true if the worldData was received successfully
     */
    public boolean receiveWorld()  {
    	try {
			Object object = in.readObject();
			if(object instanceof WorldData) {
				WorldData worldData = (WorldData)object; 
				this.world = worldData.convertIntoWorld(); 
				return true; 
			}
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		return false; 
    }
    
    public synchronized World getWorld() {
		return world;
	}

	public synchronized void setWorld(World world) {
		this.world = world;
	}

	public synchronized Player getPlayer() {
		return player;
	}

	public synchronized void setPlayer(Player player) {
		this.player = player;
	}

	/**
     * waits for the PlayerData sent by the server.
     * once received, it's transformed into an instance of Player and stored in the attribute palyer 
     * @return true if the playerData was received successfully
     */
    public boolean receivePlayer() {
    	try {
			Object object = in.readObject();
			if(object instanceof PlayerData) {
				PlayerData playerData = (PlayerData)object; 
				this.player = playerData.convertToPlayer(); 
				return true; 
			}
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		return false; 
    }
    
    /**
     * waits for a result message (ok or failed) from the server
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
			e.printStackTrace();
		} 
    	return false; 
    }
    

    /**
     * sends a result to the server.
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
            System.err.println("Error sending message: " + e.getMessage());
        }

    }
    
    /**
     * sends the width and the hight passed in parametere.
     * @param width
     * @param hight
     */
    public void sendWindowSize(double width, double hight) {
        try {
        	
        	String content = String.format("%f,%f",width, hight);  
        	
            TextData data = new TextData("WindowSize", content);  
            out.writeObject(data);  
            out.flush();
     
        } catch (IOException e) {
            System.err.println("Error sending message: " + e.getMessage());
        }
    }
    

    
    public synchronized void sendRegularUpdate(UpdateClientData update) {
    	try {
			out.writeObject(update);
			out.flush();
		} catch (IOException e) {
			System.err.println("Impossible d'envoyer la MAJ régulière  au serveur  "+socket.getRemoteSocketAddress()+" : " + e.getMessage());
		}
    }
    
    public void sendPlayerUpdate(PlayerData playerData) {
    	
    	try {
			out.writeObject(playerData);
			out.flush();
		} catch (IOException e) {
			System.err.println("Impossible d'envoyer la MAJ du joueur au serveur  "+socket.getRemoteSocketAddress()+" : " + e.getMessage());
		} 
    	
    }
    
    public void sendAbsorbedEntityUpdate(Entity entity) {
    	EntityData entityData = new EntityData(entity); 
    	try {
			out.writeObject(entityData);
			out.flush();
		} catch (IOException e) {
			System.err.println("Impossible d'envoyer la MAJ d'absorption d'une entité au serveur "+socket.getRemoteSocketAddress()+" : " + e.getMessage());
		} 
    }
    
    public void sendIsDeadData() {
    	TextData textData = new TextData("Dead", String.format("%d,true", player.getId())); 
    	try {
			out.writeObject(textData);
			out.flush();
		} catch (IOException e) {
			System.err.println("Impossible d'envoyer la MAJ de mort du joueur au serveur  "+socket.getRemoteSocketAddress()+" : " + e.getMessage());
		} 
    }
 
    public void launche() {
        try {
            
            //Receiving player instance
            boolean received = receivePlayer();
            if(received)
            	System.out.println("instance du joueur reçu : "+ getId()); 
            else
            	System.out.println("erreur de reception de l'instance du joueur"); 
            
            sendResult(received); 
          //Receiving player instance
            
            //If the Player instance was received successfully
            if(received) {
            	
                //Receiving the world
                received = receiveWorld(); 
                sendResult(received);
                
                //If the world was received successfully
                if(received) {
                	
                	System.out.println("Informations de la partie reçues avec succès "+world);
                	
                	//adding the player to the world 
                	world.addPlayer(player);
                	
                	//showing the fxml view + sending the window size + setting the window size in the world handler
                	Game.startGame(this, world, player, false);
                	this.sendWindowSize(Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);
                	worldHandler.setWidth(Config.SCREEN_WIDTH);
                	worldHandler.setHight(Config.SCREEN_HEIGHT);
                	
                	received = getResult(); 
                	//If we have the server confirmation
                	if(received) {
                		System.out.println("Confirmation reçue du serveur. Début de la partie.");
                		
                		start(); //Starting the client thread that receive updates from the server
                		worldHandler.start(); //Starting the thread that sends an update to the server every 33 millisecondes
                		
                	}//If we have the server confirmation
                	
                	else {
                		System.err.println("refus du serveur. Annulation de la partie.");
                		cleanup(); 
                	}
                	
                }//If the world was received successfully
                else
                	System.err.println("Impossible de recevoir les informations de la partie");
                
            }//If the Player instance was received successfully
            
            else
            	System.err.println("Impossible de recevoir l'instance du joueur");
            
            
        } catch (Exception e) {
            System.err.println("Erreur lors de la communication avec le serveur : " );
			e.printStackTrace();
        }
    }
}
