package fr.unicaen.iutcaen.model.entities;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class CellPack extends Cell{

    private ArrayList<Cell> cells;
    public CellPack(int id, Point2D position, double mass, Color color) {
        super(id, position, mass, color);
    }

    public void addCell(Cell cell){
        this.cells.add(cell);
    }

    public void removeCell(Cell cell){
        this.cells.remove(cell);
    }

    public ArrayList<Cell> getCells(){
        return this.cells;
    }
}
