package model;

import java.util.ArrayList;

/**
 * Created by Sviat on 12.11.14.
 */
public class Actor implements IGlobalTargetObject {
    private String name;

    private int size;

    private ArrayList<ActorTarget> targets;

    /**
     * Актор (вчитель, системний аналітик тощо)
     * @param name назва цього актора
     * @param actorCount загальна кількість акторів в мети
     */
    public Actor(String name, int actorCount) {
        this.name = name;
        this.size = actorCount;

        targets = new ArrayList<>();
    }

    public void addTarget(String name) {
        ActorTarget target = new ActorTarget(name, size);
        targets.add(target);
    }

    public ArrayList<ActorTarget> getTargets() {
        return targets;
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