package com.example.opm;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.util.Log;
import com.example.opm.databinding.ActivityMainBinding;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.ValidationResult;
import java.util.ArrayList;
import java.util.List;
import android.os.Handler;


public class BuildMathFunction  {
    public static String[] names = new String[] {"первый", "второй", "третий"
            , "четвертый", "пятый", "шестой", "седьмой", "восьмой", "девятый", "десятый"};
    private static final int[] colors = new int[]{Color.RED, Color.GREEN, Color.MAGENTA, Color.BLACK, Color.BLUE
            ,Color.YELLOW, R.color.purple, R.color.brown, R.color.pink};
    private static final ArrayList<String> functions = new ArrayList<>();
    @SuppressLint("StaticFieldLeak")
    public static ActivityMainBinding binding;
    public static int COMPLETE = 0;
    public static LineData lineData;
    public static float CURRENT_X_PLUS = 0;
    public static float CURRENT_X_MINUS = 0;

    public static void connectChart(ArrayList<String> functionsList, ActivityMainBinding bind) throws InterruptedException {
        binding = bind;
        functions.addAll(functionsList);
        for (int i = 0; i < functions.size(); i++) {
            runThread(functions.get(i), i);
        }
        while (COMPLETE != functions.size()){
            Log.d("Threads", "Waiting all Threads complete their work");
        }
        Thread.sleep(1000);
        Log.d("Threads", "All threads complete their work");
    }

    private static void runThread(String function, int index) {
        new Thread() {
            public void run() {
                Log.d("Threads", "Thread " + index + " is working");
                ArrayList<Entry> entries = new ArrayList<>();
                for(float i = -10f; i <= 10f; i += 0.01f){
                    float y = calculateFunction(function, i);
                    entries.add(new Entry(i, y));
                }
                CURRENT_X_PLUS = 10f;
                CURRENT_X_MINUS = -10f;
                Log.d("Threads", "Thread " + index + " is add dataSet to dataSets");
                addPointsToChart(index, entries);
                COMPLETE++;
                startDrawing(function, index);
            }
        }.start();
    }
    public static void startDrawing(String function, int index){
        new Thread(){
            public void run(){
                float xMaxDraw = 10f;
                float xMinDraw = -10f;
                for (int i = 0; i < 10000; i++) {
                    expandDataSetMinus(function, index, xMaxDraw + 10 * i);
                    expandDataSetPlus(function, index, xMinDraw - 10 * i);
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }.start();
    }
    static Handler handler = new Handler();
    public static void addPointsToChart(int index, List<Entry> entries) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                LineDataSet dataSet = new LineDataSet(entries, "График " + names[index]);
                dataSet.setColor(colors[index]);

                if(lineData == null) {
                    lineData = new LineData(dataSet);
                } else {
                    lineData.addDataSet(dataSet);
                }
                binding.chart.setData(lineData);
                binding.chart.setVisibleXRangeMinimum(0);
                float maxX = entries.get(entries.size() - 1).getX();
                float xRange = 8F;
                binding.chart.setVisibleXRangeMaximum(Math.max(maxX, xRange));
                float minY = entries.get(0).getY();
                float maxY = entries.get(0).getY();
                for (Entry entry : entries) {
                    if (entry.getY() < minY) {
                        minY = entry.getY();
                    }
                    if (entry.getY() > maxY) {
                        maxY = entry.getY();
                    }
                }
                binding.chart.setVisibleYRangeMinimum(minY, YAxis.AxisDependency.LEFT);
                binding.chart.setVisibleYRangeMaximum(maxY, YAxis.AxisDependency.LEFT);
                binding.chart.notifyDataSetChanged();
                binding.chart.getAxisRight().setDrawLabels(false);
                binding.chart.invalidate();

            }
        });
    }

    private static float calculateFunction(String function, float x) {
        try {
            Expression e = new ExpressionBuilder(function)
                    .variables("x")
                    .build()
                    .setVariable("x", x);

            ValidationResult validationResult = e.validate();

            if (!validationResult.isValid()) {
                throw new IllegalArgumentException("Неверное выражение: " + validationResult.getErrors());
            }

            return (float) e.evaluate();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Float.NaN;
        }
    }

    public static void expandDataSetMinus(String function, int index, float DRAWX) {
        ArrayList<Entry> expandedEntries = new ArrayList<>();
        for (float x = DRAWX; x >= DRAWX-10; x -= 0.001f) {
            float y = calculateFunction(function, x);
            expandedEntries.add(new Entry(x, y));
        }
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        finally {
            addPointsToChart(index, expandedEntries);
        }
    }
    public static void expandDataSetPlus(String function, int index, float DRAWX) {
        ArrayList<Entry> expandedEntries = new ArrayList<>();
        for (float x = DRAWX; x <= DRAWX+10; x += 0.001f) {
            float y = calculateFunction(function, x);
            expandedEntries.add(new Entry(x, y));
        }
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        finally {
            addPointsToChart(index, expandedEntries);
        }
    }
}