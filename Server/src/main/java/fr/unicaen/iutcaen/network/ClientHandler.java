package fr.unicaen.iutcaen.network;

import java.net.Socket;
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

    public ClientHandler(Socket socket) {
        this.socket = socket;
        try {
        	out = new ObjectOutputStream(socket.getOutputStream());
        	out.flush(); 
        	in = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		} 
    }

    @Override
    public void run() {
    
    	Message message; 
        while(!socket.isClosed()) {
        	try {
        		message = (Message) in.readObject(); 
        		System.out.println("type : "+message.getType()); 
        		System.out.println("message : "+((String)message.getData()) ); 
        	}
        	catch(Exception e) {
        		e.printStackTrace();
        	}
        }
        System.out.println("Client déconnecté"); 
    }
    
}
