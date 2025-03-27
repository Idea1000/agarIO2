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
import javafx.scene.shape.StrokeType;

public class PlayerView extends AbstractView{
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

        for (Cell cell : cells) {
            addCellLogic(cell, root);
        }
    }

    private void addCellLogic(Cell cell, Pane root) {
        Circle c = new Circle();

        c.centerXProperty().bind(cell.getPosition().xProperty());
        c.centerYProperty().bind(cell.getPosition().yProperty());
        c.setStrokeType(StrokeType.INSIDE);
        c.setStroke(cell.getColor().darker());

        c.setStrokeWidth(3);

        cell.getMassProperty().addListener((observableValue, number, t1) -> {
            cell.setMass(t1.doubleValue());
            c.setRadius(cell.getSize());

        });

        c.setRadius(cell.getSize());
        c.setFill(cell.getColor());

        root.getChildren().add(c);
    }

    private void removeCellLogic(Cell cell, Pane root) {

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

    @Override
    public void delete(Pane root) {

    }
}