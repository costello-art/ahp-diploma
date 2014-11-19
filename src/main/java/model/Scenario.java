package model;

/**
 * Created by Sviat on 19.11.14.
 */
public class Scenario extends Target {

    public Scenario(String name) {
        this.name = name;
    }

    @Override
    void setMatrix(double[][] matrix) {
        this.matrix = matrix;
        initSelfVector();
    }
}