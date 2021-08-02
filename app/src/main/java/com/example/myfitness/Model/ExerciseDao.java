package com.example.myfitness.Model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ExerciseDao {
    @Insert(onConflict= OnConflictStrategy.REPLACE)
    void addExercise(Exercise exercise);

    @Query("DELETE FROM exercise_table WHERE id = :inputId")
    void deleteExs(String inputId);

    @Query("DELETE FROM exercise_table ")
    void deleteAllExercises();

    @Query("SELECT * FROM exercise_table WHERE workoutId = :inputId")
    LiveData<List<Exercise>> getExerciseList(String inputId);

}
