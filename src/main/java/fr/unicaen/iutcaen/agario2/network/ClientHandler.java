package fr.unicaen.iutcaen.agario2.network;

import java.io.*;
import java.net.Socket;

public class ClientHandler extends Thread {
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private boolean ready;

    public ClientHandler(Socket socket) throws IOException {
        this.socket = socket;
        in = new ObjectInputStream(socket.getInputStream());
        out = new ObjectOutputStream(socket.getOutputStream());
    }

    @Override
    public void run() {
        while(true){
            try{
                Message message = (Message) in.readObject();
                processMessage(message);
            } catch (IOException e) {

            } catch (ClassNotFoundException e) {

            }
        }
    }

    public void sendMessage(Message message){
        try {
            out.writeObject(message);
            out.flush();
        } catch (IOException e) {

        }
    }

    public void processMessage(Message message){

    }

}
