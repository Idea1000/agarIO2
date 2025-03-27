package fr.unicaen.iutcaen.model.entities;

import fr.unicaen.iutcaen.config.Config;
import fr.unicaen.iutcaen.model.Point;
import fr.unicaen.iutcaen.model.factories.IdDistributor;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.ArrayList;

public class Cell extends Entity{

    public Cell(int id, Point position, double mass, Color color) {
        super(id, position, mass, color);
        unSplitTimer.setCycleCount((int) mass/3 + 150);
        unSplitTimer.setOnFinished(actionEvent -> {
            this.unSplit = true;
        });

    }

    private int timeAlive = 0;
    public void setUnsplit(boolean a){
        unSplit = a;
    }

    private double burstSpeed = 1;

    public void setBurstSpeed(double speedFactor) {
        this.burstSpeed = speedFactor;
    }

    private boolean unSplit = false;

    private ObservableList<Cell> neighbor = FXCollections.observableArrayList();
    private Timeline unSplitTimer = new Timeline(new KeyFrame(Duration.millis(33), actionEvent -> {
        timeAlive++;
    }));

    public void setUnSplit(boolean unSplit){
        this.unSplit = unSplit;
    }
    public boolean getUnsplit(){
        return unSplit;
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
        return Config.MIN_SPEED + (Config.BASE_SPEED / Math.sqrt(this.getMass()));
    }
    public boolean canEat(Cell cell){
        return this.getMass() >= (cell.getMass() * 1.33);
    }

    public void move(Point direction) {
        Point vecteurD = calculateDirectionVector(direction);
        vecteurD = scaleVectorIfNecessary(vecteurD);

        movePosition(vecteurD);
        handleCollisionsWithNeighbors();
        //handleChildCellRecomposition();
    }

    private Point calculateDirectionVector(Point direction) {
        return new Point(direction.getX() - position.getX(), direction.getY() - position.getY());
    }

    private Point scaleVectorIfNecessary(Point vecteurD) {
        if (Math.abs(vecteurD.getX()) > 200) {
            double ratio = Math.abs(vecteurD.getX()) / 200;
            vecteurD.setX(vecteurD.getX() / ratio);
            vecteurD.setY(vecteurD.getY() / ratio);
        }
        if (Math.abs(vecteurD.getY()) > 200) {
            double ratio = Math.abs(vecteurD.getY()) / 200;
            vecteurD.setX(vecteurD.getX() / ratio);
            vecteurD.setY(vecteurD.getY() / ratio);
        }
        return vecteurD;
    }

    private void movePosition(Point vecteurD) {
        Point newPos = position.add(vecteurD.multiply((getSpeed()  * burstSpeed)/ Config.SPEED_COEF));
        burstSpeed *= 0.92;
        if (burstSpeed < 1.01){
            burstSpeed = 1;
        }

        position.setX(newPos.getX());
        position.setY(newPos.getY());
    }

    private void handleCollisionsWithNeighbors() {
        if (timeAlive > getSize() * 2 / (Config.BURST_SPEED) + 5){

            for (int i = 0; i < neighbor.size(); i++) {
                Cell cell = neighbor.get(i);
                if (cell.unSplit && cell != this) {
                    if (this.isInside(cell)){
                        reCompose(cell);
                    }
                }else {
                    if (cell != this && colide(cell)) {
                        resolveCollision(cell);
                    }
                }
            }
        }
    }

    private void resolveCollision(Cell cell) {
        double gap = position.distance(cell.position);
        Point vec = new Point(position.getX() - cell.position.getX(), position.getY() - cell.position.getY());

        double distance = vec.length();
        if (distance != 0) {
            vec = vec.multiply(1.0 / distance);

            double sizeRatio = cell.getSize() / (this.getSize() + cell.getSize());
            double moveDistance = -(gap - (this.getSize() + cell.getSize()));

            Point moveThis = vec.multiply(sizeRatio * moveDistance);
            Point moveCell = vec.multiply((1 - sizeRatio) * moveDistance);

            position.setX(position.getX() + moveThis.getX());
            position.setY(position.getY() + moveThis.getY());

            cell.position.setX(cell.position.getX() - moveCell.getX());
            cell.position.setY(cell.position.getY() - moveCell.getY());
        }
    }



    public void getCell(ArrayList<Cell> cells){
        cells.add(this);
    }

    public void setNeighbor(ObservableList<Cell> neighbor){
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
	    			this.mass.set(cell.mass.getValue()+mass.getValue());
	    			absorbed = true; 
	    		}
	    	}
    	}
    	
    	return absorbed; 
    }



    public void reCompose(Cell cell){
        neighbor.remove(cell);
        this.setMass(this.getMass() + cell.getMass());
    }
    public void unSplit() {
        unSplitTimer.play();
    }

    public Cell split(){
        if (neighbor.size() <= 1){
            timeAlive = 0;
            this.unSplit = false;
            this.unSplit();
        }
        if (this.getMass() > 50){
            this.setMass(this.getMass()/2);
            Cell newCell = new Cell(IdDistributor.getInstance().getNextId(), new Point(position.getX(), position.getY()), this.getMass(), this.getColor());
            newCell.unSplit();
            newCell.setBurstSpeed(Config.BURST_SPEED);
            return newCell;
        }
        return null;
    }

    public String toString() {
        return String.format("ID : %d, unsplit : %b", id, unSplit);
    }

    public SimpleDoubleProperty getSizeProperty(){
        return new SimpleDoubleProperty(Config.SIZE_RATIO_CELL *Math.sqrt(this.getMass()));
    }


    public double getSize(){
        return Config.SIZE_RATIO_CELL *Math.sqrt(this.getMass());
    }

    public double getDiameter(){
        return getSize()*2;
    }


}
