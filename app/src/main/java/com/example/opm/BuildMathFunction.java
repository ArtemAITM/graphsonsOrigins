package com.example.opm;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import com.example.opm.databinding.ActivityMainBinding;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.ValidationResult;
import java.util.ArrayList;
import java.util.List;
import android.os.Handler;
import android.view.MotionEvent;
import android.widget.Toast;


public class BuildMathFunction {
    public static String[] names = new String[]{"первый", "второй", "третий"
            , "четвертый", "пятый", "шестой", "седьмой", "восьмой", "девятый", "десятый"};
    private static final int[] colors = new int[]{Color.RED, Color.GREEN, Color.MAGENTA, Color.BLACK, Color.BLUE
            , Color.YELLOW, R.color.purple, R.color.brown, R.color.pink};
    private static final ArrayList<String> functions = new ArrayList<>();
    private Context context;
    @SuppressLint("StaticFieldLeak")
    public static ActivityMainBinding binding;
    public static int COMPLETE = 0;
    public static LineData lineData;

    public BuildMathFunction(Context context) {
        this.context = context;
    }

    public void connectChart(ArrayList<String> functionsList, ActivityMainBinding bind) throws InterruptedException {
        binding = bind;
        functions.addAll(functionsList);
        for (int i = 0; i < functions.size(); i++) {
            runThread(functions.get(i), i);
        }
        while (COMPLETE != functions.size()) {
            Log.d("Threads", "Waiting all Threads complete their work");
        }
        Thread.sleep(1000);
        Log.d("Threads", "All threads complete their work");
    }

    private void runThread(String function, int index) {
        new Thread() {
            public void run() {
                Log.d("Threads", "Thread " + index + " is working");
                ArrayList<Entry> entries = new ArrayList<>();
                for (float i = -10f; i <= 10f; i += 0.001f) {
                    float y = calculateFunction(function, i);
                    Entry entry = new Entry(i, y);
                    entries.add(entry);
                }
                Log.d("Threads", "Thread " + index + " is add dataSet to dataSets");
                settingsChart();
                addPointsToChart(index, entries, true);
                COMPLETE++;
                startDrawing(function, index);

            }
        }.start();
    }

    public void settingsChart() {
        XAxis xAxis = binding.chart.getXAxis();
        xAxis.setDrawLabels(true);
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(true);
        xAxis.setAxisLineWidth(2f);
        xAxis.setGridLineWidth(1.5f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisLineColor(Color.BLACK);
        YAxis yAxis = binding.chart.getAxisLeft();
        yAxis.setDrawLabels(true);
        yAxis.setDrawAxisLine(true);
        yAxis.setDrawGridLines(true);
        yAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        yAxis.setAxisLineWidth(2f);
        yAxis.setGridLineWidth(1.5f);
        yAxis.setAxisLineColor(Color.BLACK);
        YAxis rightYAxis = binding.chart.getAxisRight();
        rightYAxis.setEnabled(false);
        binding.chart.getLegend().setEnabled(false);
        binding.chart.setDescription(null);
        binding.chart.invalidate();
    }

    public void startDrawing(String function, int index) {
        new Thread() {
            public void run() {
                binding.chart.setOnChartGestureListener(new OnChartGestureListener() {
                    @Override
                    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
                        higlightTouch(me);
                    }

                    @Override
                    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
                    }

                    @Override
                    public void onChartLongPressed(MotionEvent me) {

                    }

                    @Override
                    public void onChartDoubleTapped(MotionEvent me) {

                    }

                    @Override
                    public void onChartSingleTapped(MotionEvent me) {

                    }

                    @Override
                    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {

                    }

                    @Override
                    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
                        Toast.makeText(context, "Ага", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onChartTranslate(MotionEvent me, float dX, float dY) {

                    }
                });
                for (int i = 0; i < 5; i++) {
                    expandDataSetMinus(function, index, -20f - 10 * i);
                    expandDataSetPlus(function, index, 10f + i*10f);
                }
            }
        }.start();
    }

