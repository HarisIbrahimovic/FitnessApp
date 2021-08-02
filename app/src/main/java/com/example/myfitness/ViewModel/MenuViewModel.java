package com.example.myfitness.ViewModel;

import android.text.TextUtils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myfitness.Model.DietDay;
import com.example.myfitness.Model.User;
import com.example.myfitness.Model.Workout;
import com.example.myfitness.Repository.MenuRepository;
import com.github.mikephil.charting.data.LineData;


import java.util.List;
import java.util.UUID;

public class MenuViewModel extends ViewModel {
    private MutableLiveData<User> user;
    private MutableLiveData<List<Workout>> myWorkoutList;
    private MutableLiveData<Integer> selectedFragmentNum = new MutableLiveData<>();
    private MutableLiveData<String> toastMessage = new MutableLiveData<>();
    private MutableLiveData<Boolean> createdState = new MutableLiveData<>();
    private MutableLiveData<List<DietDay>> listOfDays = new MutableLiveData<>();
    private MutableLiveData<LineData> calorieData = new MutableLiveData<>();
    private MutableLiveData<LineData> proteinData = new MutableLiveData<>();

    public MutableLiveData<LineData> getCalorieData() {
        return calorieData;
    }

    public MutableLiveData<LineData> getProteinData() {
        return proteinData;
    }

    private MenuRepository repository;



    public void init(){
        repository = MenuRepository.getInstance();
        user = repository.getCurrentUser();
        myWorkoutList = repository.getMyWorkoutList();
        listOfDays = repository.getMyDays();
        calorieData = repository.getCalorieData();
        proteinData = repository.getProteinData();
    }
    public void setSelectedFragmentNum(int num){
        selectedFragmentNum.setValue(num);
    }

    public LiveData<List<Workout>> getMyWorkoutList() {
        return myWorkoutList;
    }
    public LiveData<Integer> getSelectedFragmentNum() {
        return selectedFragmentNum;
    }
    public LiveData<String> getToastMessage() {
        return toastMessage;
    }
    public LiveData<Boolean> getCreatedState() {
        return createdState;
    }
    public LiveData<User> getUser() {
        return user;
    }

    public MutableLiveData<List<DietDay>> getListOfDays() {
        return listOfDays;
    }

    public void deleteWorkout(String id) {
        repository.deleteWorkout(id);
    }

    public void addDay(String protein, String carbs, String fats) {
        if(TextUtils.isEmpty(protein)||TextUtils.isEmpty(carbs)||TextUtils.isEmpty(fats)){
            toastMessage.setValue("Fill in the fields");
            return;
        }
        int numProtein = Integer.parseInt(protein);
        int numCarbs = Integer.parseInt(carbs);
        int numFats = Integer.parseInt(fats);
        int calories = (numProtein+numCarbs)*4+numFats*9;
        String id = UUID.randomUUID().toString();
        DietDay dietDay = new DietDay(id,numProtein,numCarbs,numFats,calories);
        repository.addDay(dietDay);
        toastMessage.setValue("Day added.");
        createdState.setValue(true);
    }

    public void updateDay(String id, String protein, String carbs, String fats) {
        if(TextUtils.isEmpty(protein)||TextUtils.isEmpty(carbs)||TextUtils.isEmpty(fats)){
            toastMessage.setValue("Fill in the fields");
            return;
        }
        int numProtein = Integer.parseInt(protein);
        int numCarbs = Integer.parseInt(carbs);
        int numFats = Integer.parseInt(fats);
        int calories = (numProtein+numCarbs)*4+numFats*9;
        DietDay dietDay = new DietDay(id,numProtein,numCarbs,numFats,calories);
        repository.addDay(dietDay);
        toastMessage.setValue("Day updated.");
        createdState.setValue(true);
    }

    public void deleteDay(String id) {
        repository.deleteDay(id);
        toastMessage.setValue("Day removed");
        createdState.setValue(true);
    }
}
