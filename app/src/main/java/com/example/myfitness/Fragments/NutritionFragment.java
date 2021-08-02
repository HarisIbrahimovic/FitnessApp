package com.example.myfitness.Fragments;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myfitness.Adapters.DaysAdapter;
import com.example.myfitness.Model.DietDay;
import com.example.myfitness.R;
import com.example.myfitness.View.ExercisesActivity;
import com.example.myfitness.ViewModel.MenuViewModel;

import java.util.List;


public class NutritionFragment extends Fragment implements DaysAdapter.TouchListener{

    private Button addNewDayButton;
    private RecyclerView recyclerView;
    private DaysAdapter adapter;
    private View myView;
    private AlertDialog dialog;
    private Button createDayButton;
    private EditText proteinEditText;
    private EditText carbsEditText;
    private EditText fatsEditText;
    private MenuViewModel viewModel;
    private EditText uProteinEditText;
    private EditText uCarbsEditText;
    private EditText uFatsEditText;
    private Button updateButton;
    private Button deleteButton;
    private DietDay dietDay;
    private List<DietDay> dietDays;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nutrition, container, false);
        setUpStuff(view);
        observe();
        onClicks();
        return view;
    }

    private void onClicks() {
        addNewDayButton.setOnClickListener(v-> {
            openFragment();
            setUpViews();
            setOnClicksMyView();
        });
    }

    private void observe() {
        viewModel.getToastMessage().observe(getViewLifecycleOwner(), s->{
            Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
        });
        viewModel.getCreatedState().observe(getViewLifecycleOwner(),bool->{
            dialog.dismiss();
        });
        viewModel.getListOfDays().observe(getViewLifecycleOwner(),days->{
            adapter.setDietDayList(days);
            dietDays = days;
        });
    }

    private void setUpStuff(View view) {
        viewModel = ViewModelProviders.of(this).get(MenuViewModel.class);
        viewModel.init(getActivity().getApplication());
        addNewDayButton = view.findViewById(R.id.addNewDay);
        recyclerView = view.findViewById(R.id.myDaysRecView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new DaysAdapter(getActivity(),this);
        recyclerView.setAdapter(adapter);
    }

    private void setOnClicksMyView() {
        createDayButton.setOnClickListener(v->{
            viewModel.addDay(proteinEditText.getText().toString(),carbsEditText.getText().toString(),fatsEditText.getText().toString(),getActivity().getApplication());
        });
    }

    private void setUpViews() {
        createDayButton = myView.findViewById(R.id.createDayButton);
        proteinEditText = myView.findViewById(R.id.editTextProtein);
        fatsEditText = myView.findViewById(R.id.editTextFats);
        carbsEditText = myView.findViewById(R.id.editTextCarbs);
    }

    private void openFragment() {
        AlertDialog.Builder myDialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        myView = inflater.inflate(R.layout.add_diet_day,null);
        myDialog.setView(myView);
        dialog = myDialog.create();
        dialog.show();
    }

    @Override
    public void onNoteClick(int position) {
        dietDay = dietDays.get(position);
        openUpdateFragment();
        setUpViewsUpdate();
        setOnClickUpdate();
    }

    private void setOnClickUpdate() {
        updateButton.setOnClickListener(v->{
            viewModel.updateDay(dietDay.getId(),uProteinEditText.getText().toString(),uCarbsEditText.getText().toString(),uFatsEditText.getText().toString(),getActivity().getApplication());
        });
        deleteButton.setOnClickListener(v->{
            viewModel.deleteDay(dietDay.getId(),getActivity().getApplication());
        });
    }

    private void setUpViewsUpdate() {
        uProteinEditText = myView.findViewById(R.id.editTextProteinUpdate);
        uFatsEditText = myView.findViewById(R.id.editTextFatsUpdate);
        uCarbsEditText = myView.findViewById(R.id.editTextCarbsUpdate);
        updateButton = myView.findViewById(R.id.updateButtonDay);
        deleteButton = myView.findViewById(R.id.deleteButtonDay);
        uProteinEditText.setText(dietDay.getProtein()+"");
        uFatsEditText.setText(dietDay.getFats()+"");
        uCarbsEditText.setText(dietDay.getCarbs()+"");
    }

    private void openUpdateFragment() {
        AlertDialog.Builder myDialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        myView = inflater.inflate(R.layout.update_day,null);
        myDialog.setView(myView);
        dialog = myDialog.create();
        dialog.show();
    }
}