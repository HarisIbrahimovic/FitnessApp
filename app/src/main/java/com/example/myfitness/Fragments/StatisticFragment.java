package com.example.myfitness.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

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
        viewModel.init();
        viewModel.getProteinData().observe(getViewLifecycleOwner(),proteinData->{
            proteinLineChart.setData(proteinData);
            setUpChart(proteinLineChart);
        });
        viewModel.getCalorieData().observe(getViewLifecycleOwner(),calorieData->{
           calorieChart.setData(calorieData);
            setUpChart(calorieChart);
        });











        return view;
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