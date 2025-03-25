package fr.unicaen.iutcaen.agario2.model;

import javafx.beans.property.SimpleDoubleProperty;

public class Point {
    private SimpleDoubleProperty X;
    private SimpleDoubleProperty Y;

    public Point(Double x, Double y) {
        X = new SimpleDoubleProperty(x);
        Y = new SimpleDoubleProperty(y);
    }

    public double getX() {
        return X.get();
    }

    public SimpleDoubleProperty xProperty() {
        return X;
    }

    public void setX(double x) {
        this.X.set(x);
    }

    public double getY() {
        return Y.get();
    }

    public SimpleDoubleProperty yProperty() {
        return Y;
    }

    public void setY(double y) {
        this.Y.set(y);
    }

    public double distance(Point p){
        return Math.sqrt( Math.pow(p.getX() - this.getX(), 2) + Math.pow(p.getY() - this.getY(), 2));
    }

    public Point add(Point p){
        return new Point(this.getX() + p.getX(), this.getY() + p.getY());
    }

    public Point multiply(Double d){
        return new Point(this.getX()*d, this.getY()*d);
    }
}
