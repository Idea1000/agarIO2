package fr.unicaen.iutcaen.viewTest;

import fr.unicaen.iutcaen.config.Config;
import fr.unicaen.iutcaen.model.Boundary;
import fr.unicaen.iutcaen.model.Player;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import fr.unicaen.iutcaen.model.World;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class MiniMap extends Pane {
    private World world;
    private Pane root;

    private Group worldGroup;

    private Rectangle map;
    private Pane worldPane;

    public MiniMap(World world, Pane root, Group worldGroup, Player p, Stage stage) {
        this.world = world;
        this.root = root;
        this.worldGroup = worldGroup;
        Pane mapPane = new Pane();
        double mapX = root.getWidth() - Config.MAP_SIZE;
        double mapY = root.getHeight() - Config.MAP_SIZE;

        Rectangle mapBg = new Rectangle(mapX, mapY, Config.MAP_SIZE, Config.MAP_SIZE);
        root.getChildren().add(mapBg);


        Rectangle pPosition = new Rectangle();
        pPosition.setFill(Color.RED);
        worldGroup.translateXProperty().addListener((observableValue, number, t1) -> {
            double x = p.getPosition().getX() - stage.getWidth()/2;
            double y = p.getPosition().getY() - stage.getHeight()/2;
            pPosition.setX(mapX +  (p.getPosition().getX() - stage.getWidth() / 2) / Config.MAP_WIDTH * Config.MAP_SIZE);
            pPosition.setY(mapY + (p.getPosition().getY() - stage.getHeight() / 2 )/ Config.MAP_HEIGHT * Config.MAP_SIZE);

            pPosition.setWidth(stage.getWidth() / Config.MAP_WIDTH * Config.MAP_SIZE);
            pPosition.setHeight(stage.getHeight() / Config.MAP_HEIGHT * Config.MAP_SIZE);

        });
        root.getChildren().add(pPosition);

    }

    public void update() {
    }
}
