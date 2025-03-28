package fr.unicaen.iutcaen.model.factories;

import java.util.Random;

import fr.unicaen.iutcaen.config.Config;
import fr.unicaen.iutcaen.model.Player;
import fr.unicaen.iutcaen.model.Point;
import javafx.scene.paint.Color;

public class FactoryPlayer {
	
	private static Random r; 
	public static Player fabriquePlayer() {
		
		 r = new Random();
		 double x  = Config.MAP_WIDTH/2 ; //r.nextDouble(*/Config.MAP_WIDTH); 
		 double y = Config.MAP_HEIGHT/2; //r.nextDouble(Config.MAP_HEIGHT); 
		 Point p = new Point(x, y); 
		 
		 Color color = Color.color(r.nextDouble(1), r.nextDouble(1), r.nextDouble(1));
		 
		 return new Player(p, Config.PLAYER_INITIAL_SIZE, color); 
		 
	}
}
