package com.example.myfitness.ViewModel;

import android.app.Application;
import android.text.TextUtils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.myfitness.Model.Exercise;
import com.example.myfitness.Model.Workout;
import com.example.myfitness.Repository.NewWorkoutRepository;
import java.util.UUID;

public class NewWorkoutViewModel extends ViewModel {

    private MutableLiveData<Boolean> workoutCreated = new MutableLiveData<>();
    private MutableLiveData<String> toastMessage = new MutableLiveData<>();
    private MutableLiveData<Integer> weightValue = new MutableLiveData<>();
    private MutableLiveData<Integer> setsValue = new MutableLiveData<>();
    private NewWorkoutRepository repository;
    private String randomId;

    public void init(){
        repository = NewWorkoutRepository.getInstance();
    }

    public void setWeightValue(int num){
        weightValue.setValue(num);
    }
    public void setSetsValue(int num){
        setsValue.setValue(num);
    }

    public LiveData<Integer> getWeightValue() {
        return weightValue;
    }
    public LiveData<Integer> getSetsValue() {
        return setsValue;
    }
    public LiveData<String> getToastMessage() {
        return toastMessage;
    }
    public LiveData<Boolean> getWorkoutCreated() {
        return workoutCreated;
    }

    private void justCreate(String wName, String randomId, Application application) {
        Workout workout = new Workout(randomId,wName);
        repository.addWorkout(workout,application);
        workoutCreated.setValue(true);
    }

    public void createWorkout(String wName, String eName, String reps, String sets, String rest, String weight, String buttonState,Application application) {
        if(TextUtils.isEmpty(wName)){
            toastMessage.setValue("Please add or select workout name.");
            return;
        }

        if(buttonState.equals("Create")){
            randomId = UUID.randomUUID().toString();
            justCreate(wName,randomId,application);
            toastMessage.setValue("Workout created.");
        }
        if(TextUtils.isEmpty(eName)||TextUtils.isEmpty(reps)||TextUtils.isEmpty(sets)||TextUtils.isEmpty(weight)||TextUtils.isEmpty(rest)){
            toastMessage.setValue("Please fill in exercise info.");
            return;
        }
        String exId = UUID.randomUUID().toString();
        int numReps = Integer.parseInt(reps);
        int numSets = Integer.parseInt(sets);
        int numWeight = Integer.parseInt(weight);
        int numRest = Integer.parseInt(rest);
        Exercise exercise = new Exercise(exId,randomId,eName,numWeight,numRest,numSets,numReps);
        repository.addExercise(exercise,application);
        toastMessage.setValue("Exercise added");
    }
}
