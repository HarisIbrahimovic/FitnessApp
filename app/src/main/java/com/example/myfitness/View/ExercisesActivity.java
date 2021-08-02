package com.example.myfitness.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myfitness.Adapters.ExerciseAdapter;
import com.example.myfitness.Model.Exercise;
import com.example.myfitness.R;
import com.example.myfitness.ViewModel.ExerciseViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ExercisesActivity extends AppCompatActivity implements ExerciseAdapter.TouchListner{
    private RecyclerView recyclerView;
    private ExerciseAdapter adapter;
    private ExerciseViewModel viewModel;
    private List<Exercise>exerciseList;
    private FloatingActionButton deleteButton;
    private FloatingActionButton addExerciseButton;
    private Button button;
    private TextView weightValue;
    private EditText restValue;
    private EditText exName;
    private TextView setsValue;
    private EditText repsValue;
    private Button leftWeight,rightWeight,leftSets,rightSets;
    private View myView;
    private String exId;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises);
        setUpView();
        onClicks();
        observe();

    }

    private void observe() {
        viewModel.getExerciseList().observe(this, exercises -> {
            adapter.setExerciseList(exercises);
            recyclerView.setAdapter(adapter);
            exerciseList = exercises;
        });
        viewModel.getDoneUpdate().observe(this, bool -> {
            if(bool) {
                dialog.dismiss();
            }
        });
        viewModel.getToastMess().observe(this, s->{
            Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
        });
    }

    private void setUpView() {
        getWindow().setNavigationBarColor(getResources().getColor(R.color.myPurple));
        viewModel = ViewModelProviders.of(this).get(ExerciseViewModel.class);
        viewModel.init(getIntent().getStringExtra("workoutId"));
        deleteButton= findViewById(R.id.deleteWorkoutFB);
        addExerciseButton = findViewById(R.id.addExs);
        recyclerView = findViewById(R.id.recylclerViewEx);
        TextView workoutName = findViewById(R.id.textView4);
        workoutName.setText(getIntent().getStringExtra("workoutName"));
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new ExerciseAdapter(getApplicationContext(),this);
        recyclerView.setAdapter(adapter);
    }

    private void onClicks() {
        addExerciseButton.setOnClickListener(v -> {
            openUpdate();
            configureUpdate();
            button.setText("Add");
            onClicksUpdate();
        });
        deleteButton.setOnClickListener(v-> {
            viewModel.deleteWorkout(getIntent().getStringExtra("workoutId"));
            Toast.makeText(getApplicationContext(), "Workout removed", Toast.LENGTH_SHORT).show();
            finish();
        });
    }


    @Override
    public void onNoteClick(int poistion) {
        openUpdate();
        configureUpdate();
        setUpViewForUpdate(exerciseList.get(poistion));
        onClicksUpdate();

    }

    private void onClicksUpdate() {
        leftSets.setOnClickListener(v -> {
            updateValue(setsValue,-1);
        });
        rightSets.setOnClickListener(v -> {
            updateValue(setsValue,1);
        });
        leftWeight.setOnClickListener(v -> {
            updateValue(weightValue,-5);
        });
        rightWeight.setOnClickListener(v -> {
            updateValue(weightValue,5);
        });
        button.setOnClickListener(v -> {
            String workoutId = getIntent().getStringExtra("workoutId");
            viewModel.addExercise(exId,workoutId,exName.getText().toString(),setsValue.getText().toString(),repsValue.getText().toString()
                    ,weightValue.getText().toString(),restValue.getText().toString(),button.getText().toString());
        });
    }

    private void updateValue(TextView textView, int i) {
        if(textView.getText().equals("0"))return;
        int num = Integer.parseInt(textView.getText().toString());
        num+=i;
        textView.setText(num+"");
    }


    private void setUpViewForUpdate(Exercise exercise) {
        button.setText("Update");
        repsValue.setText(exercise.getReps()+"");
        setsValue.setText(exercise.getSets()+"");
        weightValue.setText(exercise.getWeight()+"");
        exName.setText(exercise.getExerciseName());
        restValue.setText(exercise.getRest()+"");
        exId = exercise.getId();
    }

    private void configureUpdate() {
        button = myView.findViewById(R.id.updateButton);
        weightValue = myView.findViewById(R.id.updateWeightTextView);
        setsValue = myView.findViewById(R.id.updateSetsTextView);
        repsValue = myView.findViewById(R.id.uRepsEditText);
        exName = myView.findViewById(R.id.uExerciseNameEditText);
        restValue = myView.findViewById(R.id.uRestEditText);
        leftSets= myView.findViewById(R.id.uSetsLeft);
        rightSets = myView.findViewById(R.id.uSetsRight);
        leftWeight = myView.findViewById(R.id.uWeightLeft);
        rightWeight = myView.findViewById(R.id.uWeightRight);
    }

    private void openUpdate() {
        AlertDialog.Builder myDialog = new AlertDialog.Builder(ExercisesActivity.this);
        LayoutInflater inflater = LayoutInflater.from(ExercisesActivity.this);
        myView = inflater.inflate(R.layout.update_exercise,null);
        myDialog.setView(myView);
        dialog = myDialog.create();
        dialog.show();
    }

    @Override
    public void deleteNode(int position) {
        Exercise exercise = exerciseList.get(position);
        Toast.makeText(getApplicationContext(),"Exercise removed",Toast.LENGTH_SHORT).show();
        viewModel.deleteExercise(exercise.getId());
    }
}