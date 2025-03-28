package fr.unicaen.iutcaen.view;

import java.util.List;

import fr.unicaen.iutcaen.config.Config;
import fr.unicaen.iutcaen.model.Boundary;
import fr.unicaen.iutcaen.model.Player;
import fr.unicaen.iutcaen.model.Point;
import fr.unicaen.iutcaen.model.World;
import fr.unicaen.iutcaen.model.entities.Pellet;
import fr.unicaen.iutcaen.model.entities.Virus;
import fr.unicaen.iutcaen.model.factories.FactoryPellet;
import fr.unicaen.iutcaen.model.entities.Cell;
import fr.unicaen.iutcaen.model.entities.Entity;
import fr.unicaen.iutcaen.network.Client;
import fr.unicaen.iutcaen.networkProtocol.PlayerData;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Game2 extends Application{
	
	
	private Player player; 
	
    private boolean local; 
    
    private World world; 
    
    private static Stage currentStage;

    private static Client client;
    
    private double mX; 
    private double mY; 
    
    boolean space; 
    Pane worldPane; 
    
    Timeline timeline; 
    
    Group cameraGroup; 
    
    List<Entity> entities; 
    
    Point vector; 
    
    
    public Game2(Client client, World world, Player player, boolean local) {
    	this.local = local; 
    	this.world = world; 
    	this.player = player;
        this.client = client;
	}
    
    public static void startGame(Client client, World world, Player player, boolean local) {
        Game2 game = new Game2(client, world, player, local);
        Stage stage = new Stage();
        try {
            game.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void stopCurrentGame() {
        if (currentStage != null) {
            currentStage.close();
            currentStage = null;
        }
    }

	@Override
	public void start(Stage stage) throws Exception {
		
		cameraGroup = new Group();
		worldPane = new Pane(cameraGroup);
		
        Scene scene = new Scene(worldPane, Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);
        
        worldPane.prefHeightProperty().bind(scene.heightProperty());
        worldPane.prefWidthProperty().bind(scene.widthProperty());
        
        worldPane.setOnMouseMoved(mouseEvent -> {
            mX = mouseEvent.getX();
            mY = mouseEvent.getY();
            vector = new Point(mX - player.getCenter().getX(), mY - player.getCenter().getY()); 
        });
        
        worldPane.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.SPACE){
                if (!space){
                    player.split();
                    space = true;
                }
            }
        });
        worldPane.setOnKeyReleased(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.SPACE){
                space = false;
            }
        });
        
        
        currentStage = stage;
        
        stage.onCloseRequestProperty().addListener(evt -> {
        	if(client != null)
                client.cleanup();
                System.exit(0);
        });
        
        worldPane.widthProperty().addListener((obs, _old, _new)->{
        	if(local == false) {
            	client.sendWindowSize((double)_new, (double) worldPane.getHeight()); 
        	}
        });
        
        worldPane.heightProperty().addListener((obs, _old, _new)->{
        	if(local == false) {
            	client.sendWindowSize((double) worldPane.getWidth(), (double)_new); 
        	}
        });
        
        
        worldPane.setStyle("-fx-background-color: white;");

        stage.setTitle("Agar.io Clone");
        stage.setScene(scene);
        stage.show();
        
        worldPane.requestFocus();
        
        launch(); 
	}
	
	public void launch() {
		
		drawBoundary(); 
		
         timeline = new Timeline(new KeyFrame(Duration.millis(33), event -> {
        	 
        	 if(player.isDead()) {
        		System.exit(0);  
        	 }
        	 
        	if (vector != null)
        		player.movePlayer(new Point(mX, mY)) ; 
        	drawBoundary(); 
        	
        	for (Entity entity : entities) {
        	    if (entity != player.getCells() && isColliding(player.getCells(), entity)) {
        	        boolean absorbed = player.absorb(world, entity);
        	        if(absorbed) {
        	        	if(local == false) {
        	        		if(!(entity instanceof Virus) )
        	        			client.sendAbsorbedEntityUpdate(entity); 
        	        		else {
        	        			client.sendPlayerUpdate(new PlayerData(player)); 
        	        			client.sendRemoveEntity(entity); 
        	        		}
        	        	}
        	        }
        	    }
        	}
        	
        }));
        
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
	}
	
	
	public void drawBoundary() {
		double paneWidth = worldPane.getScene().getWidth(); 
		double paneHeight = worldPane.getScene().getHeight(); 
		
		double posCameraX = player.getPosition().getX() - paneWidth  / 2;  
		double posCameraY = player.getPosition().getY() - paneHeight  / 2;

		double margin = 100;

		Boundary viewBoundary = new Boundary(
				posCameraX,
				posCameraY,
				posCameraX + paneWidth + margin,
				posCameraY + paneHeight + margin
		);
		
		
		entities = world.getEntitiesAround(viewBoundary); 
		cameraGroup.getChildren().clear();
		
		double offsetX = paneWidth / 2 - player.getPosition().getX();
		double offsetY = paneHeight / 2 - player.getPosition().getY();

		cameraGroup.setTranslateX(offsetX);
		cameraGroup.setTranslateY(offsetY);
		
		// Par exemple, une taille de référence
		double baseSize = 100.0; 
		double playerSize = player.getCells().getSize();

		// Calculer le facteur de scale : plus le joueur grossit, plus scaleFactor diminue (zoom out)
		double scaleFactor = baseSize / playerSize;

		// On peut éventuellement contraindre le zoom à des valeurs raisonnables
		scaleFactor = Math.max(0.5, Math.min(2.0, scaleFactor));

		cameraGroup.setScaleX(scaleFactor);
		cameraGroup.setScaleY(scaleFactor);
		
		drawEntites(entities); 
		drawPlayer(); 
	}

	private void drawPlayer() {
		
		List<Cell> cells = player.getCells().getAllCells();
		
		for(Cell cell : cells) {
	        Circle c = new Circle();
	        c.setCenterX(cell.getPosition().xProperty().get());
	        c.setCenterY(cell.getPosition().yProperty().get());
	        c.setRadius(cell.getSize());
	        c.setFill(Color.RED);
	        cameraGroup.getChildren().add(c);
		}
		

	}

	private void drawEntites(List<Entity> entities) {
		for(Entity entity : entities) {
			
			if (entity == player.getCells()) continue;
			
	        Circle c = new Circle();
	        c.setCenterX(entity.getPosition().xProperty().get());;
	        c.setCenterY(entity.getPosition().yProperty().get());
	        c.setRadius(entity.getSize());
	        c.setFill(entity.getColor());
	        cameraGroup.getChildren().add(c);
		}
	}
	
	private boolean isColliding(Entity a, Entity b) {
	    double dx = a.getPosition().getX() - b.getPosition().getX();
	    double dy = a.getPosition().getY() - b.getPosition().getY();
	    double distanceSquared = dx * dx + dy * dy;

	    double radiusA = a.getSize();
	    double radiusB = b.getSize();

	    double minDistance = radiusA + radiusB;

	    return distanceSquared <= minDistance * minDistance;
	}
	
	private double lerp(double start, double end, double alpha) {
	    return start + (end - start) * alpha;
	}
	
}
