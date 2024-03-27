package com.example.opm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.example.opm.databinding.ActivityMathCalculatorBinding;

import java.util.ArrayList;

public class MathCalculator extends AppCompatActivity {
    private ActivityMathCalculatorBinding binding; //test comment 2
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMathCalculatorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

}