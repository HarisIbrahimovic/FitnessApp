package com.example.myfitness.Repository;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.myfitness.Model.DayDao;
import com.example.myfitness.Model.DietDay;
import com.example.myfitness.Model.Exercise;
import com.example.myfitness.Model.ExerciseDao;
import com.example.myfitness.Model.User;
import com.example.myfitness.Model.UserDao;
import com.example.myfitness.Model.Workout;
import com.example.myfitness.Model.WorkoutDao;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainRepository {
    static MainRepository repository;
    private WorkoutDao workoutDao;
    private ExerciseDao exerciseDao;
    private UserDao userDao;
    private DayDao dayDao;

    public static MainRepository getInstance(){
        if(repository==null) repository = new MainRepository();
        return repository;
    }

    public void addUser(User user) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(user.getId());
        databaseReference.setValue(user);
    }


    public void fetchData(Application application) {
        workoutDao = Db.getInstance(application).workoutDao();
        exerciseDao = Db.getInstance(application).exerciseDao();
        userDao = Db.getInstance(application).userDao();
        dayDao = Db.getInstance(application).dayDao();
        getUser();
        getAllWorkouts();
        getAllExercises();
        getDays();

    }

    private void getDays() {
        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(id).child("Days");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    DietDay dietDay = dataSnapshot.getValue(DietDay.class);
                    new MenuRepository.InsertDay(dayDao).execute(dietDay);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void getAllExercises() {
        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(id).child("Exercises");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Exercise exercise = dataSnapshot.getValue(Exercise.class);
                    new MenuRepository.InsertExercise(exerciseDao).execute(exercise);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void getAllWorkouts() {
        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(id).child("Workouts");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Workout workout = dataSnapshot.getValue(Workout.class);
                    new MenuRepository.InsertWorkout(workoutDao).execute(workout);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void getUser() {
        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(id);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                new MenuRepository.InsertUser(userDao).execute(user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
