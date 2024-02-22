package com.example.opm;

import androidx.appcompat.app.AppCompatActivity;

import android.accounts.AbstractAccountAuthenticator;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        nullChart();
        binding.draw.setOnClickListener(v -> {
            nullChart();
            String function = binding.enterfunction.getText().toString();
            if (function.length() > 10) {
                Toast.makeText(this, "Недоступно в вашей стране)", Toast.LENGTH_SHORT).show();
            } else {
                DoubleEvaluator evaluator = new DoubleEvaluator();
                ArrayList<Entry> entriesFirst = new ArrayList<>();
                for (double x = -1000.0; x <= 1000.0; x += 0.1) {
                    StaticVariableSet<Double> variables = new StaticVariableSet<Double>();
                    variables.set("x", x);
                    double y = evaluator.evaluate(function, variables);
                    entriesFirst.add(new Entry((float) x, (float) y));
                }
                LineDataSet datasetFirst = new LineDataSet(entriesFirst, "График первый");
                ArrayList<ILineDataSet> dataSets = new ArrayList();
                dataSets.add(datasetFirst);
                datasetFirst.setDrawCircles(false);
                datasetFirst.setDrawValues(false);
                datasetFirst.setMode(LineDataSet.Mode.LINEAR);
                LineData lineData = new LineData(dataSets);
                binding.chart.setData(lineData);

                binding.chart.invalidate();
            }
        });
    }
    private void nullChart(){
        ArrayList<Entry> entriesempty = new ArrayList<>();
        LineDataSet datasetEmpty = new LineDataSet(entriesempty, "График первый");
        dataSetsEmpty.add(datasetEmpty);
        LineData lineData = new LineData(dataSetsEmpty);
        binding.chart.setData(lineData);
        binding.chart.invalidate();
    }
}