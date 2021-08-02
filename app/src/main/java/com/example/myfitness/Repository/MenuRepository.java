package com.example.myfitness.Repository;


import android.annotation.SuppressLint;
import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myfitness.Model.DayDao;
import com.example.myfitness.Model.DietDay;
import com.example.myfitness.Model.Exercise;
import com.example.myfitness.Model.ExerciseDao;
import com.example.myfitness.Model.User;
import com.example.myfitness.Model.UserDao;
import com.example.myfitness.Model.Workout;
import com.example.myfitness.Model.WorkoutDao;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;


public class MenuRepository {
    public static MenuRepository repository;
    private LiveData<User> currentUser = new MutableLiveData<>();
    private LiveData<List<DietDay>> listOfMyDays = new MutableLiveData<>();
    private LiveData<List<Workout>> myWorkoutList= new MutableLiveData<List<Workout>>();
    private WorkoutDao workoutDao;
    private UserDao userDao;
    private ExerciseDao exerciseDao;
    private DayDao dayDao;
    private User user = new User();

    public static MenuRepository getInstance(){
        if(repository == null)repository = new MenuRepository();
        return repository;
    }

    public LiveData<List<Workout>> getMyWorkoutList(Application application) {
        workoutDao = Db.getInstance(application).workoutDao();
        myWorkoutList = workoutDao.getAllWorkouts();
        return myWorkoutList;
    }
    public LiveData<User> getCurrentUser(Application application) {
        userDao = Db.getInstance(application).userDao();
        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        currentUser = userDao.getCurrentUser(id);
        return currentUser;
    }


    public void deleteWorkout(String id,Application application) {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userId).child("Workouts").child(id);
        databaseReference.removeValue();
        workoutDao = Db.getInstance(application).workoutDao();
        new DeleteWorkout(workoutDao).execute(id);
    }

    public void addDay(DietDay dietDay,Application application) {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userId).child("Days").child(dietDay.getId());
        databaseReference.setValue(dietDay);
        dayDao = Db.getInstance(application).dayDao();
        new InsertDay(dayDao).execute(dietDay);

    }

    public LiveData<List<DietDay>> getMyDays(Application application) {
        dayDao = Db.getInstance(application).dayDao();
        listOfMyDays = dayDao.getAllDays();
        return listOfMyDays;
    }



    public void deleteDay(String id,Application application) {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userId).child("Days").child(id);
        databaseReference.removeValue();
        dayDao = Db.getInstance(application).dayDao();
        new DeleteDay(dayDao).execute(id);
    }





    public static class InsertUser extends AsyncTask<User,Void,Void> {
        private UserDao userDao;
        public InsertUser(UserDao userDao) {
            this.userDao = userDao;
        }
        @Override
        protected Void doInBackground(User... users) {
            userDao.addUser(users[0]);
            return null;
        }
    }

    public static class InsertWorkout extends AsyncTask<Workout,Void,Void> {
        private WorkoutDao workoutDao;
        public InsertWorkout(WorkoutDao workoutDao) {
            this.workoutDao = workoutDao;
        }
        @Override
        protected Void doInBackground(Workout... workouts) {
            workoutDao.insertWorkout(workouts[0]);
            return null;
        }
    }


    public static class InsertExercise extends AsyncTask<Exercise,Void,Void> {
        private ExerciseDao exerciseDao;
        public InsertExercise(ExerciseDao exerciseDao) {
            this.exerciseDao = exerciseDao;
        }
        @Override
        protected Void doInBackground(Exercise... exercises) {
            exerciseDao.addExercise(exercises[0]);
            return null;
        }
    }

    public static class InsertDay extends AsyncTask<DietDay,Void,Void> {
        private DayDao dayDao;
        public InsertDay(DayDao dayDao) {
            this.dayDao = dayDao;
        }
        @Override
        protected Void doInBackground(DietDay... dietDays) {
            dayDao.insertDay(dietDays[0]);
            return null;
        }
    }

    public static class DeleteWorkout extends AsyncTask<String,Void,Void> {
        private WorkoutDao workoutDao;
        public DeleteWorkout(WorkoutDao workoutDao) {
            this.workoutDao = workoutDao;
        }
        @Override
        protected Void doInBackground(String... strings) {
            workoutDao.deleteWorkout(strings[0]);
            return null;
        }
    }

    public static class DeleteExercise extends AsyncTask<String,Void,Void> {
        private ExerciseDao exerciseDao;
        public DeleteExercise(ExerciseDao exerciseDao) {
            this.exerciseDao = exerciseDao;
        }
        @Override
        protected Void doInBackground(String... strings) {
            exerciseDao.deleteExs(strings[0]);
            return null;
        }
    }

    public static class DeleteDay extends AsyncTask<String,Void,Void> {
        private DayDao dayDao;
        public DeleteDay(DayDao dayDao) {
            this.dayDao = dayDao;
        }
        @Override
        protected Void doInBackground(String... strings) {
            dayDao.deleteDay(strings[0]);
            return null;
        }
    }

}
