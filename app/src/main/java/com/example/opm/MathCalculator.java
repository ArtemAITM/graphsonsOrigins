package com.example.opm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.opm.databinding.ActivityMathCalculatorBinding;

public class MathCalculator extends AppCompatActivity {
    private ActivityMathCalculatorBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMathCalculatorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}