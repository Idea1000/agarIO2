package fr.unicaen.iutcaen.agario2.view;

import fr.unicaen.iutcaen.agario2.model.Player;
import fr.unicaen.iutcaen.agario2.model.entities.Cell;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

public class PlayerView {

    private Player player;
    public PlayerView(Player player, Pane root) {
        this.player = player;
        for (Cell cell : player.getCells().getAllCells()) {
            Circle c = new Circle();
            SimpleDoubleProperty p = new SimpleDoubleProperty(cell.getPosition().getX());
            c.centerXProperty().bind(cell.getPosition().xProperty());
            c.centerYProperty().bind(cell.getPosition().yProperty());
            c.radiusProperty().bindBidirectional(new SimpleDoubleProperty(cell.getSize()));
            c.setFill(cell.getColor());
            root.getChildren().add(c);
        }

    }
}
