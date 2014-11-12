package model;

/**
 * Created by Sviat on 12.11.14.
 */
public class ActorTarget implements IGlobalTargetObject{

    /**
     * Кількість цілей для актора
     */
    private final int targetCount;

    /**
     * Назва цілі
     */
    private String name;

    /**
     * Ціль актора
     * @param name назва цілі
     * @param targetCount кількість цілей для поточного актора
     */
    public ActorTarget(String name, int targetCount) {
        this.name = name;
        this.targetCount = targetCount;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
}