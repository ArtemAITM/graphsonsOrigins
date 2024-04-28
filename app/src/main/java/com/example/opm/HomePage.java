package com.example.opm;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.opm.databinding.ActivityHomePageBinding;

public class HomePage extends AppCompatActivity {
    ActivityHomePageBinding binding;
    String[] elements = new String[] {"Калькулятор", "Графический калькулятор",
            "Построение простейших круговых диаграм", "Скоро в приложении..."};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomePageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.calculator.setOnClickListener(v -> {
            FragmentManager fragmentManager = getSupportFragmentManager();
            @SuppressLint("CommitTransaction") FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
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
            Intent i = new Intent(this, PieCharts.class);
            startActivity(i);
        });
    }

}