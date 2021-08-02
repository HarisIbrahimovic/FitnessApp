package com.example.myfitness.Model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface DayDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDay(DietDay dietDay);

    @Query("DELETE FROM day_table")
    void deleteAllDays();

    @Query("DELETE FROM day_table WHERE id = :inputId")
    void deleteDay(String inputId);

    @Query("SELECT * FROM day_table ")
    LiveData<List<DietDay>> getAllDays();



}
