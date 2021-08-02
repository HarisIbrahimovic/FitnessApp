package com.example.myfitness.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myfitness.Model.DietDay;
import com.example.myfitness.R;
import com.example.myfitness.ViewModel.MenuViewModel;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;

import java.util.ArrayList;
import java.util.List;


public class StatisticFragment extends Fragment   {
    private LineChart calorieChart;
    private LineChart proteinLineChart;
    private MenuViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_statistic, container, false);
        calorieChart = view.findViewById(R.id.lineChart);
        proteinLineChart = view.findViewById(R.id.proteinLineCHart);
        viewModel= ViewModelProviders.of(this).get(MenuViewModel.class);
        viewModel.init(getActivity().getApplication());
        viewModel.getListOfDays().observe(getViewLifecycleOwner(),dietDays->{
            setUpProteinData(dietDays);
            setUpChart(proteinLineChart);
            setUpCaloriesData(dietDays);
            setUpChart(calorieChart);
        });

        return view;
    }

    private void setUpProteinData(List<DietDay> dietDays ) {
        List<DietDay> days = dietDays;
        ArrayList<Entry> calorieEntries = new ArrayList<>();
        int i=1;
        for(DietDay dietDay: days){
            calorieEntries.add(new Entry(i,dietDay.getProtein()));
            i++;
        }
        LineDataSet set = new LineDataSet(calorieEntries,"Daily protein");
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        set.setFillAlpha(100);
        dataSets.add(set);
        LineData data = new LineData(dataSets);
        proteinLineChart.invalidate();
        if(proteinLineChart.getData()!=null)return;
        proteinLineChart.setData(data);
    }

    private void setUpCaloriesData(List<DietDay> dietDays ) {
        List<DietDay> days = dietDays;
        ArrayList<Entry> calorieEntries = new ArrayList<>();
        int i=1;
        for(DietDay dietDay: days){
            calorieEntries.add(new Entry(i,dietDay.getCalories()));
            i++;
        }
        LineDataSet set = new LineDataSet(calorieEntries,"Daily calories");
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        set.setFillAlpha(100);
        dataSets.add(set);
        LineData data = new LineData(dataSets);
        calorieChart.invalidate();
        if(calorieChart.getData()!=null)return;
        calorieChart.setData(data);
    }

    private void setUpChart(LineChart chart) {
        chart.setDragEnabled(true);
        chart.setScaleEnabled(false);
        chart.getLegend().setTextColor(getResources().getColor(R.color.white));
        chart.getAxisLeft().setTextColor(getResources().getColor(R.color.white));
        chart.getXAxis().setTextColor(getResources().getColor(R.color.white));
        chart.getAxisRight().setDrawLabels(false);
        chart.getLineData().setValueTextSize(0);
        chart.getDescription().setText("");
    }

}