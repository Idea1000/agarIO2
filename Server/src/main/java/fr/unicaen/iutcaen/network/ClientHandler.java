package fr.unicaen.iutcaen.network;

import java.net.Socket;
import java.util.List;

import fr.unicaen.iutcaen.model.Player;
import fr.unicaen.iutcaen.model.World;
import fr.unicaen.iutcaen.model.factories.FactoryPlayer;
import fr.unicaen.iutcaen.model.factories.IdDistributor;
import fr.unicaen.iutcaen.networkProtocol.Message;
import fr.unicaen.iutcaen.networkProtocol.TextData;
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

    public ClientHandler(Socket socket, List<ClientHandler> clientHandlers, WorldHandler worldHandler) throws IOException {
        this.socket = socket;
        this.clientHandlers = clientHandlers;
        this.worldHandler = worldHandler;

        out = new ObjectOutputStream(socket.getOutputStream());
        out.flush(); 
        in = new ObjectInputStream(socket.getInputStream());
    }
    
    /**
     * waits for new messages from the client.
     *  When a new message arrives, it is processed by processReceivedMessage function
     */
    @Override
    public void run() {
        try {
        	
        	//Creating an instance for the player 
        	player = FactoryPlayer.fabriquePlayer(); 
        	
        	//Sending the id ID to the client 
        	TextData textData = new TextData("client_id", Integer.toString(player.getId())); 
        	out.writeObject(textData);
        	out.flush();
        	
        	boolean result = getResult(); 
        	//Sending the id ID to the client
        	
        	//If the ID was sent successfully
        	if(result) {
        		
        		System.out.println("Client "+socket.getRemoteSocketAddress() +" : ID envoyé avec succès"); 
        		
        		//Sending the world information
            	WorldData worldData = new WorldData(worldHandler.getWorld());
            	out.writeObject(worldData);
            	out.flush();
            	//Sending the world information
            	
            	result = getResult();
            	
            	//If the world was sent successfully
            	if(result) {
            		
            		System.out.println("Client "+socket.getRemoteSocketAddress() +" : Monde envoyé avec succès"); 
            		worldHandler.addPlayer(player); //Adding the player instance to the world
            		ready = true; 
            		
                    while (!socket.isClosed()) {
                        Object obj = in.readObject();
                        if (obj instanceof Message message) {
                            processReceivedMessage(message);
                        }
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
     * Cleans up the connection with the client by closing the socket and removing the thread from the list.
     */
    private void cleanup() {
        try {
            socket.close();
        } catch (IOException ignored) {}
        clientHandlers.remove(this);
        System.out.println("Client " + socket.getRemoteSocketAddress() + " supprimé.");
    }

    /**
     * Sends a message to the client. 
     * @param message
     */
    public synchronized void sendMessage(Message message) {
        try {
            out.reset(); 
            out.writeObject(message);
            out.flush();
        } catch (IOException e) {
            System.err.println("Erreur d’envoi au client " + socket.getRemoteSocketAddress());
            cleanup();
        }
    }
    
    /**
     * checks weather the client is ready to play or not. 
     * @return
     */
    public synchronized boolean isReady() {
        return ready;
    }
    
    
    public synchronized void setReady(boolean value) {
        this.ready = value;
    }
    
    
    public void processReceivedMessage(Message message) {

    }
}
