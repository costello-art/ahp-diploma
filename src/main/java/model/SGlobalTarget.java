package model;

import java.util.ArrayList;

/**
 * Created by Sviat on 23.11.14.
 */
public class SGlobalTarget {

    private String name;

    private ArrayList<String> actors = new ArrayList<>();
    private ArrayList<String> targets = new ArrayList<>();
    private ArrayList<String> scenarios = new ArrayList<>();

    private double[][] actorsMatrix;
    private ArrayList<Double> actorsSelfVector = new ArrayList<>();

    private ArrayList<double[][]> targetsMatrices = new ArrayList<>();
    private ArrayList<double[][]> scenariosMatrices = new ArrayList<>();

    private double[][] matrixFromScenariosSelfVectors;
    private double[][] matrixFromTargetsSelfVectors;

    private ArrayList<Double> result;

    public SGlobalTarget(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getActors() {
        return actors;
    }

    public void addActor(String actor) {
        actors.add(actor);
    }

    public ArrayList<String> getTargets() {
        return targets;
    }

    public void addTarget(String target) {
        targets.add(target);
    }

    public ArrayList<String> getScenarios() {
        return scenarios;
    }

    public void addScenario(String scenario) {
        scenarios.add(scenario);
    }

    public double[][] getActorsMatrix() {
        return actorsMatrix;
    }

    public void setActorsMatrix(double[][] actorsMatrix) {
        this.actorsMatrix = actorsMatrix;
    }

    public ArrayList<Double> getActorsSelfVector() {
        return actorsSelfVector;
    }

    public void setActorsSelfVector(ArrayList<Double> actorsSelfVector) {
        this.actorsSelfVector = actorsSelfVector;
    }

    public ArrayList<double[][]> getTargetsMatrices() {
        return targetsMatrices;
    }

    public void setTargetsMatrices(ArrayList<double[][]> targetsMatrices) {
        this.targetsMatrices = targetsMatrices;
    }

    public ArrayList<double[][]> getScenariosMatrices() {
        return scenariosMatrices;
    }

    public void addScenarioMatrix(double[][] scenariosMatrix) {
        scenariosMatrices.add(scenariosMatrix);
    }

    public double[][] getMatrixFromScenariosSelfVectors() {
        return matrixFromScenariosSelfVectors;
    }

    public void setMatrixFromScenariosSelfVectors(double[][] matrixFromScenariosSelfVectors) {
        this.matrixFromScenariosSelfVectors = matrixFromScenariosSelfVectors;
    }

    public double[][] getMatrixFromTargetsSelfVectors() {
        return matrixFromTargetsSelfVectors;
    }

    public void setMatrixFromTargetsSelfVectors(double[][] matrixFromTargetsSelfVectors) {
        this.matrixFromTargetsSelfVectors = matrixFromTargetsSelfVectors;
    }

    public void calculate() {
        result = null;
    }
}
