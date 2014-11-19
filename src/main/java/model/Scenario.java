package model;

import model.math.Calculate;
import org.apache.log4j.Logger;

import java.util.ArrayList;

/**
 * Created by Sviat on 19.11.14.
 */
public class Scenario {
    final static Logger log = Logger.getLogger(Scenario.class);
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
        log.debug("vector size: " + selfVector.size());
        return selfVector;
    }

    public String getName() {
        return name;
    }
}
