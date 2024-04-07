package com.example.opm;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;

import com.example.opm.databinding.ActivityMainBinding;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

public class Settings extends MainActivity{
    private Context context;
    private ActivityMainBinding binding;
    private View view;
    public Settings(Context context, ActivityMainBinding bindin, View v){
        this.binding = bindin;
        this.context = context;
        this.view = v;
    }
    public void showSettingsMenu() {
        PopupMenu popup = new PopupMenu(this, findViewById(R.id.plus));
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.settings_menu, popup.getMenu());


        popup.show();
        Button button = findViewById(R.id.save);
        button.setOnClickListener(v -> {
            EditText centerX = findViewById(R.id.Center_X);
            EditText centerY = findViewById(R.id.Center_Y);
            String cX = centerX.getText().toString();
            String cY = centerY.getText().toString();
            try {
                FileOutputStream fileOutputStream = new FileOutputStream("center");
                fileOutputStream.write(cX.getBytes());
                fileOutputStream.write("\n".getBytes());
                fileOutputStream.write(cY.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

}
