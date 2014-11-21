package model;

import org.apache.log4j.Logger;
import util.MapSort;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sviat on 12.11.14.
 */
public class GlobalTarget extends Target implements Serializable {
    final static Logger log = Logger.getLogger(GlobalTarget.class);

    private ArrayList<Actor> unsortedActorList;

    private ArrayList<Actor> sortedByWeightActorList;

    private Map<String, Double> bestTargetsForActors;

    private ArrayList<double[][]> scenarioMatrixForBestTarget;
    private ArrayList<Scenario> scenarioList;
    private ArrayList<Double> finalVector;

    /**
     * Глобальна мета, яка містить акторів та їх цілі
     *
     * @param target назва мети
     */
    public GlobalTarget(String target) {
        this.name = target;
        unsortedActorList = new ArrayList<>();
        sortedByWeightActorList = new ArrayList<>();
        scenarioList = new ArrayList<>();
        scenarioMatrixForBestTarget = new ArrayList<>();
    }

    private void initMatrix() {
        matrix = new double[unsortedActorList.size()][unsortedActorList.size()];
        for (int i = 0; i < unsortedActorList.size(); i++) {
            matrix[i][i] = 1;
        }
    }

    public void setScenarioList(ArrayList<String> scenario) {
        for (String sName : scenario) {
            scenarioList.add(new Scenario(sName));
        }
    }

    public ArrayList<Scenario> getScenarioList() {
        return scenarioList;
    }

    public ArrayList<String> getScenarioListNames() {
        ArrayList<String> names = new ArrayList<>();

        for (Scenario sc : scenarioList) {
            names.add(sc.getName());
        }

        return names;
    }

    public void addActor(String name, ArrayList<String> targets) {
        log.info(String.format("Added actor %s with target count %d", name, targets.size()));

        unsortedActorList.add(new Actor(name, targets));
        initMatrix();
    }

    public ArrayList<String> getActorsNames() {
        ArrayList<String> names = new ArrayList<>();

        ArrayList<Actor> actors = getActorsList();

        for (Actor actor : actors) {
            names.add(actor.getName());
        }

        return names;
    }

    public void addScenarioMatrix(double[][] matrix) {
        scenarioMatrixForBestTarget.add(matrix);
    }

    public ArrayList<double[][]> getScenarioMatrixForBestTarget() {
        return scenarioMatrixForBestTarget;
    }

    public ArrayList<Actor> getActorsList() {
        return unsortedActorList;
    }

    @Override
    public void setMatrix(double[][] matrix) {
        this.matrix = matrix;
        initSelfVector();

        Map<Actor, Double> actorsWeight = new HashMap<>();

        for (int i = 0; i < selfVector.size(); i++) {
            Actor a = unsortedActorList.get(i);
            a.setWeight(selfVector.get(i));
            actorsWeight.put(a, selfVector.get(i));
            log.debug(String.format("Actor: %s, weight: %s", unsortedActorList.get(i).getName(), selfVector.get(i)));
        }

        actorsWeight = MapSort.sortActorByComparator(actorsWeight, false);

        for (Actor actor : actorsWeight.keySet()) {
            sortedByWeightActorList.add(actor);
        }
    }

    public void setBestTargetsForActors(Map<String, Double> bestTargets) {
        bestTargetsForActors = bestTargets;
    }

    public void printResult(ArrayList<Double> finalVector) {
        for (int i = 0; i < scenarioList.size(); i++) {
            log.debug(String.format("Result for scenario %s: %f", scenarioList.get(i).getName(), finalVector.get(i)));
        }
    }

    public ArrayList<Double> getBestTargetsAsArray() {
        ArrayList<Double> actorsWeightAsArray = new ArrayList<>();

        for (String key : bestTargetsForActors.keySet()) {
            actorsWeightAsArray.add(bestTargetsForActors.get(key));
        }

        return actorsWeightAsArray;
    }

    public Map<String, Double> getBestTargetsForActors() {
        return bestTargetsForActors;
    }

    public void setFinalVector(ArrayList<Double> finalVector) {
        this.finalVector = finalVector;
    }

    public ArrayList<Double> getFinalVector() {
        return finalVector;
    }
}