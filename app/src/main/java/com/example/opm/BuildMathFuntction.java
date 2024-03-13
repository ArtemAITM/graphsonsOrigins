package com.example.opm;

import android.graphics.Color;

import com.example.opm.databinding.ActivityMainBinding;
import com.fathzer.soft.javaluator.DoubleEvaluator;
import com.fathzer.soft.javaluator.StaticVariableSet;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.HashMap;

public class BuildMathFuntction {
    private HashMap<String, LineDataSet> dataSetMap = new HashMap<>();
    private ArrayList<ILineDataSet> dataSets = new ArrayList<>();
    private String[] names = new String[] {"первый", "второй", "третий"
            , "четвертый", "пятый", "шестой", "седьмой", "восьмой", "девятый", "десятый"};
    private int[] colors = new int[]{Color.RED, Color.GREEN, Color.MAGENTA, Color.BLACK, Color.BLUE
            ,Color.YELLOW, R.color.purple, R.color.brown, R.color.pink};

    public void draw(String function, int i) {
        DoubleEvaluator evaluator = new DoubleEvaluator();
        ArrayList<Entry> entriesFirst = new ArrayList<>();
        for (double x = -1000.0; x <= 1000.0; x += 0.1) {
            StaticVariableSet<Double> variables = new StaticVariableSet<Double>();
            variables.set("x", x);
            double y = evaluator.evaluate(function, variables);
            entriesFirst.add(new Entry((float) x, (float) y));
        }
        LineDataSet datasetFirst = new LineDataSet(entriesFirst, "График " + names[i]);
        datasetFirst.setDrawCircles(false);
        datasetFirst.setDrawValues(false);
        datasetFirst.setMode(LineDataSet.Mode.LINEAR);
        datasetFirst.setColor(colors[i]);
        connect_to_dataset(datasetFirst);
    }
    public void connect_to_dataset(LineDataSet datasetFirst){
        dataSets.add(datasetFirst);
    }
    public void draw_all(ActivityMainBinding binding){
        LineData lineData = new LineData(dataSets);
        binding.chart.setData(lineData);
        binding.chart.setVisibleXRangeMinimum(0); // Минимальное количество видимых значений по оси X
        binding.chart.setVisibleXRangeMaximum(8F); // Максимальное количество видимых значений по оси X
        binding.chart.setVisibleYRangeMinimum(0f, YAxis.AxisDependency.LEFT); // Минимальное значение по оси Y
        binding.chart.setVisibleYRangeMaximum(8f, YAxis.AxisDependency.LEFT); // Максимальное значение по оси Y
        binding.chart.centerViewTo(0, 0, YAxis.AxisDependency.LEFT);
        binding.chart.invalidate();
    }

//    public void addFunction(String functionName, String function, ActivityMainBinding binding) {
//        dataSetMap.put(functionName, dataSet);
//        dataSets.add(dataSet);
//    }
//
//    public void draw(ActivityMainBinding binding, ArrayList<String> notesList) throws InstantiationException {
//        ArrayList<ILineDataSet> dataSets = new ArrayList();
//        nullChart(binding);
//        String function = binding.enterfunction.getText().toString();
//        LineData lineData = new LineData(dataSets);
//    }
//
//    protected void nullChart(ActivityMainBinding binding) {
//        dataSets.clear();
//        dataSetMap.clear();
//
//        ArrayList<Entry> entriesempty = new ArrayList<>();
//        LineDataSet datasetEmpty = new LineDataSet(entriesempty, "График первый");
//        dataSets.add(datasetEmpty);
//
//        LineData lineData = new LineData(dataSets);
//        binding.chart.setData(lineData);
//        binding.chart.invalidate();
//    }
//    protected void draw(ActivityMainBinding binding, LineData lineData){
//        binding.chart.setData(lineData);
//        binding.chart.setVisibleXRangeMinimum(0); // Минимальное количество видимых значений по оси X
//        binding.chart.setVisibleXRangeMaximum(8F); // Максимальное количество видимых значений по оси X
//        binding.chart.setVisibleYRangeMinimum(0f, YAxis.AxisDependency.LEFT); // Минимальное значение по оси Y
//        binding.chart.setVisibleYRangeMaximum(8f, YAxis.AxisDependency.LEFT); // Максимальное значение по оси Y
//        binding.chart.centerViewTo(0, 0, YAxis.AxisDependency.LEFT);
//        binding.chart.invalidate();
//    }
//    protected void add_dataset(String function, int i){
//        try {
//            if (function.length() > 10) {
//                Toast.makeText(MainActivity.class.newInstance(), "Недоступно в вашей стране)", Toast.LENGTH_SHORT).show();
//            } else {
//                DoubleEvaluator evaluator = new DoubleEvaluator();
//                ArrayList<Entry> entriesFirst = new ArrayList<>();
//                for (double x = -1000.0; x <= 1000.0; x += 0.1) {
//                    StaticVariableSet<Double> variables = new StaticVariableSet<Double>();
//                    variables.set("x", x);
//                    double y = evaluator.evaluate(function, variables);
//                    entriesFirst.add(new Entry((float) x, (float) y));
//                }
//                LineDataSet datasetFirst = new LineDataSet(entriesFirst, "График " + names[i]);
//                dataSets.add(datasetFirst);
//                datasetFirst.setDrawCircles(false);
//                datasetFirst.setDrawValues(false);
//                datasetFirst.setMode(LineDataSet.Mode.LINEAR);
//                datasetFirst.setColor(colors[i]);
//            }
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            throw new RuntimeException(e);
//        }
//    }
}