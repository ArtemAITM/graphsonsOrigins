package com.example.opm;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class GraphicalFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_graphical, container, false);
    }
    public void updateText(String newText) {
        TextView textView = getView().findViewById(R.id.textView);
        textView.setText(newText);
    }
}