package fr.unicaen.iutcaen.agario2.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.ArrayList;
import fr.unicaen.iutcaen.agario2.model.World;

public class Server {
    private ServerSocket serverSocket;
    private List<ClientHandler> clientHandlers;
    private World world;

    public Server(int port) throws IOException {
        // Initialisation du ServerSocket, de la liste des clients et du monde
        ServerSocket socket = new ServerSocket();

    }

    // Démarre le serveur et l'écoute des connexions
    public void start() {
        // Boucle accept() pour créer des ClientHandler pour chaque connexion
        
    }

    // Diffuse un message à tous les clients connectés
    public void broadcast(String message) {
        // Parcourt la liste des clients et envoie le message via chacun d'eux
    }

    // Boucle d'actualisation du monde à intervalle régulier (ex. toutes les 33ms)
    public void updateWorld() {
        // Met à jour l'état du monde et envoie les mises à jour aux clients
    }
}
