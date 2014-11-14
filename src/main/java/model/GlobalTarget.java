package model;

import java.util.ArrayList;

/**
 * Created by Sviat on 12.11.14.
 */
public class GlobalTarget implements IGlobalTargetObject {
    private String target;
    private int actorCount;

    private ArrayList<Actor> actors;

    private float[][] matrix;
    private float[] selfVector;

    /**
     * Глобальна мета, яка містить акторів та їх цілі
     * @param target назва мети
     * @param actorCount кількість акторів
     */
    public GlobalTarget(String target, int actorCount) {
        this.target = target;
        this.actorCount = actorCount;
        matrix = new float[actorCount][actorCount];
        actors = new ArrayList<>();

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
        actors.add(actor);
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
        return actors;
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
    }

    public float[][] getMatrix() {
        return matrix;
    }
}