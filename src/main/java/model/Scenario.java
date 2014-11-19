package model;

import model.math.Calculate;

import java.util.ArrayList;

/**
 * Created by Sviat on 19.11.14.
 */
public class Scenario {
    private String name;

    private float[][] matrix;
    private ArrayList<Float> selfVector;


    public Scenario(String name) {
        this.name = name;
    }

    public void setMatrix(float[][] matrix) {
        this.matrix = matrix;
        selfVector = Calculate.selfVectorForMatrix(this.matrix);
    }

    public ArrayList<Float> getSelfVector() {
        return selfVector;
    }

    public String getName() {
        return name;
    }
}
