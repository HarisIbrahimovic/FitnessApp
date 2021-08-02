package com.example.myfitness.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myfitness.Adapters.WorkoutAdapter;
import com.example.myfitness.Model.User;
import com.example.myfitness.Model.Workout;
import com.example.myfitness.R;
import com.example.myfitness.View.ExercisesActivity;
import com.example.myfitness.View.NewWorkoutActivity;
import com.example.myfitness.ViewModel.MenuViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.util.List;


public class WorkoutFragment extends Fragment implements WorkoutAdapter.TouchListner{
    private TextView userNameTextView;
    private MenuViewModel viewModel;
    private RecyclerView recyclerView;
    private WorkoutAdapter workoutAdapter;
    private Button addWorkoutButton;
    private List<Workout> workoutList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_workout,container,false);
        setUpRecView(view);
        observe();
        onClicks();
        return view;
    }

    private void onClicks() {
        addWorkoutButton.setOnClickListener(v -> startActivity(new Intent(getActivity(), NewWorkoutActivity.class)));
    }

    private void observe() {
        viewModel.getMyWorkoutList().observe(getViewLifecycleOwner(), workouts -> {
            workoutAdapter.setWorkoutList(workouts);
            recyclerView.setAdapter(workoutAdapter);
            workoutList = workouts;
        });
        viewModel.getUser().observe(getViewLifecycleOwner(), user -> userNameTextView.setText(user.getUsername()+"."));
    }

    private void setUpRecView(View view) {
        viewModel = ViewModelProviders.of(this).get(MenuViewModel.class);
        viewModel.init();
        addWorkoutButton = view.findViewById(R.id.addWorkoutBotton);
        userNameTextView = view.findViewById(R.id.userNameWorkoutFrag);
        recyclerView = view.findViewById(R.id.myWorkoutsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        workoutAdapter = new WorkoutAdapter(getActivity(),this);
        recyclerView.setAdapter(workoutAdapter);
    }

    @Override
    public void onNoteClick(int poistion) {
        Intent intent= new Intent(getActivity(), ExercisesActivity.class);
        intent.putExtra("workoutId",workoutList.get(poistion).getId());
        intent.putExtra("workoutName",workoutList.get(poistion).getName());
        startActivity(intent);
    }

    @Override
    public void deleteNode(int position) {
        Toast.makeText(getActivity(),"Workout removed.",Toast.LENGTH_SHORT).show();
        String id = workoutList.get(position).getId();
        viewModel.deleteWorkout(id);
    }
}