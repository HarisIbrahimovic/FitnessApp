package com.example.myfitness.Repository;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.myfitness.Model.DayDao;
import com.example.myfitness.Model.DietDay;
import com.example.myfitness.Model.Exercise;
import com.example.myfitness.Model.ExerciseDao;
import com.example.myfitness.Model.User;
import com.example.myfitness.Model.UserDao;
import com.example.myfitness.Model.Workout;
import com.example.myfitness.Model.WorkoutDao;

@Database(entities = {Workout.class, Exercise.class, User.class, DietDay.class}, version = 1,exportSchema = false)
abstract class Db extends RoomDatabase {
    public abstract WorkoutDao workoutDao();
    public abstract ExerciseDao exerciseDao();
    public abstract UserDao userDao();
    public abstract DayDao dayDao();

    public static Db instance;
    public static synchronized Db getInstance(Application application){
        if(instance==null){
            instance = Room.databaseBuilder(application.getApplicationContext(), Db.class, "my_db")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db){
            super.onCreate(db);
        }
    };

}
