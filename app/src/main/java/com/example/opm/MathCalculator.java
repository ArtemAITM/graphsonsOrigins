package com.example.opm;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.opm.databinding.ActivityMathCalculatorBinding;

public class MathCalculator extends Activity {//guy
    private ActivityMathCalculatorBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMathCalculatorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}
