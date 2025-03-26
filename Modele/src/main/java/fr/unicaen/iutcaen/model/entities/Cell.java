package fr.unicaen.iutcaen.model.entities;

import fr.unicaen.iutcaen.Config;
import fr.unicaen.iutcaen.model.Point;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Cell extends Entity{

    public Cell(int id, Point position, double mass, Color color) {
        super(id, position, mass, color);

    }

    private ArrayList<Cell> neighbor = new ArrayList<>();

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
        //System.out.println(Config.MINSPEED + (Config.BASESPEED / Math.sqrt(mass)));
        return Config.MINSPEED + (Config.BASESPEED / Math.sqrt(mass));

    }
    public boolean canEat(Cell cell){
        return this.getMass() >= (cell.getMass() * 1.33);
    }

    public void move(Point direction){

        Point vecteurD = new Point(direction.getX()-position.getX(), direction.getY() - position.getY());
        if (Math.abs(vecteurD.getX()) > 200){
            double ratio = Math.abs(vecteurD.getX()) / 200;
            vecteurD.setX(vecteurD.getX() / ratio);
            vecteurD.setY(vecteurD.getY() / ratio);
        }
        if (Math.abs(vecteurD.getY()) > 200){
            double ratio = Math.abs(vecteurD.getY()) / 200;
            vecteurD.setX(vecteurD.getX() / ratio);
            vecteurD.setY(vecteurD.getY() / ratio);
        }
        Point newPos = position.add(vecteurD.multiply(getSpeed()/Config.SPEEDCOEF));
        position.setX(newPos.getX());
        position.setY(newPos.getY());
        for (Cell cell : neighbor) {
            if (cell != this) {
                if (colide(cell)) {
                    double gap = position.distance(cell.position);

                    Point vec = new Point(position.getX() - cell.position.getX(), position.getY() - cell.position.getY());

                    double distance = vec.length();
                    if (distance != 0) {
                        vec = vec.multiply(1.0 / distance);

                        double sizeRatio = cell.getSize() / (this.getSize() + cell.getSize());

                        double moveDistance = -(gap - (this.getSize() + cell.getSize()));

                        Point moveThis = vec.multiply(sizeRatio * moveDistance);
                        Point moveCell = vec.multiply((1-sizeRatio) * moveDistance);

                        position.setX(position.getX() + moveThis.getX());
                        position.setY(position.getY() + moveThis.getY());

                        cell.position.setX(cell.position.getX() - moveCell.getX());
                        cell.position.setY(cell.position.getY() - moveCell.getY());
                    }
                }
            }
        }

    }

    public void getCell(ArrayList<Cell> cells){
        cells.add(this);
    }

    public void setNeighbor(ArrayList<Cell> neighbor){
        this.neighbor = neighbor;
    }
    
    public Boolean absorbEntity(Entity entity) {
    	Boolean absorbed = false; 
    	
    	if(this.isInside(entity)) {
    		
	       if(entity instanceof Pellet) {
	    	   Pellet pellet = (Pellet) entity; 
	    	   pellet.applyEffect(this);
	    	   absorbed = true; 
	       }
	       
	    	else if (entity instanceof Cell) {
	    		Cell cell = (Cell) entity; 
	    		if(this.canEat(cell)) {
	    			this.mass += cell.mass;  
	    			absorbed = true; 
	    		}
	    	}
    	}
    	
    	return absorbed; 
    }
}
