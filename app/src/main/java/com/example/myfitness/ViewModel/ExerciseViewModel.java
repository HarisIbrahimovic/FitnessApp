package com.example.myfitness.ViewModel;

import android.app.Application;
import android.text.TextUtils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myfitness.Model.Exercise;
import com.example.myfitness.Repository.ExercisesRepository;

import java.util.List;
import java.util.UUID;

public class ExerciseViewModel extends ViewModel {
    private ExercisesRepository repository;
    private LiveData<List<Exercise>> exerciseList;
    private MutableLiveData<String> toastMess=new MutableLiveData<>();
    private MutableLiveData<Boolean> doneUpdate=new MutableLiveData<>();
    public void init(String id, Application application){
        repository = ExercisesRepository.getInstance();
        exerciseList = repository.getExerciseList(id,application);
    }

    public LiveData<String> getToastMess() {return toastMess;}
    public LiveData<Boolean> getDoneUpdate() {return doneUpdate;}
    public LiveData<List<Exercise>> getExerciseList() {
        return exerciseList;
    }

    public void deleteExercise(String id, Application application) {
        repository.deleteExercise(id,application);
    }

    public void deleteWorkout(String workoutId,Application application) {
        repository.deleteWorkout(workoutId,application);
    }

    public void addExercise(String exId, String workoutId, String exName, String sets, String reps, String weight, String rest, String button,Application application) {
        String random= UUID.randomUUID().toString();
        if(TextUtils.isEmpty(workoutId)||TextUtils.isEmpty(reps)||TextUtils.isEmpty(sets)||TextUtils.isEmpty(weight)||TextUtils.isEmpty(rest)||TextUtils.isEmpty(button)||TextUtils.isEmpty(exName)){
            toastMess.setValue("Fill in the fields.");
            return;
        }
        int numWeight = Integer.parseInt(weight);
        int numReps = Integer.parseInt(reps);
        int numRest = Integer.parseInt(rest);
        int numSets = Integer.parseInt(sets);
        doneUpdate.setValue(true);
        if(button.equals("Add")){
            Exercise exercise = new Exercise(random,workoutId,exName,numWeight,numRest,numSets,numReps);
            repository.addExercise(exercise,application);
        }else {
            Exercise exercise = new Exercise(exId,workoutId,exName,numWeight,numRest,numSets,numReps);
            repository.addExercise(exercise,application);
        }
        toastMess.setValue("Exercise added.");
    }
}
