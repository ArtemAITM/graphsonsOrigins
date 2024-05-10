package com.example.opm;

import static com.example.opm.XLSXDiagram.BAR_ENTRIES;
import static com.example.opm.XLSXDiagram.PIE_ENTRIES;

import android.graphics.Color;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.opm.databinding.ActivityXlsxdiagramBinding;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;

public class DrawingChartsXLSX {
    private final ActivityXlsxdiagramBinding binding;
    public DrawingChartsXLSX(ActivityXlsxdiagramBinding bind){
        this.binding = bind;
    }
    public void DRAW(){
        CheckTypeDiagram();
        System.out.println("Отправлено на проверку");
    }
    private void CheckTypeDiagram(){
        if (binding.pieChart.getVisibility() == View.VISIBLE){
            drawPieDiagram();
        } else if (binding.barChart.getVisibility() == View.VISIBLE) {
            drawBarDiagram();
        }
    }
    private void drawPieDiagram(){
        for (int i = 0; i < XLSXDiagram.values.size(); i++) {
            PIE_ENTRIES.add(new PieEntry(Float.parseFloat(XLSXDiagram.values.get(i)), XLSXDiagram.keys.get(i)));
        }
        PieDataSet dataSet = getPieDataSet();
        PieData data = new PieData(dataSet);
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.BLACK);
        binding.pieChart.setHoleRadius(40);
        binding.pieChart.setData(data);
        binding.pieChart.invalidate();
    }
    private void drawBarDiagram(){
        for (int i = 0; i < XLSXDiagram.values.size(); i++) {
            BAR_ENTRIES.add(new BarEntry(Float.parseFloat(XLSXDiagram.values.get(i)), Float.parseFloat(XLSXDiagram.keys.get(i))));
        }
        BarData barData = new BarData(getBarDataSet());
        binding.barChart.setData(barData);
    }

    private BarDataSet getBarDataSet() {
        return new BarDataSet(BAR_ENTRIES, null);
    }

    @NonNull
    private PieDataSet getPieDataSet() {
        PieDataSet dataSet = new PieDataSet(PIE_ENTRIES, null);
        dataSet.setSliceSpace(1f);
        dataSet.setIconsOffset(new MPPointF(0, 10));
        dataSet.setSelectionShift(6f);
        dataSet.setValueLinePart1OffsetPercentage(100f);
        dataSet.setValueLinePart1Length(0.6f);
        dataSet.setValueLinePart2Length(0.6f);
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        return dataSet;
    }
}
