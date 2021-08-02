package com.example.myfitness.Model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface WorkoutDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertWorkout(Workout workout);

    @Query("DELETE FROM workout_table WHERE id = :inputId")
    void deleteWorkout(String inputId);

    @Query("SELECT * FROM workout_table ")
    LiveData<List<Workout>> getAllWorkouts();
}
