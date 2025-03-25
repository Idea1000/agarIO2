package fr.unicaen.iutcaen.network;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {

    public static String SERVERIP = "10.42.17.154";
    public static int PORT = 8000;
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;


    public Client(String serverIp, int port) throws IOException {
        // Initialisation de la connexion au serveur

        try {
            // Create socket connection to the server
            socket = new Socket(SERVERIP, PORT);
            //socket.connect(socket.getRemoteSocketAddress());

            // Initialize input and output streams
            out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(socket.getInputStream());

            System.out.println("Connected to server: " + SERVERIP);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    // Envoi d'un message au serveur avec un objet Message
    public void sendMessage(String type, Object data) {
        try {
            Message message = new Message(type, data);  // Create message with type and data
            out.writeObject(message);  // Serialize and send the message object
            out.flush();

            System.out.println("Message sent: " + type +message.getData());
        } catch (IOException e) {
            System.err.println("Error sending message: " + e.getMessage());
        }

    }

    // Thread qui reçoit les mises à jour envoyées par le serveur
    public void receiveUpdates() {

        new Thread(() -> {
            try {
                while (true) {
                    Message message = (Message) in.readObject();  // Deserialize the received message
                    System.out.println("Update received: Type - " + message.getType() + ", Data - " + message.getData());
                    // Process the message and update the view or game state accordingly
                    // gameView.updateWorldState(message.getData()); // Example usage
                }
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error receiving updates: " + e.getMessage());
            }
        }).start();
    }

    // Fermer la connexion
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

    // Point d'entrée de l'application
    public static void main(String[] args) {
        try {
            Client client = new Client("10.42.17.154", 8000);
            // Example of sending a move message
            Scanner sc = new Scanner(System.in);
            while(true){
                String input = sc.nextLine();
                client.sendMessage("String", input);
            }
             // Sending movement coordinates (example)
        } catch (IOException e) {
            System.err.println("Error initializing client: " + e.getMessage());
        }
    }
}
