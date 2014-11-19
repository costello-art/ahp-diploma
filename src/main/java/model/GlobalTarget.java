package model;

import org.apache.log4j.Logger;
import util.MapSort;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sviat on 12.11.14.
 */
public class GlobalTarget extends Target {
    final static Logger log = Logger.getLogger(GlobalTarget.class);

    private ArrayList<Actor> actorsList;
    private Map<String, Double> actorsWeight;
    private Map<String, Double> bestTargetsForActors;
    private ArrayList<Scenario> scenarioList;

    /**
     * Глобальна мета, яка містить акторів та їх цілі
     *
     * @param target назва мети
     */
    public GlobalTarget(String target) {
        this.name = target;
        actorsList = new ArrayList<>();
        actorsWeight = new HashMap<>();
    }

    private void initMatrix() {
        matrix = new double[actorsList.size()][actorsList.size()];
        for (int i = 0; i < actorsList.size(); i++) {
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

    public void addActor(String name, ArrayList<String> targets) {
        log.info(String.format("Added actor %s with target count %d", name, targets.size()));

        actorsList.add(new Actor(name, targets));
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


    public ArrayList<Actor> getActorsList() {
        return actorsList;
    }

    @Override
    public void setMatrix(double[][] matrix) {
        this.matrix = matrix;
        initSelfVector();

        for (int i = 0; i < selfVector.size(); i++) {
            actorsWeight.put(actorsList.get(i).getName(), selfVector.get(i));
            log.debug(String.format("Actor: %s, weight: %s", actorsList.get(i).getName(), selfVector.get(i)));
        }

        actorsWeight = MapSort.sortByComparator(actorsWeight, false);
    }

    public void setBestTargetsForActors(Map<String, Double> bestTargets) {
        bestTargetsForActors = bestTargets;
    }

    public void printResult(ArrayList<Float> finalVector) {
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
}