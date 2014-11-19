package util;

import model.Scenario;
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
    @Deprecated
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
     * @param firstTarget цілі першого актора
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

    public static ArrayList<Double> getResultVector(ArrayList<Scenario> scenarioList, ArrayList<Double> bestVector) {
        ArrayList<Double> resultVector = new ArrayList<>();

        double[][] matrix = new double[scenarioList.size()][scenarioList.size()];

        log.debug("scenario size: " + scenarioList.size());

        for (int i = 0; i < scenarioList.size(); i++) {
            for (int j = 0; j < scenarioList.size(); j++) {
                matrix[j][i] = scenarioList.get(i).getSelfVector().get(j);
            }
        }

        double[] v = new double[bestVector.size()];

        for (int i = 0; i < bestVector.size(); i++) {
            v[i] = bestVector.get(i);
        }

        double[] arrayResult = multiply(matrix, v);

        for (double anArrayResult : arrayResult) {
            resultVector.add(anArrayResult);
        }

        return resultVector;
    }

    // matrix-vector multiplication (y = matrix * vector)
    public static double[] multiply(double[][] matrix, double[] vector) {
        int m = matrix.length;
        int n = matrix[0].length;

        if (vector.length != n) throw new RuntimeException("Illegal matrix dimensions.");

        double[] y = new double[m];

        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                y[i] += (matrix[i][j] * vector[j]);

        return y;
    }
}