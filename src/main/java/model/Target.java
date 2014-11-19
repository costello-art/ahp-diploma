package model;

import org.apache.log4j.Logger;

import java.util.ArrayList;

/**
 * Created by Sviat on 19.11.14.
 */
abstract class Target {
    private final static Logger log = Logger.getLogger(Target.class);

    protected String name;

    /**
     * Matrix with targets
     */
    protected double[][] matrix;

    protected ArrayList<Double> selfVector;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /**
     * Set matrix, which was entered by user
     * @param matrix square matrix
     */
    public abstract void setMatrix(double[][] matrix);

    public double[][] getMatrix() {
        return matrix;
    }

    /**
     * Обчислення власного вектора із матриці оцінок
     *
     * @return власний вектор
     */
    protected void initSelfVector() {
        selfVector = new ArrayList<>();

        int length = matrix.length;

        double rowMul = 1;
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                rowMul *= matrix[i][j];
            }

            rowMul = Math.pow(rowMul, 1f / length);
            selfVector.add(rowMul);
        }

        float allRowSum = 0;
        for (double row : selfVector) {
            allRowSum += row;
        }

        for (int i = 0; i < selfVector.size(); i++) {
            double calculated = selfVector.get(i) / allRowSum;
            calculated = (float) (Math.round(calculated * 100.0) / 100.0);
            selfVector.set(i, calculated);
        }

        log.debug(String.format("Self vector for \"%s\" has been initialized, size: %d", name, selfVector.size()));
        for (Double value : selfVector) {
            log.debug("value: " + value);
        }

    }

    public ArrayList<Double> getSelfVector() {
        return selfVector;
    }
}