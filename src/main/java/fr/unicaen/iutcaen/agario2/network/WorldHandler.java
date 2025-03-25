package fr.unicaen.iutcaen.agario2.network;

import fr.unicaen.iutcaen.agario2.model.World;

import java.net.Socket;
import java.util.List;

public class WorldHandler extends Thread{

    World world;
    List<ClientHandler> clientsHandlers;
    public WorldHandler(List<ClientHandler> clientsHandlers) {
        world = new World();
        this.clientsHandlers = clientsHandlers;
    }

    @Override
    public void run() {
        while(true){
            try {
                sleep(33);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Message message = new Message("World", world);
            for(ClientHandler client : clientsHandlers){

                client.sendMessage(message);
            }
        }
    }

    public void

}
