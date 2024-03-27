package com.example.opm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.opm.databinding.ActivityHomePageBinding;

import java.util.ArrayList;

public class HomePage extends AppCompatActivity {
    ActivityHomePageBinding binding;
    private View view;
    String[] elements = new String[] {"Калькулятор", "Графический калькулятор",
            "Решение уравнений", "Скоро в приложении..."};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomePageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.calculator.setOnClickListener(v -> {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            for(int i = 0; i < 4; ++i){
                GraphicalFragment fragment = new GraphicalFragment();
                fragment.updateText(elements[i]);
                fragmentTransaction.add(fragment, "/StartPos");
            }
        });
        binding.mathFunctions.setOnClickListener(v -> {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        });
        binding.uravnenia.setOnClickListener(v -> {
            Intent i = new Intent(this, MathCalculator.class);
            startActivity(i);
        });
    }

}