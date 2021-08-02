package com.example.myfitness.ViewModel;

import android.app.Application;
import android.text.TextUtils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myfitness.Model.DietDay;
import com.example.myfitness.Model.User;
import com.example.myfitness.Model.Workout;
import com.example.myfitness.Repository.MenuRepository;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MenuViewModel extends ViewModel {
    private LiveData<User> user;
    private LiveData<List<Workout>> myWorkoutList;
    private MutableLiveData<Integer> selectedFragmentNum = new MutableLiveData<>();
    private MutableLiveData<String> toastMessage = new MutableLiveData<>();
    private MutableLiveData<Boolean> createdState = new MutableLiveData<>();
    private LiveData<List<DietDay>> listOfDays ;

    private MenuRepository repository;

    public void init(Application application){
        repository = MenuRepository.getInstance();
        user = repository.getCurrentUser(application);
        myWorkoutList = repository.getMyWorkoutList(application);
        listOfDays = repository.getMyDays(application);
      }

    public void setSelectedFragmentNum(int num){
        selectedFragmentNum.setValue(num);
    }

    public LiveData<Integer> getSelectedFragmentNum() {
        return selectedFragmentNum;
    }
    public LiveData<List<Workout>> getMyWorkoutList() {
        return myWorkoutList;
    }
    public LiveData<List<DietDay>> getListOfDays() {
        return listOfDays;
    }
    public LiveData<Boolean> getCreatedState() {
        return createdState;
    }
    public LiveData<String> getToastMessage() {
        return toastMessage;
    }
    public LiveData<User> getUser() {
        return user;
    }

    public void addDay(String protein, String carbs, String fats,Application application) {
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
        repository.addDay(dietDay, application);
        toastMessage.setValue("Day added.");
        createdState.setValue(true);
    }

    public void updateDay(String id, String protein, String carbs, String fats,Application application) {
        if(TextUtils.isEmpty(protein)||TextUtils.isEmpty(carbs)||TextUtils.isEmpty(fats)){
            toastMessage.setValue("Fill in the fields");
            return;
        }
        int numProtein = Integer.parseInt(protein);
        int numCarbs = Integer.parseInt(carbs);
        int numFats = Integer.parseInt(fats);
        int calories = (numProtein+numCarbs)*4+numFats*9;
        DietDay dietDay = new DietDay(id,numProtein,numCarbs,numFats,calories);
        repository.addDay(dietDay,application);
        toastMessage.setValue("Day updated.");
        createdState.setValue(true);
    }

    public void deleteDay(String id,Application application) {
        repository.deleteDay(id,application);
        toastMessage.setValue("Day removed");
        createdState.setValue(true);
    }

    public void deleteWorkout(String id,Application application) {
        repository.deleteWorkout(id,application);
    }
}
