package com.example.myfitness.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.myfitness.Fragments.NutritionFragment;
import com.example.myfitness.Fragments.StatisticFragment;
import com.example.myfitness.Fragments.WorkoutFragment;
import com.example.myfitness.R;
import com.example.myfitness.ViewModel.MenuViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MenuActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private Fragment selectedFragment;
    private MenuViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        setUpViews();
        onClicks();
        observe();

    }

    private void onClicks() {
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                selectedFragment = null;
                switch (item.getItemId()){
                    case R.id.nutrition:
                        selectedFragment = new NutritionFragment();
                        viewModel.setSelectedFragmentNum(1);
                        break;
                    case R.id.workout:
                        selectedFragment = new WorkoutFragment();
                        viewModel.setSelectedFragmentNum(2);
                        break;
                    case R.id.statistics:
                        viewModel.setSelectedFragmentNum(3);
                        selectedFragment = new StatisticFragment();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.menuFragmentFrame,selectedFragment).commit();
                return true;
            }
        });
    }

    private void observe() {
        viewModel.getSelectedFragmentNum().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                switch (integer){
                    case 1:
                        selectedFragment = new NutritionFragment();
                        break;
                    case 2:
                        selectedFragment = new WorkoutFragment();
                        break;
                    case 3:
                        selectedFragment = new StatisticFragment();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.menuFragmentFrame,selectedFragment).commit();
            }
        });
    }

    private void setUpViews() {
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setItemIconTintList(null);
        viewModel = ViewModelProviders.of(this).get(MenuViewModel.class);
        getWindow().setNavigationBarColor(getResources().getColor(R.color.myLightPurple));
        selectedFragment = new WorkoutFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.menuFragmentFrame,selectedFragment).commit();
    }

}