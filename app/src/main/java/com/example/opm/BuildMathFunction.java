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
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BuildMathFunction {
    private HashMap<String, LineDataSet> dataSetMap = new HashMap<>();
    private ArrayList<ILineDataSet> dataSets = new ArrayList<>();
    private String[] names = new String[] {"первый", "второй", "третий"
            , "четвертый", "пятый", "шестой", "седьмой", "восьмой", "девятый", "десятый"};
    private int[] colors = new int[]{Color.RED, Color.GREEN, Color.MAGENTA, Color.BLACK, Color.BLUE
            ,Color.YELLOW, R.color.purple, R.color.brown, R.color.pink};
//fdbdsgbsdgbsdfgsd
    public void draw(String function, int i) {
        DoubleEvaluator evaluator = new DoubleEvaluator();
        Map<Double, Double> cache = new HashMap<>(); // Кеш для хранения рассчитанных значений
        ArrayList<Entry> entries = new ArrayList<>();

        StaticVariableSet<Double> variables = new StaticVariableSet<Double>();

        for (double x = -1000.0; x <= 1000.0; x += 0.1) {
            double y;
            if (cache.containsKey(x)) {
                y = cache.get(x); // Если значение уже рассчитано, берем его из кеша
            } else {
                variables.set("x", x);
                y = evaluator.evaluate(function, variables);
                cache.put(x, y); // Сохраняем рассчитанное значение в кеш
            }

            entries.add(new Entry((float) x, (float) y));
        }

        LineDataSet dataset = new LineDataSet(entries, "График " + names[i]);
        dataset.setDrawCircles(false);
        dataset.setDrawValues(false);
        dataset.setMode(LineDataSet.Mode.LINEAR);
        dataset.setColor(colors[i]);
        dataSets.add(dataset);
    }

    public void draw_all(ActivityMainBinding binding){
        LineData lineData = new LineData(dataSets);
        binding.chart.setData(lineData);
        binding.chart.setVisibleXRangeMinimum(0);
        binding.chart.setVisibleXRangeMaximum(8F);
        binding.chart.setVisibleYRangeMinimum(0f, YAxis.AxisDependency.LEFT);
        binding.chart.setVisibleYRangeMaximum(8f, YAxis.AxisDependency.LEFT);
        binding.chart.centerViewTo(0, 0, YAxis.AxisDependency.LEFT);
        binding.chart.invalidate();
    }

}