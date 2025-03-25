package fr.unicaen.iutcaen.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.ArrayList;

public class Server {
	
	private static Server instance; 
	public static int PORT = 8000; 
	
    private ServerSocket serverSocket;
    private List<ClientHandler> clientHandlers;
    
    public static Server getInstance() throws IOException {
    	if(instance == null) {
    		instance = new Server(PORT); 
    	}
    	return instance; 
    }
    

    private Server(int port) throws IOException {
        serverSocket = new ServerSocket(port); 
        clientHandlers = new ArrayList<ClientHandler>(); 
    }

    /**
     * Starts the server and listens for new connections
     * @throws IOException 
     */
    public void start()  {
    	Socket socket; 
    	ClientHandler client; 
        while(true) {
        	
        	try {
        		System.out.println("Le serveur écoute"); 
        		socket = serverSocket.accept(); 
        		System.out.println("Demande reçu"); 
        		client = new ClientHandler(socket); 
        		System.out.println("Thread pour le client crée"); 
        		client.start();
        		clientHandlers.add(client); 
        		System.out.println("CLient ajouté par le serveur"); 
        	}
        	catch(IOException e) {
        		e.printStackTrace();
        	}
        	
        }
    }
    
}
