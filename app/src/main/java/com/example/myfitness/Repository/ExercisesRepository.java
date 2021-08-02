package com.example.myfitness.Repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.myfitness.Model.Exercise;
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

    public static ExercisesRepository getInstance(){
        if(repository==null)repository= new ExercisesRepository();
        return repository;
    }

    private MutableLiveData<List<Exercise>> exerciseList = new MutableLiveData<>();

    public MutableLiveData<List<Exercise>> getExerciseList(String id) {
        setExerciseList(id);
        return exerciseList;
    }

    public void deleteExercise(String id) {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userId).child("Exercises").child(id);
        databaseReference.removeValue();
    }

    public void deleteWorkout(String workoutId) {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userId).child("Workouts").child(workoutId);
        databaseReference.removeValue();
    }

    public void addExercise(Exercise exercise) {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userId).child("Exercises").child(exercise.getId());
        databaseReference.setValue(exercise);
    }


    private void setExerciseList(String id) {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userId).child("Exercises");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Exercise> exercises = new ArrayList<>();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Exercise exercise = dataSnapshot.getValue(Exercise.class);
                    if(exercise.getWorkoutId().equals(id)){
                        exercises.add(exercise);
                    }
                }
                exerciseList.postValue(exercises);
                exerciseList.setValue(exercises);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
