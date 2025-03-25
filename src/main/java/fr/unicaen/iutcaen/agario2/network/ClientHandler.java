package fr.unicaen.iutcaen.agario2.network;

import java.net.Socket;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStreamReader;

public class ClientHandler extends Thread {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public ClientHandler(Socket socket) {
        this.socket = socket;
        // Initialisation des flux d'entrée et sortie
    }

    @Override
    public void run() {
        // Boucle de lecture des messages envoyés par le client et mise à jour du serveur en conséquence
    }

    // Envoie un message au client
    public void sendMessage(String message) {
        // Utilise le PrintWriter pour envoyer le message
    }
}
