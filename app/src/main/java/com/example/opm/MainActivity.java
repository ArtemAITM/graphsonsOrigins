package com.example.opm;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.opm.databinding.ActivityMainBinding;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private BuildMathFunction drawing = new BuildMathFunction();
    private ArrayList<String> FunctionsList;
    private ArrayAdapter<String> adapter;
    private CustomKeyboard customKeyboard;
    private InputMethodManager inputMethodManager;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        start();
        openKeyboard();
        binding.draw.setOnClickListener(v -> {
            for (int i = 0; i < FunctionsList.size(); i++) {
                drawing.draw(FunctionsList.get(i), i);
                drawing.draw_all(binding);
            }
        });

        binding.plus.setOnClickListener(v -> {
            String text = binding.editText.getText().toString();
            FunctionsList.add(text);
            adapter.notifyDataSetChanged();
            binding.editText.setText("");
        });
    }

    protected void start(){
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //funtction.nullChart(binding);
        FunctionsList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, R.layout.function_item, R.id.FunctionString, FunctionsList);
        binding.listFunctions.setAdapter(adapter);
        binding.listFunctions.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String pos = FunctionsList.get(position);
                delFunction(pos);
                return false;
            }
        });
    }

    private void delFunction(String pos) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Удалить функцию?");
        builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FunctionsList.remove(pos);
                adapter.notifyDataSetChanged();
            }
        });

        builder.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
    protected void openKeyboard(){
        EditText editText = findViewById(R.id.editText);
        customKeyboard = new CustomKeyboard(this);
        customKeyboard.setEditText(editText);

        // Получаем InputMethodManager для управления клавиатурой
        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        // Скрываем основную клавиатуру при фокусировке на поле ввода
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    editText.requestFocus();
                    editText.setCursorVisible(true);
                    inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                    FrameLayout.LayoutParams layoutParams
                            = new FrameLayout.LayoutParams(
                            FrameLayout.LayoutParams.MATCH_PARENT,
                            FrameLayout.LayoutParams.WRAP_CONTENT);

                    layoutParams.gravity = Gravity.BOTTOM;
                    ((FrameLayout) findViewById(android.R.id.content)).addView(customKeyboard, layoutParams);
                    binding.getRoot().setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {

                            inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                            editText.clearFocus();
                            return false;
                        }
                    });
                    editText.post(new Runnable() {
                        @Override
                        public void run() {
                            editText.setCursorVisible(true);
                        }
                    });
                } else {
                    Toast.makeText(MainActivity.this, "dsgsdgsdhd", Toast.LENGTH_SHORT).show();
                    ((FrameLayout) findViewById(android.R.id.content)).removeView(customKeyboard);
                }

            }
        });
        editText.setInputType(InputType.TYPE_NULL);
    }

}