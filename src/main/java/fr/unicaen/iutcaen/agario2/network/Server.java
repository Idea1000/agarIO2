package fr.unicaen.iutcaen.agario2.network;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.ArrayList;
import fr.unicaen.iutcaen.agario2.model.World;

public class Server {

    final static int PORT = 8000;
    private static Server instance;
    protected ServerSocket serverSocket;
    protected List<ClientHandler> clientHandlers;

    public static Server getInstance() throws IOException {
        if(instance == null){
            instance = new Server(PORT);
        }
        return instance;
    }

    private Server(int port) throws IOException {
        // Initialisation du ServerSocket, de la liste des clients et du monde
        serverSocket = new ServerSocket(port);
        clientHandlers = new ArrayList<ClientHandler>();
    }

    /**
     * Starts the server and waits for new connection
      */
    public void start() {
        Socket socket;

        while(true){
            try {
                socket = serverSocket.accept();
                clientHandlers.add(new ClientHandler(socket));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
