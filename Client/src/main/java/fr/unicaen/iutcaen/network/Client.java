package fr.unicaen.iutcaen.network;

import java.io.*;
import java.net.*;
import java.util.Scanner;

import fr.unicaen.iutcaen.model.World;
import fr.unicaen.iutcaen.networkProtocol.TextData;
import fr.unicaen.iutcaen.networkProtocol.WorldData;

public class Client extends Thread{

    public static String SERVERIP = "10.42.17.154";
    public static int PORT = 8000;
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    
    private World world; 
    private String id; 


    public Client() throws IOException {
        try {
            socket = new Socket(SERVERIP, PORT);
            
            out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(socket.getInputStream());

            System.out.println("Connected to server: " + SERVERIP);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    
    
    public boolean receiveID()  {
    	try {
			Object object = in.readObject();
			if(object instanceof TextData) {
				TextData textData = (TextData)object; 
				if(textData.getType().equalsIgnoreCase("client_id")) {
					id = textData.getContent(); 
					return true; 
				}
			}
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		return false; 
    }
    
    public boolean receiveWorld()  {
    	try {
			Object object = in.readObject();
			if(object instanceof WorldData) {
				WorldData worldData = (WorldData)object; 
				this.world = worldData.getWorld(); 
				return true; 
			}
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		return false; 
    }

    
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

    
    public void receiveUpdates() {
    	
    }

    
    public void close() {
        try {
            if (socket != null) {
                socket.close();
                System.out.println("Connection closed.");
            }
        } catch (IOException e) {
            System.err.println("Error closing connection: " + e.getMessage());
        }
    }
    

    
    public static void main(String[] args) {
        try {
            Client client = new Client();
            
            boolean received = client.receiveID();
            if(received)
            	System.out.println("ID received : "+client.getId()); 
            else
            	System.out.println("erreur ID"); 
            
            client.sendResult(received); 
            
            received = client.receiveWorld(); 
            
            if(received)
            	System.out.println("world received"+client.world);
            else
            	System.out.println("erreur world");
            client.sendResult(received); 
            
        } catch (IOException e) {
            System.err.println("Error initializing client: " + e.getMessage());
        }
    }
}
