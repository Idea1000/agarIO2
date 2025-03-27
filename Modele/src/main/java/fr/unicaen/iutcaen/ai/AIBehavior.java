package fr.unicaen.iutcaen.ai;

/**
 * Define an AI behavior
 * @author courtoi223, Idea1000
 */
public interface AIBehavior {

    /**
     * Move the AI
     * @param AI
     */
    void move(AI AI);

    /**
     * Update the AI target
     * @param AI
     */
    void update(AI AI);

}

