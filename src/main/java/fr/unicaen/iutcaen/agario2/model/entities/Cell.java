package fr.unicaen.iutcaen.agario2.model.entities;

import fr.unicaen.iutcaen.agario2.Config;
import fr.unicaen.iutcaen.agario2.model.Point;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Cell extends Entity{

    public Cell(int id, Point position, double mass, Color color) {
        super(id, position, mass, color);

    }

    @Override
    public void update() {

    }
    public boolean colide(Entity e){
        return this.position.distance(e.position) < (this.getSize() + e.getSize());
    }
    public boolean isInside(Entity e){
        return this.position.distance(e.position) < this.getSize();
    }

    public double getSpeed(){
        return Config.MINSPEED + (Config.BASESPEED / Math.sqrt(mass));
    }
    public boolean canEat(Cell cell){
        return this.getMass() >= (cell.getMass() * 1.33);
    }

    public void move(Point direction){

        Point vecteurD = new Point(direction.getX()-position.getX(), direction.getY() - position.getY());
        Point newPos = position.add(vecteurD.multiply(getSpeed()/Config.SPEEDCOEF));
        position.setX(newPos.getX());
        position.setY(newPos.getY());

    }

    public void getCell(ArrayList<Cell> cells){
        cells.add(this);
    }
}
