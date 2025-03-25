package fr.unicaen.iutcaen.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import fr.unicaen.iutcaen.model.World;

import java.util.ArrayList;

public class Server {
	
	private static Server instance; 
	public static int PORT = 8000; 
	
    private ServerSocket serverSocket;
    private List<ClientHandler> clientHandlers;
    
    private WorldHandler worldHandler; 
    
    public static Server getInstance() throws IOException {
    	if(instance == null) {
    		instance = new Server(PORT); 
    	}
    	return instance; 
    }
    

    private Server(int port) throws IOException {
        serverSocket = new ServerSocket(port); 
        clientHandlers = new ArrayList<ClientHandler>(); 
        worldHandler = new WorldHandler(clientHandlers); 
        
    }

    /**
     * Starts the server and listens for new connections
     * @throws IOException 
     */
    public void start()  {
    	
    	Socket socket; 
    	ClientHandler client; 
    	worldHandler.start(); 
    	
        while(true) {
        	
        	try {
        		
        		System.out.println("Le serveur écoute"); 
        		socket = serverSocket.accept(); 
        		System.out.println("Demande reçu du client "+socket.getRemoteSocketAddress()); 
        		client = new ClientHandler(socket, clientHandlers, worldHandler); 
        		System.out.println("Thread crée pour le client "+socket.getRemoteSocketAddress()); 
        		client.start();
        		
        		synchronized(clientHandlers) {
        			clientHandlers.add(client); 
        		}
        		
        		System.out.println("Client "+socket.getRemoteSocketAddress()+"ajouté par le serveur"); 
        	}
        	catch(IOException e) {
        		e.printStackTrace();
        	}
        	
        }
    }
    
}
