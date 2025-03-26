package fr.unicaen.iutcaen.viewTest;

import fr.unicaen.iutcaen.model.Player;
import fr.unicaen.iutcaen.model.entities.Cell;
import javafx.beans.property.SimpleDoubleProperty;

import javafx.scene.Group;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
public class PlayerView {
    private Player player;

    public PlayerView(Player player, Pane root) {
        this.player = player;

        ObservableList<Cell> cells = this.player.getCells().getAllCells();


        cells.addListener((ListChangeListener<Cell>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    for (Cell addedCell : change.getAddedSubList()) {
                        addCellLogic(addedCell, root);
                    }
                }
                if (change.wasRemoved()) {
                    for (Cell removedCell : change.getRemoved()) {
                        removeCellLogic(removedCell, root);  // Handle the removal logic
                    }
                }
            }
        });

        // Apply the logic to all existing cells
        for (Cell cell : cells) {
            addCellLogic(cell, root);
        }
    }

    private void addCellLogic(Cell cell, Pane root) {
        Circle c = new Circle();
        SimpleDoubleProperty p = new SimpleDoubleProperty(cell.getPosition().getX());

        c.centerXProperty().bind(cell.getPosition().xProperty());
        c.centerYProperty().bind(cell.getPosition().yProperty());

        cell.getMassProperty().addListener((observableValue, number, t1) -> {
            c.setRadius(cell.getSize());
            System.out.println(t1);
        });

        c.radiusProperty().bindBidirectional(new SimpleDoubleProperty(cell.getSize()));
        c.setFill(cell.getColor());

        root.getChildren().add(c);
    }

    private void removeCellLogic(Cell cell, Pane root) {
        // Assuming there is a method to find the Circle corresponding to a Cell
        // This is just a basic assumption and may need adjustments based on your actual logic
        Circle circleToRemove = findCircleForCell(cell, root);
        if (circleToRemove != null) {
            root.getChildren().remove(circleToRemove);
        }
    }

    private Circle findCircleForCell(Cell cell, Pane root) {
        for (Node node : root.getChildren()) {
            if (node instanceof Circle) {
                Circle circle = (Circle) node;
                if (circle.centerXProperty().get() == cell.getPosition().getX() &&
                        circle.centerYProperty().get() == cell.getPosition().getY()) {
                    return circle;
                }
            }
        }
        return null;
    }
}