package fr.unicaen.iutcaen.agario2.network;

import java.net.Socket;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import fr.unicaen.iutcaen.agario2.view.GameView;
import fr.unicaen.iutcaen.agario2.model.World;

public class Client {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private GameView gameView;
    private World world;

    public Client(String serverIp, int port) {
        // Initialisation de la connexion au serveur
    }

    // Connexion au serveur et initialisation des flux
    public void connect() {
        // Création du socket et des flux
    }

    // Envoi d'un message (ex. mouvement, action) au serveur
    public void sendMessage(String message) {
        // Utilise le PrintWriter pour envoyer le message
    }

    // Thread qui reçoit les mises à jour envoyées par le serveur
    public void receiveUpdates() {
        // Boucle de lecture pour mettre à jour le monde et rafraîchir la vue
    }
}
