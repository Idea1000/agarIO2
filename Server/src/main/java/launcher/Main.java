package launcher;

import java.io.IOException;

import fr.unicaen.iutcaen.network.Server;

public class Main {

	/**
	 * The main method of the servers
	 * @param args
	 * @throws IOException
	 */
	public static void main(String args[]) throws IOException {
		
		if(args.length > 0)
			Server.PORT = Integer.parseInt(args[0]); 
		
		Server server = Server.getInstance(); 
		server.start();
	}

}
