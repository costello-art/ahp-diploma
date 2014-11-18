package model;

import model.math.Calculate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sviat on 12.11.14.
 */
public class GlobalTarget implements IGlobalTargetObject {
    private String target;

    private ArrayList<Actor> actorsList;

    private float[][] matrix;

    private Map<String, Float> actorsWeightValues;
    private ArrayList<Actor> bestActors;

    /**
     * Глобальна мета, яка містить акторів та їх цілі
     *
     * @param target     назва мети
     */
    public GlobalTarget(String target) {
        this.target = target;
        actorsList = new ArrayList<>();
        actorsWeightValues = new HashMap<>();
    }

    private void initMatrix() {
        matrix = new float[actorsList.size()][actorsList.size()];
        for (int i = 0; i < actorsList.size(); i++) {
            matrix[i][i] = 1;
        }
    }

    public void addActor(String name, ArrayList<String> targets) {
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

        for (int i = 0; i < selfVector.size(); i++) {
            actorsWeightValues.put(actorsList.get(i).getName(), selfVector.get(i));
        }

        actorsWeightValues = MapSort.sortByComparator(actorsWeightValues, false);
        MapSort.printMap(actorsWeightValues);

        bestActors = new ArrayList<>();

        for (String key : actorsWeightValues.keySet()) {
            for (Actor actor : actorsList) {
                if (actor.getName().equals(key)) {
                    bestActors.add(actor);
                    if (bestActors.size() > 2) return;
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
}