package com.example.opm;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.opm.databinding.ActivityMainBinding;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ArrayList<String> items;
    private ArrayAdapter<String> adapter;
    private ActivityMainBinding binding;
    private boolean VISIBILITY = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        createKeyboard();

        binding.inputEditText.setShowSoftInputOnFocus(false);
        items = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        binding.resultListView.setAdapter(adapter);

        binding.drawAll.setOnClickListener(v -> {
            BuildMathFunction buildMathFunction = new BuildMathFunction(binding.chart);
            buildMathFunction.plotFunction(items);
        });
        binding.openKeyboard.setOnClickListener(v -> {
            if (VISIBILITY) {
                VISIBILITY = false;
                binding.WORKBoard.setVisibility(View.GONE);
                binding.WORKBoard.setBackgroundColor(Color.TRANSPARENT);
            } else {
                VISIBILITY = true;
                binding.WORKBoard.setVisibility(View.VISIBLE);
                binding.WORKBoard.setBackgroundColor(Color.GRAY);
            }
        });
        binding.submitButton.setOnClickListener(v -> {
            String text = binding.inputEditText.getText().toString();
            if (!text.isEmpty()) {
                items.add(text);
            }
            binding.inputEditText.setText("");
            adapter.notifyDataSetChanged();
        });
        binding.resultListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = items.get(position);
                Toast.makeText(MainActivity.this, "Выбран элемент: " + selectedItem, Toast.LENGTH_SHORT).show();
            }
        });
        binding.resultListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("Удалить элемент?");
                dialog.setPositiveButton("ДА", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        items.remove(position);
                        adapter.notifyDataSetChanged();
                    }
                });
                dialog.setNegativeButton("НЕТ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                dialog.show();
                return false;
            }
        });
    }

    private void createKeyboard() {
        CustomKeyboard keyboard = new CustomKeyboard(this);
        keyboard.setupKeys();
    }
}