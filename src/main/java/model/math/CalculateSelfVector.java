package model.math;

import java.util.ArrayList;

/**
 * Created by Sviat on 14.11.14.
 */
public class CalculateSelfVector {
    public static ArrayList<Float> forMatrix(float[][] matrix) {
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

    public static float getLambda(float[][] matrix, ArrayList<Float> v) {

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
}
