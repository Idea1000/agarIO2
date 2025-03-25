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

    private final Socket socket;
    private final ObjectInputStream in;
    private final ObjectOutputStream out;
    private final List<ClientHandler> clientHandlers;
    private final WorldHandler worldHandler;

    private volatile boolean ready = false;

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
            while (!socket.isClosed()) {
                Object obj = in.readObject();
                if (obj instanceof Message message) {
                    processReceivedMessage(message);
                }
            }
        } catch (Exception e) {
            System.err.println("Client déconnecté : " + socket.getRemoteSocketAddress());
        } finally {
            cleanup();
        }
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
