package fr.unicaen.iutcaen.model.entities;

import fr.unicaen.iutcaen.model.Point;
import fr.unicaen.iutcaen.model.factories.IdDistributor;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class CellPack extends Cell{

    private ArrayList<Cell> cells;
    public CellPack(int id, Point position, double mass, Color color) {
        super(id, position, mass, color);
        cells = new ArrayList<>();
        cells.add(new Cell(IdDistributor.getInstance().getNextId(), position, mass, Color.ALICEBLUE));
        cells.add(new Cell(IdDistributor.getInstance().getNextId(), new Point(position.getX() + 100, position.getY() + 100), mass - 70, Color.BLACK));
        cells.add(new Cell(IdDistributor.getInstance().getNextId(), new Point(position.getX() + 100, position.getY() + 100), mass +50, Color.RED));
    }

    public void addCell(Cell cell){
        this.cells.add(cell);
    }

    public void removeCell(Cell cell){
        this.cells.remove(cell);
    }

    protected void getCells(ArrayList<Cell> cells){
        for (Cell cell : this.cells) {
            cell.getCell(cells);
        }
    }

    /**
     *
     *
     * @return the list of all the cells without any cellpack
     */
    public ArrayList<Cell> getAllCells(){
        ArrayList<Cell> a = new ArrayList<>();
        getCells(a);
        return a;
    }


    public void move(Point direction){
        for (Cell cell : cells) {
            cell.move(direction);
        }
    }

    public void split(){
        for (Cell cell : getAllCells()){
            cell.setNeighbor(getAllCells());
        }
    }


}
