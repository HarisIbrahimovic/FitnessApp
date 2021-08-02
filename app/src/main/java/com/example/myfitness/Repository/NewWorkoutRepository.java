package com.example.myfitness.Repository;

import com.example.myfitness.Model.Exercise;
import com.example.myfitness.Model.Workout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NewWorkoutRepository {
    static NewWorkoutRepository repository;

    public static NewWorkoutRepository getInstance(){
        if(repository==null)repository = new NewWorkoutRepository();
        return repository;
    }

    public void addWorkout(Workout workout) {
        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(id).child("Workouts");
        databaseReference.child(workout.getId()).setValue(workout);
    }

    public void addExercise(Exercise exercise) {
        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(id).child("Exercises");
        databaseReference.child(exercise.getId()).setValue(exercise);
    }
}