    private void higlightTouch(MotionEvent event) {
        Highlight highlight = binding.chart.getHighlightByTouchPoint(event.getX(), event.getY());

        if (highlight != null) {
            Entry entry = binding.chart.getEntryByTouchPoint(event.getX(), event.getY());
            if (entry != null) {
                binding.chart.highlightValue(highlight);
                float x = entry.getX();
                float y = entry.getY();
                Log.d("ChartTouch", "Нажатие. X: " + x + ", Y: " + y);
                CustomMarkerView markerView = new CustomMarkerView(context, R.layout.custom_marker_view_layout);
                markerView.setChartView(binding.chart);
                binding.chart.setMarker(markerView);
            }
        }
    }

    static Handler handler = new Handler();

    public void addPointsToChart(int index, List<Entry> entries, boolean HideEntries) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                LineDataSet dataSet = new LineDataSet(entries, "График " + names[index]);
                dataSet.setColor(colors[index]);
                dataSet.setLineWidth(2f);
                if (HideEntries) {
                    dataSet.setDrawCircleHole(false);
                    dataSet.setDrawValues(false);
                    dataSet.setCircleColor(Color.TRANSPARENT);
                    dataSet.setCircleRadius(0);
                }
                if (lineData == null) {
                    lineData = new LineData(dataSet);
                } else {
                    lineData.addDataSet(dataSet);
                }
                binding.chart.setData(lineData);
                binding.chart.setVisibleXRangeMinimum(0f);
                binding.chart.setVisibleXRangeMaximum(8f);
                binding.chart.setVisibleYRangeMinimum(0f, YAxis.AxisDependency.LEFT);
                binding.chart.setVisibleYRangeMaximum(8f, YAxis.AxisDependency.LEFT);
                binding.chart.notifyDataSetChanged();
                binding.chart.centerViewTo(0, 0, YAxis.AxisDependency.LEFT);
                binding.chart.getAxisRight().setDrawLabels(false);
                binding.chart.invalidate();

            }
        });
    }

    private float calculateFunction(String function, float x) {
        try {
            Expression e = new ExpressionBuilder(function)
                    .variables("x")
                    .build()
                    .setVariable("x", x);

            ValidationResult validationResult = e.validate();

            if (!validationResult.isValid()) {
                throw new IllegalArgumentException("Неверное выражение: " + validationResult.getErrors());
            }
            System.out.println(x + ", " + e.evaluate());
            return (float) e.evaluate();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Float.NaN;
        }
    }
    public void expandDataSetMinus(String function, int index, float DRAWX) {
        new Thread(){
            @Override
            public void run() {
                ArrayList<Entry> expandedEntries = new ArrayList<>();
                for (float x = DRAWX; x <= DRAWX+10; x += 0.001f) {
                    float y = calculateFunction(function, x);
                    expandedEntries.add(new Entry(x, y));
                    System.out.println(x + " " + y);
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                finally {
                    addPointsToChart(index, expandedEntries, true);
                }
            }
        }.start();
    }

    public void expandDataSetPlus(String function, int index, float DRAWX) {
        new Thread(){
            @Override
            public void run() {
                ArrayList<Entry> expandedEntries = new ArrayList<>();
                for (float x = DRAWX; x <= DRAWX+10; x += 0.001f) {
                    float y = calculateFunction(function, x);
                    expandedEntries.add(new Entry(x, y));
                    System.out.println(x + " " + y);
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                finally {
                    addPointsToChart(index, expandedEntries, true);
                }
            }
        }.start();
    }
}

//public class BuildMathFunction extends MainActivity{
//    @SuppressLint("StaticFieldLeak")
//    public static ActivityMainBinding binding;
//        public static String[] names = new String[] {"первый", "второй", "третий"
//            , "четвертый", "пятый", "шестой", "седьмой", "восьмой", "девятый", "десятый"};
//    private static final int[] colors = new int[]{Color.RED, Color.GREEN, Color.MAGENTA, Color.BLACK, Color.BLUE
//            ,Color.YELLOW, R.color.purple, R.color.brown, R.color.pink};
//    private static final ArrayList<String> functions = new ArrayList<>();
//    public static int COMPLETE = 0;
//    public static LineData lineData;
//    public static float CURRENT_X_PLUS = 0;
//    public static float CURRENT_X_MINUS = 0;
//    public static ArrayList<String> FunctionList = new ArrayList<>();
//    public static void connectChart(ArrayList<String> functions, ActivityMainBinding bind) {
//        binding = bind;
//        FunctionList.addAll(functions);
//        for (int i = 0; i < FunctionList.size(); i++) {
//            runThread(FunctionList.get(i), i);
//        }
//
//    }
//        private static void runThread(String function, int index) {
//        new Thread() {
//            public void run() {
//                Log.d("Threads", "Thread " + index + " is working");
//                ArrayList<Entry> entries = new ArrayList<>();
//                for(float i = -10f; i <= 10f; i += 0.01f){
//                    float y = calculateFunction(function, i);
//                    entries.add(new Entry(i, y));
//                }
//                CURRENT_X_PLUS = 10f;
//                CURRENT_X_MINUS = -10f;
//                addPointsToChart(index, entries);
//                binding.chart.setOnDragListener((v, event) -> {
//                    float Xmin = binding.chart.getLowestVisibleX();
//                    float Xmax = binding.chart.getHighestVisibleX();
//                    //float Ymax = binding.chart.getAxisLeft().mAxisMaximum;
//                    //float Ymin = binding.chart.getAxisLeft().mAxisMinimum;
//                    newEntries(Xmin, Xmax, index, function);
//                    return true;
//                });
//
//            }
//        }.start();
//    }
//    private static float calculateFunction(String function, float x) {
//        try {
//            Expression e = new ExpressionBuilder(function)
//                    .variables("x")
//                    .build()
//                    .setVariable("x", x);
//        ValidationResult validationResult = e.validate();
//        if (!validationResult.isValid()) {
//            throw new IllegalArgumentException("Неверное выражение: " + validationResult.getErrors());
//        }
//        return (float) e.evaluate();
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            return Float.NaN;
//        }
//    }
//    private static void newEntries(float xMin, float xMax, int index, String func){
//        ArrayList<Entry> entries = new ArrayList<>();
//        for (float i = xMin; i <= xMax; i += 0.001f) {
//            float y = calculateFunction(func, i);
//            entries.add(new Entry(i, y));
//        }
//        addPointsToChart(index, entries);
//    }
//    static Handler handler = new Handler();
//    public static void addPointsToChart(int index, List<Entry> entries) {
//        handler.post(new Runnable() {
//            @Override
//            public void run() {
//                LineDataSet dataSet = new LineDataSet(entries, "График " + names[index]);
//                dataSet.setColor(colors[index]);
//
//                if(lineData == null) {
//                    lineData = new LineData(dataSet);
//                } else {
//                    lineData.addDataSet(dataSet);
//                }
//                binding.chart.setData(lineData);
//                binding.chart.setVisibleXRangeMinimum(0);
//                binding.chart.setVisibleXRangeMaximum(8f);
//                binding.chart.setVisibleXRangeMinimum(0f);
//                binding.chart.setVisibleYRangeMinimum(0f, YAxis.AxisDependency.LEFT);
//                binding.chart.setVisibleYRangeMaximum(8f, YAxis.AxisDependency.LEFT);
//                binding.chart.notifyDataSetChanged();
//                binding.chart.getAxisRight().setDrawLabels(false);
//                float Xmin = binding.chart.getLowestVisibleX();
//                float Xmax = binding.chart.getHighestVisibleX();
//                float Ymax = binding.chart.getAxisLeft().mAxisMaximum;
//                float Ymin = binding.chart.getAxisLeft().mAxisMinimum;
//                binding.chart.centerViewTo((Xmin + Xmax) / 2, (Ymin + Ymax) / 2, YAxis.AxisDependency.LEFT);
//                binding.chart.invalidate();
//            }
//        });
//    }
//}