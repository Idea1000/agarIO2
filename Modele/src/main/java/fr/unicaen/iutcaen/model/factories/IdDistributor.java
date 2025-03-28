package fr.unicaen.iutcaen.model.factories;

/**
 * The IdDistributor is a Singleton<br>
 * It gives Ids when {@link #getNextId()}  is used
 */
public class IdDistributor {

    /**
     * the last Id given
     */
    private int id;

    /**
     * the instance of the IdDistributor
     */
    private static IdDistributor instance = new IdDistributor();

    /**
     * the private Constructor as the Singleton Patron does
     */
    private IdDistributor(){
        id = 0;
    }

    /**
     * gives the instance of the IdDistributor as the Singleton Patron does
     * @return the instance of the IdDistributor
     */
    public static IdDistributor getInstance(){
        return instance;
    }

    /**
     * gives a unique id
     * @return a unique Id
     */
    public int getNextId(){
        return ++id;
    }
}
