package fr.unicaen.iutcaen.model.entities;

import fr.unicaen.iutcaen.model.Point;
import fr.unicaen.iutcaen.model.factories.IdDistributor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class CellPack extends Cell{

    private ArrayList<Cell> cells;
    private ObservableList<Cell> allCells = FXCollections.observableArrayList();
    public CellPack(int id, Point position, double mass, Color color) {
        super(id, position, mass, color);
        cells = new ArrayList<>();
        Cell cell = new Cell(IdDistributor.getInstance().getNextId(), position, mass+400, Color.RED);
        cell.setUnsplit(true);
        allCells.add(cell);
//        allCells.add(new Cell(IdDistributor.getInstance().getNextId(), position, mass, Color.ALICEBLUE));
//        allCells.add(new Cell(IdDistributor.getInstance().getNextId(), new Point(position.getX() + 100, position.getY() + 100), mass - 70, Color.BLACK));

//        allCells.add(new Cell(IdDistributor.getInstance().getNextId(), new Point(position.getX() + 100, position.getY() + 100), mass +50, Color.RED));
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
    public ObservableList<Cell> getAllCells(){
        return allCells;
    }


    public void move(Point direction){
        for (int i = 0; i < allCells.size(); i++) {
            allCells.get(i).move(direction);
        }
    }

    public void splitCells(){
        System.out.println("size:"+allCells.size());
        System.out.println("aa");
        int t = allCells.size();
        System.out.println(t);
        for (int i = 0; i < t; i++) {
                System.out.println("aah");
                Cell splittedCell = allCells.get(i).split();
                if (splittedCell != null){
                    allCells.add(splittedCell);
                }
                setNeighbor();
        }
        System.out.println("end");
    }

    private void setNeighbor(){
        for (Cell cell : allCells) {
            cell.setNeighbor(allCells);
        }
    }



}
