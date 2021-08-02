package com.example.myfitness.Repository;


import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import com.example.myfitness.Model.DietDay;
import com.example.myfitness.Model.User;
import com.example.myfitness.Model.Workout;
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
    private MutableLiveData<User> currentUser = new MutableLiveData<>();
    private MutableLiveData<List<DietDay>> listOfMyDays = new MutableLiveData<>();
    private MutableLiveData<List<Workout>> myWorkoutList= new MutableLiveData<List<Workout>>();
    private MutableLiveData<LineData> calorieData = new MutableLiveData<>();
    private MutableLiveData<LineData> proteinData = new MutableLiveData<>();
    private User user = new User();

    public static MenuRepository getInstance(){
        if(repository == null)repository = new MenuRepository();
        return repository;
    }

    public MutableLiveData<List<Workout>> getMyWorkoutList() {
        setMyWorkoutList();
        return myWorkoutList;
    }
    public MutableLiveData<User> getCurrentUser() {
        setCurrentUser();
        return currentUser;
    }

    private void setMyWorkoutList() {
        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(id).child("Workouts");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Workout> workouts = new ArrayList<>();
                workouts.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    workouts.add(dataSnapshot.getValue(Workout.class));
                }
                myWorkoutList.postValue(workouts);
                myWorkoutList.setValue(workouts);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setCurrentUser(){
        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(id);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                currentUser.postValue(snapshot.getValue(User.class));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public void deleteWorkout(String id) {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userId).child("Workouts").child(id);
        databaseReference.removeValue();
    }

    public void addDay(DietDay dietDay) {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userId).child("Days").child(dietDay.getId());
        databaseReference.setValue(dietDay);
    }

    public MutableLiveData<List<DietDay>> getMyDays() {
        setMyDays();
        return listOfMyDays;

    }

    private void setMyDays() {
        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(id).child("Days");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<DietDay> days = new ArrayList<>();
                days.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    days.add(dataSnapshot.getValue(DietDay.class));
                }
                listOfMyDays.postValue(days);
                listOfMyDays.setValue(days);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void deleteDay(String id) {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userId).child("Days").child(id);
        databaseReference.removeValue();
    }

    public MutableLiveData<LineData> getCalorieData() {
        setCalorieData();
        return calorieData;
    }

    private void setCalorieData() {
        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(id).child("Days");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<DietDay> days = new ArrayList<>();
                days.clear();
                ArrayList<Entry> calorieEntries = new ArrayList<>();
                int i=1;
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    DietDay dietDay = dataSnapshot.getValue(DietDay.class);
                    calorieEntries.add(new Entry(i,dietDay.getCalories()));
                    i++;
                }
                LineDataSet set = new LineDataSet(calorieEntries,"Daily calories");

                ArrayList<ILineDataSet> dataSets = new ArrayList<>();
                set.setFillAlpha(100);
                dataSets.add(set);
                LineData data = new LineData(dataSets);
                calorieData.postValue(data);
                calorieData.setValue(data);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public MutableLiveData<LineData> getProteinData() {
        setProteinData();
        return proteinData;
    }

    private void setProteinData() {
        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(id).child("Days");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<DietDay> days = new ArrayList<>();
                days.clear();
                ArrayList<Entry> proteinEntries = new ArrayList<>();
                int i=1;
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    DietDay dietDay = dataSnapshot.getValue(DietDay.class);
                    proteinEntries.add(new Entry(i,dietDay.getProtein()));
                    i++;
                }
                LineDataSet set = new LineDataSet(proteinEntries,"Daily protein");
                set.setFillAlpha(100);
                ArrayList<ILineDataSet> dataSets = new ArrayList<>();
                dataSets.add(set);
                LineData data = new LineData(dataSets);
                proteinData.postValue(data);
                proteinData.setValue(data);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
