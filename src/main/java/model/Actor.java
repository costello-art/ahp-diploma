package model;

import model.math.Calculate;

import java.util.ArrayList;

/**
 * Created by Sviat on 12.11.14.
 */
public class Actor implements IGlobalTargetObject {
    private String actorName;

    private ArrayList<String> targetNames;

    private int targetCount;
    private ArrayList<Float> selfVector;

    /**
     * Матриця оцінок цілей актора
     */
    private float[][] matrix;

    /**
     * Актор (вчитель, системний аналітик тощо)
     * @param name назва цього актора
     * @param targetCount кількість цілей в цього актора
     * @param targets
     */
    public Actor(String name, int targetCount, ArrayList<String> targets) {
        this.actorName = name;
        this.targetCount = targetCount;
        targetNames = targets;
        matrix = new float[targetCount][targetCount];
    }

    public void addTarget(String name) {
        targetNames.add(name);
    }

    public ArrayList<String> getTargets() {
        return targetNames;
    }

    @Override
    public String getName() {
        return actorName;
    }

    @Override
    public void setName(String name) {
        this.actorName = name;
    }

    public void setMatrix(float[][] matrix) {
        this.matrix = matrix;

        selfVector = Calculate.selfVectorForMatrix(matrix);
    }

    public float[][] getMatrix() {
        return matrix;
    }
}