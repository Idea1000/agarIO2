package fr.unicaen.iutcaen.agario2.model.factories;

public class IdDistributor {

    private int id;
    private static IdDistributor instance = new IdDistributor();

    private IdDistributor(){
        id = 0;
    }
    public static IdDistributor getInstance(){
        return instance;
    }
    public int getNextId(){
        return ++id;
    }
}
