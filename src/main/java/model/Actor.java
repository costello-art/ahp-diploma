package model;

import model.math.Calculate;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sviat on 12.11.14.
 */
public class Actor implements IGlobalTargetObject {
    final static Logger log = Logger.getLogger(Actor.class);
    private String actorName;

    private ArrayList<String> targetList;
    private Map<String, Float> actorsTargetsWeightValues;

    private ArrayList<Float> selfVector;
    private ArrayList<String> bestTargets;

    /**
     * Матриця оцінок цілей актора
     */
    private float[][] matrix;

    /**
     * Актор (вчитель, системний аналітик тощо)
     * @param name назва цього актора
     * @param targets
     */
    public Actor(String name, ArrayList<String> targets) {
        this.actorName = name;
        targetList = targets;
        matrix = new float[targets.size()][targets.size()];
    }

    public void addTarget(String name) {
        targetList.add(name);
    }

    public ArrayList<String> getTargets() {
        return targetList;
    }

    @Override
    public String getName() {
        return actorName;
    }

    @Override
    public void setName(String name) {
        this.actorName = name;
    }

    public void setMatrix(float[][] matrix) {
        this.matrix = matrix;

        selfVector = Calculate.selfVectorForMatrix(matrix);

        log.debug(String.format("Matrix for %s has been set.", getName()));

        actorsTargetsWeightValues = new HashMap<>();
        for (int i = 0; i < selfVector.size(); i++) {
            actorsTargetsWeightValues.put(targetList.get(i), selfVector.get(i));
            log.debug(String.format("Target: %s, weight: %s", targetList.get(i), selfVector.get(i)));
        }

        actorsTargetsWeightValues = MapSort.sortByComparator(actorsTargetsWeightValues, false);
        MapSort.printMap(actorsTargetsWeightValues);

       /* bestTargets = new ArrayList<>();

        log.debug("actor weight map has been sorted");
        for (String key : actorsTargetsWeightValues.keySet()) {
            for (String target : targetList) {
                if (target.equals(key)) {
                    bestTargets.add(target);
                    log.debug("added actor " + key);
                    if (bestTargets.size() >= 2) {
                        log.debug("added needed amount of targets");
                        return;
                    }
                }
            }
        }*/
    }

    public float[][] getMatrix() {
        return matrix;
    }

    public Map<String, Float> getBestActorsWeightValues() {
        return actorsTargetsWeightValues;
    }
}