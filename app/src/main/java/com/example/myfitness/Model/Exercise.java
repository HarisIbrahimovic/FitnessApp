package com.example.myfitness.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Exercise {
    @PrimaryKey
    private String id;
    private String workoutId;
    private String exerciseName;
    private int weight;
    private int rest;
    private int sets;
    private int reps;

    public Exercise() {
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public Exercise(String id, String workoutId, String exerciseName, int weight, int rest, int sets, int reps) {
        this.id = id;
        this.workoutId = workoutId;
        this.exerciseName = exerciseName;
        this.weight = weight;
        this.rest = rest;
        this.sets = sets;
        this.reps = reps;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWorkoutId() {
        return workoutId;
    }

    public void setWorkoutId(String workoutId) {
        this.workoutId = workoutId;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getRest() {
        return rest;
    }

    public void setRest(int rest) {
        this.rest = rest;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }
}
