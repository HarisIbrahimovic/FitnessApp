package com.example.myfitness.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myfitness.R;
import com.example.myfitness.ViewModel.NewWorkoutViewModel;

public class NewWorkoutActivity extends AppCompatActivity {
    private EditText workoutName, exerciseName;
    private EditText repsValue, restValue;
    private TextView weightValue, setsValue;
    private TextView chestAndArms,cardio,legs,push,pull,abdomen,shoulders,backAndShoulders,arms,chest,armsAndShoulders,chestAndTriceps,upper;
    private Button leftWeight,rightWeight,leftSets,rightSets;
    private TextView lastTextValue;
    private Button createWorkout;
    private NewWorkoutViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_workout);
        setUpView();
        onClicks();
        observe();
    }

    private void observe() {
        viewModel.getToastMessage().observe(this, s -> {
            Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
        });

        viewModel.getWorkoutCreated().observe(this, aBoolean -> {
            if(aBoolean){
                createWorkout.setText("Add");
            }
        });
        viewModel.getWeightValue().observe(this, i->{
            weightValue.setText(i+"");
        });

        viewModel.getSetsValue().observe(this, i->{
            setsValue.setText(i+"");
        });

    }



    private void onClicks() {
        chestAndTriceps.setOnClickListener(v -> {
            updateColors(chestAndTriceps);
        });
        upper.setOnClickListener(v -> {
            updateColors(upper);
        });
        armsAndShoulders.setOnClickListener(v -> {
            updateColors(armsAndShoulders);
        });
        chest.setOnClickListener(v -> {
            updateColors(chest);
        });
        arms.setOnClickListener(v -> {
            updateColors(arms);
        });
        backAndShoulders.setOnClickListener(v -> {
            updateColors(backAndShoulders);
        });
        abdomen.setOnClickListener(v -> {
            updateColors(abdomen);
        });
        shoulders.setOnClickListener(v -> {
            updateColors(shoulders);
        });
        pull.setOnClickListener(v -> {
            updateColors(pull);
        });
        push.setOnClickListener(v -> {
            updateColors(push);
        });
        legs.setOnClickListener(v -> {
            updateColors(legs);
        });
        cardio.setOnClickListener(v -> {
            updateColors(cardio);
        });
        chestAndArms.setOnClickListener(v -> {
            updateColors(chestAndArms);
        });
        leftSets.setOnClickListener(v -> {
            updateValueS(setsValue,-1);
        });
        rightSets.setOnClickListener(v -> {
            updateValueS(setsValue,1);
        });
        leftWeight.setOnClickListener(v -> {
            updateValueW(weightValue,-5);
        });
        rightWeight.setOnClickListener(v -> {
            updateValueW(weightValue,5);
        });
        createWorkout.setOnClickListener(v -> {
            checkValues();
        });
        workoutName.setOnClickListener(v -> {
            if(lastTextValue!=null) lastTextValue.setTextColor(getResources().getColor(R.color.myBlue));
        });

    }

    private void checkValues() {
        String wName = workoutName.getText().toString().trim();
        String eName = exerciseName.getText().toString().trim();
        String reps = repsValue.getText().toString().trim();
        String sets = setsValue.getText().toString().trim();
        String rest = restValue.getText().toString().trim();
        String weight = weightValue.getText().toString().trim();
        String buttonState = createWorkout.getText().toString().trim();
        viewModel.createWorkout(wName,eName,reps,sets,rest,weight,buttonState);
    }

    private void updateValueW(TextView textView, int i) {
        if(textView.getText().equals("0")&&i==-5)return;
        int num = Integer.parseInt(textView.getText().toString());
        num+=i;
        viewModel.setWeightValue(num);
    }
    private void updateValueS(TextView textView, int i) {
        if(textView.getText().equals("0")&&i==-1)return;
        int num = Integer.parseInt(textView.getText().toString());
        num+=i;
        viewModel.setSetsValue(num);
    }

    private void updateColors(TextView textView) {
        if(lastTextValue!=null) lastTextValue.setTextColor(getResources().getColor(R.color.myBlue));
        textView.setTextColor(getResources().getColor(R.color.teal_200));
        lastTextValue = textView;
        workoutName.setText(textView.getText().toString());
    }

    private void setUpView() {
        getWindow().setNavigationBarColor(getResources().getColor(R.color.myPurple));
        workoutName = findViewById(R.id.workoutNameEditText);
        exerciseName = findViewById(R.id.exerciseNameEditText);
        repsValue = findViewById(R.id.repsEditText);
        restValue = findViewById(R.id.restEditText);
        weightValue = findViewById(R.id.weightTextView);
        setsValue = findViewById(R.id.setsTextView);
        chestAndArms = findViewById(R.id.chestAndArms);
        cardio = findViewById(R.id.cardio);
        legs = findViewById(R.id.legs);
        pull = findViewById(R.id.pull);
        push = findViewById(R.id.push);
        abdomen = findViewById(R.id.abdomen);
        shoulders = findViewById(R.id.shoulders);
        backAndShoulders = findViewById(R.id.backAndShoulders);
        arms = findViewById(R.id.arms);
        chest = findViewById(R.id.chest);
        armsAndShoulders = findViewById(R.id.armsAndShoulders);
        chestAndTriceps = findViewById(R.id.chestAndTriceps);
        upper = findViewById(R.id.upper);
        leftWeight = findViewById(R.id.weightLeft);
        leftSets = findViewById(R.id.setsLeft);
        rightSets = findViewById(R.id.setsRight);
        rightWeight = findViewById(R.id.weightRight);
        createWorkout = findViewById(R.id.createWorkoutButton);
        viewModel = ViewModelProviders.of(this).get(NewWorkoutViewModel.class);
        viewModel.init();

    }
}