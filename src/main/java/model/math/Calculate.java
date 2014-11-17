package model.math;

import java.util.ArrayList;

/**
 * Created by Sviat on 14.11.14.
 */
public class Calculate {
    /**
     * Обчислення власного вектора із матриці оцінок
     * @param matrix матриця оцінок
     * @return власний вектор
     */
    public static ArrayList<Float> selfVectorForMatrix(float[][] matrix) {
        ArrayList<Float> selfVector = new ArrayList<>();

        int length = matrix.length;

        float rowMul = 1;
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                rowMul *= matrix[i][j];
            }

            rowMul = (float) Math.pow(rowMul, 1f / length);
            selfVector.add(rowMul);
        }

        float allRowSum = 0;
        for (float row : selfVector) {
            allRowSum += row;
        }

        for (int i = 0; i < selfVector.size(); i++) {
            float calculated = selfVector.get(i) / allRowSum;
            calculated = (float) (Math.round(calculated * 100.0) / 100.0);
            selfVector.set(i, calculated);
        }

        return selfVector;
    }

    /**
     * Обчислення лямбди з вектора власних цілей та матриці оцінок
     * @param matrix матриця цілей
     * @param v вектор власних цілей з цієї матриці
     * @return ?
     */
    public static float lambda(float[][] matrix, ArrayList<Float> v) {

        ArrayList<Float> colSumVect = new ArrayList<>();
        int length = matrix.length;

        float colSum = 0.0f;
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                colSum += matrix[j][i];
            }

            colSumVect.add(colSum);
            colSum = 0;
        }

        for (int i = 0; i < length; i++) {
            float calulatedColSum = v.get(i) * colSumVect.get(i);
            colSumVect.set(i, calulatedColSum);
        }

        float result = 0;
        for (float col : colSumVect) {
            result += col;
        }

        return result;
    }

    /**
     * Перемноження власного вектора цілей на відповідну вагу актора (вага це краще значення, м
     * @param bestValue вага (краще значення, загалом має бути два таких для акторів)
     * @param selfVector вектор власних значень
     * @return ?
     */
    public static ArrayList<Float> bestValueOnSelfVector(float bestValue, ArrayList<Float> selfVector) {
        for (int i = 0; i < selfVector.size(); i++) {
            float newValue = selfVector.get(i) * bestValue;
            selfVector.set(i, newValue);
        }

        return selfVector;
    }

    /**
     * Нормалізація вектора ваг кращих цілей
     * @param vector вектор для нормалізації
     * @return нормалізований вектор такого ж розміру
     */
    public static ArrayList<Float> normalizeWeightVectorOfBestTargets(ArrayList<Float> vector){
        float sume = 0;
        for (Float v : vector) {
            sume += v;
        }

        for (int i = 0; i < vector.size(); i++) {
            float newValue = (float) (vector.get(i) * 100.0 / sume);
            vector.set(i, newValue);
        }

        return vector;
    }
}
