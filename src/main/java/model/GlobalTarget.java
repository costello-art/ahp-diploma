package model;

import model.math.Calculate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created by Sviat on 12.11.14.
 */
public class GlobalTarget implements IGlobalTargetObject {
    private String target;
    private int actorCount;

    private ArrayList<Actor> actorsList;

    private float[][] matrix;

    private HashMap<String, Float> actorsWeightValues;

    private int firstBestActor = -1;
    private int secondBestActor = -1;

    /**
     * Глобальна мета, яка містить акторів та їх цілі
     * @param target назва мети
     * @param actorCount кількість акторів
     */
    public GlobalTarget(String target, int actorCount) {
        this.target = target;
        this.actorCount = actorCount;
        matrix = new float[actorCount][actorCount];
        actorsList = new ArrayList<>();

        actorsWeightValues = new HashMap<>();

        initMatrix();
    }

    private void initMatrix() {
        for (int i = 0; i < actorCount; i++) {
            matrix[i][i] = 1;
        }
    }

    public int getActorCount() {
        return actorCount;
    }

    public void addActor(String name, ArrayList<String> targets) {
        Actor actor = new Actor(name, actorCount, targets);
        actorsList.add(actor);
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

        ArrayList<Float> selfVectorCopy = new ArrayList<>();

        selfVectorCopy.addAll(selfVector);

        Collections.sort(selfVectorCopy);
        float first = selfVectorCopy.get(0);
        float second = selfVectorCopy.get(1);

        firstBestActor = selfVector.indexOf(first);
        secondBestActor = selfVector.indexOf(second);
    }

    public float[][] getMatrix() {
        return matrix;
    }
}