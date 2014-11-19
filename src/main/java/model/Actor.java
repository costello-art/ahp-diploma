package model;

import org.apache.log4j.Logger;
import util.MapSort;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sviat on 12.11.14.
 */
public class Actor extends Target {
    final static Logger log = Logger.getLogger(Actor.class);

    private ArrayList<String> targetList;
    private Map<String, Double> targetWeight;

    private double weight;

    /**
     * Актор (вчитель, системний аналітик тощо)
     * @param name назва цього актора
     * @param targets
     */
    public Actor(String name, ArrayList<String> targets) {
        this.name = name;
        targetList = targets;
    }

    public ArrayList<String> getTargets() {
        return targetList;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public void setMatrix(double[][] matrix) {
        this.matrix = matrix;
        initSelfVector();

        targetWeight = new HashMap<>();
        for (int i = 0; i < selfVector.size(); i++) {
            targetWeight.put(targetList.get(i), selfVector.get(i));
            log.debug(String.format("Target: %s, weight: %s", targetList.get(i), selfVector.get(i)));
        }

        targetWeight = MapSort.sortByComparator(targetWeight, false);
    }

    public Map<String, Double> getBestActorsWeightValues() {
        return targetWeight;
    }
}