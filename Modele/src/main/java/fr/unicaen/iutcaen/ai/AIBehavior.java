package fr.unicaen.iutcaen.ai;

/**
 * Define an AI behavior
 * @author courtoi223, Idea1000
 */
public interface AIBehavior {

    /**
     * Move the AI
     * @param ia
     */
    void move(IA ia);

    /**
     * Update the AI target
     * @param ia
     */
    void update(IA ia);

}

