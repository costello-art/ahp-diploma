package model;

import model.math.Calculate;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Sviat on 12.11.14.
 */
public class GlobalTarget implements IGlobalTargetObject {
    private String target;
    private int actorCount;

    private ArrayList<Actor> actors;

    private float[][] matrix;
    private ArrayList<Float> selfVector;

    HashMap<Actor, Float> bestActors;

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
        selfVector = new ArrayList<>();
        bestActors = new HashMap<>();

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

        selfVector = Calculate.selfVectorForMatrix(matrix);

         /*float max = selfVector.get(0);
        TODO: треба вибирати два найкращих актори (максимальні значення вектора)
        for (int i = 0; i < 2; i++) {
            for (int j = 1; j < selfVector.size(); j++) {
                if (max < selfVector.get(j)) {
                    max = selfVector.get(j);
                }
            }
        }*/
    }

    public float[][] getMatrix() {
        return matrix;
    }
}