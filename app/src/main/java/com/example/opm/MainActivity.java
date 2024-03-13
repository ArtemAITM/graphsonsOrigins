package com.example.opm;

import androidx.appcompat.app.AppCompatActivity;

import android.accounts.AbstractAccountAuthenticator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.example.opm.databinding.ActivityMainBinding;
import com.fathzer.soft.javaluator.DoubleEvaluator;
import com.fathzer.soft.javaluator.StaticVariableSet;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Entry> entriesempty = new ArrayList<>();
    private LineDataSet datasetEmpty = new LineDataSet(entriesempty, "График первый");
    private ActivityMainBinding binding;
    private ArrayList<ILineDataSet> dataSetsEmpty = new ArrayList();
    private BuildMathFuntction funtction = new BuildMathFuntction();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        funtction.nullChart(binding);
        binding.draw.setOnClickListener(v -> {
            try {
                funtction.draw(binding);
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            }
        });
    }

}