package com.example.opm;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.example.opm.databinding.ActivityMainBinding;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Entry> entriesempty = new ArrayList<>();
    private LineDataSet datasetEmpty = new LineDataSet(entriesempty, "График первый");
    private ActivityMainBinding binding;
    private ArrayList<ILineDataSet> dataSetsEmpty = new ArrayList();
    private BuildMathFunction drawing = new BuildMathFunction();
    private ArrayList<String> FunctionsList;
    private ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        start();

        binding.draw.setOnClickListener(v -> {
            for (int i = 0; i < FunctionsList.size(); i++) {
                drawing.draw(FunctionsList.get(i), i);
                drawing.draw_all(binding);
            }
        });

        binding.plus.setOnClickListener(v -> {
            String text = binding.enterfunction.getText().toString();
            FunctionsList.add(text);
            adapter.notifyDataSetChanged();
            binding.enterfunction.setText("");
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

}