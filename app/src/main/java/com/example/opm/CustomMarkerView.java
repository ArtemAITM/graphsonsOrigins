package com.example.opm;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

import java.text.DecimalFormat;
import java.util.ConcurrentModificationException;

@SuppressLint("ViewConstructor")
public class CustomMarkerView extends MarkerView {

    private final TextView textView;

    public CustomMarkerView(Context context, int layoutResource) {
        super(context, layoutResource);
        textView = findViewById(R.id.textView);
    }
    @SuppressLint("SetTextI18n")
    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        float x = e.getX();
        float y = e.getY();
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        textView.setText("(" + decimalFormat.format(x) + ", " + decimalFormat.format(y) + ")");
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(0, getHeight());
    }
}

