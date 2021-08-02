package com.example.myfitness.Repository;

import android.app.Application;

import com.example.myfitness.Model.Exercise;
import com.example.myfitness.Model.ExerciseDao;
import com.example.myfitness.Model.Workout;
import com.example.myfitness.Model.WorkoutDao;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NewWorkoutRepository {
    static NewWorkoutRepository repository;
    private WorkoutDao workoutDao;
    private ExerciseDao exerciseDao;
    public static NewWorkoutRepository getInstance(){
        if(repository==null)repository = new NewWorkoutRepository();
        return repository;
    }

    public void addWorkout(Workout workout, Application application) {
        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(id).child("Workouts");
        databaseReference.child(workout.getId()).setValue(workout);
        workoutDao = Db.getInstance(application).workoutDao();
        new MenuRepository.InsertWorkout(workoutDao).execute(workout);
    }

    public void addExercise(Exercise exercise, Application application) {
        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(id).child("Exercises");
        databaseReference.child(exercise.getId()).setValue(exercise);
        exerciseDao = Db.getInstance(application).exerciseDao();
        new MenuRepository.InsertExercise(exerciseDao).execute(exercise);
    }
}
