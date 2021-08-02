package com.example.myfitness.Repository;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myfitness.Model.Exercise;
import com.example.myfitness.Model.ExerciseDao;
import com.example.myfitness.Model.Workout;
import com.example.myfitness.Model.WorkoutDao;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ExercisesRepository {
    static ExercisesRepository repository;
    private ExerciseDao exerciseDao;
    private WorkoutDao workoutDao;

    public static ExercisesRepository getInstance(){
        if(repository==null)repository= new ExercisesRepository();
        return repository;
    }

    private LiveData<List<Exercise>> exerciseList = new MutableLiveData<>();

    public LiveData<List<Exercise>> getExerciseList(String id, Application application) {
        exerciseDao = Db.getInstance(application).exerciseDao();
        exerciseList = exerciseDao.getExerciseList(id);
        return exerciseList;
    }

    public void deleteExercise(String id,Application application) {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userId).child("Exercises").child(id);
        databaseReference.removeValue();
        exerciseDao = Db.getInstance(application).exerciseDao();
        new MenuRepository.DeleteExercise(exerciseDao).execute(id);
    }

    public void deleteWorkout(String workoutId, Application application) {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userId).child("Workouts").child(workoutId);
        databaseReference.removeValue();
        workoutDao = Db.getInstance(application).workoutDao();
        new MenuRepository.DeleteWorkout(workoutDao).execute(workoutId);
    }

    public void addExercise(Exercise exercise,Application application) {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userId).child("Exercises").child(exercise.getId());
        databaseReference.setValue(exercise);
        exerciseDao = Db.getInstance(application).exerciseDao();
        new MenuRepository.InsertExercise(exerciseDao).execute(exercise);
    }



}
