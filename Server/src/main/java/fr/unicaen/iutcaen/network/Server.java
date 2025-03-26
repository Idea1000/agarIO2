package fr.unicaen.iutcaen.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import fr.unicaen.iutcaen.model.World;

import java.util.ArrayList;

public class Server {

    private static Server instance;
    public static int PORT = 8000;

    private final ServerSocket serverSocket;
    private final List<ClientHandler> clientHandlers;
    private final WorldHandler worldHandler;

    /**
     * Gets the instance of the server
     * @return the instance of the server
     * @throws IOException
     */
    public static Server getInstance() throws IOException {
        if (instance == null) {
            instance = new Server(PORT);
        }
        return instance;
    }

    /**
     * Creates a Server object
     * @param port
     * @throws IOException
     */
    private Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        clientHandlers = new CopyOnWriteArrayList<>();
        worldHandler = new WorldHandler(clientHandlers);
    }

    /**
     * Starts the server and can handle a client
     */
    public void start() {
        worldHandler.start();

        while (true) {
            try {
                System.out.println("En attente de connexions...");
                Socket socket = serverSocket.accept();
                System.out.println("Connexion de : " + socket.getRemoteSocketAddress());

                ClientHandler client = new ClientHandler(socket, clientHandlers, worldHandler);
                clientHandlers.add(client);
                client.start();

                System.out.println("Client ajouté : " + socket.getRemoteSocketAddress());
            } catch (IOException e) {
                System.err.println("Erreur de connexion : " + e.getMessage());
            }
        }
    }
}