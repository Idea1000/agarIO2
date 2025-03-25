package fr.unicaen.iutcaen.network;

import java.net.Socket;
import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ClientHandler extends Thread {
	
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private List<ClientHandler> clientHandlers;
    private WorldHandler worldHandler; 
    
    private boolean ready; 
    
    
    public ClientHandler(Socket socket, List<ClientHandler> clientHandlers, WorldHandler worldHandler) {
        this.socket = socket;
        this.clientHandlers = clientHandlers; 
        ready = false; 
        this.worldHandler = worldHandler; 
        try {
        	out = new ObjectOutputStream(socket.getOutputStream());
        	out.flush(); 
        	in = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		} 
    }
    
    /**
     * 
     */
    @Override
    public void run() {
    
    	Message message; 
    	
        while(!socket.isClosed()) {
        	try {
        		message = (Message) in.readObject(); 
        		processRecievedMessage(message); 
        	}
        	catch(Exception e) {
        		e.printStackTrace();
        	}
        }
        
        synchronized(clientHandlers) {
        	clientHandlers.remove(this); 
        }
        
        System.out.println("Client "+socket.getRemoteSocketAddress()+" déconnecté"); 
    }
    
    /**
     * Processes a message received from the client and does what is needed
     * @param message Message the message received from the client  
     */
    public void processRecievedMessage(Message message) {
    	
    }
    
    
    /**
     * Checks weather the client is ready to play or not
     * @return
     */
    public synchronized Boolean isReady() {
    	return ready; 
    }
    
    /**
     * Sends a message to the client
     * @param message the message to send
     */
    public synchronized void sendMessage(Message message) {
    	try {
			out.writeObject(message);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
}
