package util;

import org.apache.log4j.Logger;

import java.util.*;

/**
 * Created by Sviat on 14.11.14.
 */
public class Calculate {
    final static Logger log = Logger.getLogger(Calculate.class);

    /**
     * Обчислення власного вектора із матриці оцінок
     *
     * @param matrix матриця оцінок
     * @return власний вектор
     */
    public static ArrayList<Double> selfVectorForMatrix(double[][] matrix) {
        ArrayList<Double> selfVector = new ArrayList<>();

        int length = matrix.length;

        double rowMul = 1;
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                rowMul *= matrix[i][j];
            }

            rowMul = (float) Math.pow(rowMul, 1f / length);
            selfVector.add(rowMul);
        }

        double allRowSum = 0;
        for (double row : selfVector) {
            allRowSum += row;
        }

        for (int i = 0; i < selfVector.size(); i++) {
            double calculated = selfVector.get(i) / allRowSum;
            calculated = (Math.round(calculated * 100.0) / 100.0);
            selfVector.set(i, calculated);
        }

        return selfVector;
    }

    /**
     * Обчислення лямбди з вектора власних цілей та матриці оцінок
     *
     * @param matrix матриця цілей
     * @param v      вектор власних цілей з цієї матриці
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
     *
     * @param bestValue  вага (краще значення, загалом має бути два таких для акторів)
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
     *
     * @param targets вектор для нормалізації
     * @return нормалізований вектор такого ж розміру
     */
    public static Map<String, Double> normalizeWeightVectorOfBestTargets(Map<String, Double> targets) {
        log.info("Going to normalize targets");

        double sume = 0;
        for (String target : targets.keySet()) {
            sume += targets.get(target);
        }

        for (String target : targets.keySet()) {
            double newValue = (float) (targets.get(target) * 100.0 / sume);
            targets.put(target, newValue);
            log.debug("calculated new normal value: " + newValue);
        }

        return targets;
    }

    /**
     * Побудова вектора із двох найкращих цілей двох акторів.
     * Також цей вектор нормалізується. Прив’язка значення-цілі зберігається.
     *
     * @param firstTarget  цілі першого актора
     * @param secondTarget цілі другого актора
     * @return збудований вектор розміром 4
     */
    public static Map<String, Double> buildAndNormalizeVectorOfBestTargets(Map<String, Double> firstTarget, Map<String, Double> secondTarget) {
        Map<String, Double> newVector = new HashMap<>();

        for (String targetName : firstTarget.keySet()) {
            newVector.put(targetName, firstTarget.get(targetName));
            if (newVector.size() >= 2) {
                log.debug("added needed count of targets in vector from first");
                break;
            }
        }

        for (String targetName : secondTarget.keySet()) {
            newVector.put(targetName, secondTarget.get(targetName));
            if (newVector.size() >= 4) {
                log.debug("added needed count of targets in vector from second");
                break;
            }
        }

        return normalizeWeightVectorOfBestTargets(newVector);
    }

    public static ArrayList<Double> getResultVector(ArrayList<double[][]> scenarioMatrices, ArrayList<Double> bestVector) {
        double[][] m = buildMatrixFromVectors(scenarioMatrices, bestVector.size());

        ArrayList<Double> resultVector = new ArrayList<>();

        double[] v = new double[bestVector.size()];

        for (int i = 0; i < bestVector.size(); i++) {
            v[i] = bestVector.get(i);
        }

        double[] arrayResult = multiply(m, v);

        for (double anArrayResult : arrayResult) {
            resultVector.add(anArrayResult);
        }

        return resultVector;
    }

    public static double[][] buildMatrixFromVectors(ArrayList<double[][]> scenarioMatrices, int size) {
        double[][] matrix = new double[scenarioMatrices.get(0)[0].length][size];

        int col = 0;
        for (double[][] m : scenarioMatrices) {
            ArrayList<Double> selfVector = selfVectorForMatrix(m);

            for (int i = 0; i < scenarioMatrices.get(0).length; i++) {
                matrix[i][col] = selfVector.get(i);
            }

            col++;
        }

        return matrix;
    }

    // matrix-vector multiplication (y = matrix * vector)
    public static double[] multiply(double[][] matrix, double[] vector) {
        int m = matrix.length;
        int n = matrix[0].length;

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }

            System.out.println();
        }

        System.out.println();

        for (int i = 0; i < vector.length; i++) {
            System.out.println(vector[i]);
        }

        System.out.println();

        if (vector.length != n) throw new RuntimeException("Illegal matrix dimensions.");

        double[] y = new double[m];

        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                y[i] += (matrix[i][j] * vector[j]);


        for (int i = 0; i < y.length; i++) {
            System.out.println(y[i]);
        }

        return y;
    }

    public static ArrayList<Double> normalizeVector(ArrayList<Double> vector) {
        double sum = 0;
        for (double v : vector) {
            sum += v;
        }

        for (int i = 0; i < vector.size(); i++) {
            double newValue = (float) (vector.get(i) * 100.0 / sum);
            vector.set(i, newValue);
        }

        return vector;
    }
}