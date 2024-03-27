package com.example.opm;
import static com.github.mikephil.charting.components.YAxis.AxisDependency.LEFT;
import android.graphics.Color;
import com.example.opm.databinding.ActivityMainBinding;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.ValidationResult;
import java.util.ArrayList;
import java.util.HashMap;

public class BuildMathFunction {
    public static String[] names = new String[] {"первый", "второй", "третий"
            , "четвертый", "пятый", "шестой", "седьмой", "восьмой", "девятый", "десятый"};
    private static int[] colors = new int[]{Color.RED, Color.GREEN, Color.MAGENTA, Color.BLACK, Color.BLUE
            ,Color.YELLOW, R.color.purple, R.color.brown, R.color.pink};

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

            // Вычисляем значение функции для данного x
            return (float) e.evaluate();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Float.NaN;
        }
    }

    private static final ArrayList<Entry> allEntries = new ArrayList<>();
    static void updateChart(ActivityMainBinding binding, String function, int index) {
        allEntries.clear();
        float currentX = -10f;
        {

            float step = 0.01f;
            for (float x = Math.max(currentX + step, -10); x <= 100; x += step) {
                float y = calculateFunction(function, x);
                allEntries.add(new Entry(x, y));
            }
        }
        if (currentX <= 100 || currentX >= -10000) {
            float step = 0.01f;
            for (float x = Math.max(currentX + step, -10000); x <= 100; x += step) {
                float y = calculateFunction(function, x);
                allEntries.add(new Entry(x, y));
            }
            binding.chart.centerViewTo(0, 0, LEFT);
        }
        LineDataSet dataSet = new LineDataSet(allEntries, "График " + names[index]);
        dataSet.setColor(colors[index]);
        LineData lineData = new LineData(dataSet);
        binding.chart.setData(lineData);
        binding.chart.setVisibleXRangeMinimum(0);
        binding.chart.setVisibleXRangeMaximum(8F);
        binding.chart.setVisibleYRangeMinimum(0f, LEFT);
        binding.chart.setVisibleYRangeMaximum(8f, LEFT);
        binding.chart.getVisibility();
        binding.chart.centerViewTo(0, 0, LEFT);
        binding.chart.invalidate();
    }

}