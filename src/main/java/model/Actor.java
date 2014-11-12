package model;

import java.util.ArrayList;

/**
 * Created by Sviat on 12.11.14.
 */
public class Actor implements IGlobalTargetObject {
    private String actorName;

    private ArrayList<String> targetNames;

    private int targetCount;

    /**
     * Матриця оцінок цілей актора
     */
    private float[][] matrix;

    /**
     * Актор (вчитель, системний аналітик тощо)
     * @param name назва цього актора
     * @param targetCount загальна кількість акторів в мети
     */
    public Actor(String name, int targetCount) {
        this.actorName = name;
        this.targetCount = targetCount;

    }

    public void addTarget(String name) {
        targetNames.add(name);
    }

    public ArrayList<ActorTarget> getTargets() {
        return null;
    }

    @Override
    public String getName() {
        return actorName;
    }

    @Override
    public void setName(String name) {
        this.actorName = name;
    }
}