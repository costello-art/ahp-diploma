package model;

import model.math.Calculate;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sviat on 12.11.14.
 */
public class GlobalTarget implements IGlobalTargetObject {
    final static Logger log = Logger.getLogger(GlobalTarget.class);
    private String target;

    private ArrayList<Actor> actorsList;

    private float[][] matrix;

    private Map<String, Float> actorsWeightValues;
    private ArrayList<Actor> bestActors;

    private ArrayList<String> scenarioList;

    private Map<String, Float> bestTargetsForActors;

    /**
     * Глобальна мета, яка містить акторів та їх цілі
     *
     * @param target назва мети
     */
    public GlobalTarget(String target) {
        this.target = target;
        actorsList = new ArrayList<>();
        actorsWeightValues = new HashMap<>();
        scenarioList = new ArrayList<>();
    }

    private void initMatrix() {
        matrix = new float[actorsList.size()][actorsList.size()];
        for (int i = 0; i < actorsList.size(); i++) {
            matrix[i][i] = 1;
        }
    }

    public void setScenarioList(ArrayList<String> scenario) {
        scenarioList = scenario;
    }

    public ArrayList<String> getScenario() {
        return scenarioList;
    }

    public void addActor(String name, ArrayList<String> targets) {
        log.info(String.format("Added actor %s with target count %d", name, targets.size()));

        Actor actor = new Actor(name, targets);
        actorsList.add(actor);
        initMatrix();
    }

    public int getActorCount() {
        return actorsList.size();
    }

    public ArrayList<String> getActorsNames() {
        ArrayList<String> names = new ArrayList<>();

        ArrayList<Actor> actors = getActors();

        for (Actor actor : actors) {
            names.add(actor.getName());
        }

        return names;
    }


    public ArrayList<Actor> getActors() {
        return actorsList;
    }

    @Override
    public String getName() {
        return target;
    }

    @Override
    public void setName(String name) {
        target = name;
    }

    public void setMatrix(float[][] matrix) {
        this.matrix = matrix;

        ArrayList<Float> selfVector = Calculate.selfVectorForMatrix(matrix);

        log.debug("selfVector for GlobalTarget has been calculated");

        for (int i = 0; i < selfVector.size(); i++) {
            actorsWeightValues.put(actorsList.get(i).getName(), selfVector.get(i));
            log.debug(String.format("Actor: %s, weight: %s", actorsList.get(i).getName(), selfVector.get(i)));
        }

        actorsWeightValues = MapSort.sortByComparator(actorsWeightValues, false);
        MapSort.printMap(actorsWeightValues);

        bestActors = new ArrayList<>();

        log.debug("actor weight map has been sorted");
        for (String key : actorsWeightValues.keySet()) {
            for (Actor actor : actorsList) {
                if (actor.getName().equals(key)) {
                    bestActors.add(actor);
                    log.debug("added actor " + key);
                    if (bestActors.size() >= 2) {
                        log.debug("added needed amount of actors");
                        return;
                    }
                }
            }
        }
    }

    public float[][] getMatrix() {
        return matrix;
    }

    public ArrayList<Actor> getBestActors() {
        return bestActors;
    }

    public void setBestTargetsForActors(Map<String, Float> bestTargets) {
        bestTargetsForActors = bestTargets;
    }

    public Map<String, Float> getBestTargetsForActors() {
        return bestTargetsForActors;
    }
}