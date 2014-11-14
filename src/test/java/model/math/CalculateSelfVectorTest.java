package model.math;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class CalculateSelfVectorTest {

    float[][] matrix = {
            {1, 3, 0.2f, 6},
            {0.3f, 1, 2, 7},
            {5, 0.5f, 1, 5},
            {0.16f, 0.14f, 0.2f, 1}};

    private ArrayList<Float> vExpected;

    @Before
    public void setUp() throws Exception {
        vExpected = new ArrayList<>();
        vExpected.add(0.26f);
        vExpected.add(0.29f);
        vExpected.add(0.39f);
        vExpected.add(0.06f);
    }

    @Test
    public void testForMatrix() throws Exception {
        assertEquals(vExpected, CalculateSelfVector.forMatrix(matrix));
    }

    @Test
    public void testGetLamda() throws Exception {
        float expected = 5.51f;
        assertEquals(expected, CalculateSelfVector.getLambda(matrix, vExpected), 0.1f);
    }
}