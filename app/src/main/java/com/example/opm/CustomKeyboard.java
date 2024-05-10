package com.example.opm;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.PopupMenu;

import java.io.FileOutputStream;
import java.io.IOException;

public class CustomKeyboard extends GridLayout implements View.OnClickListener {
    private String[] listSTEPENI = new String[] {"⁰", "¹", "²", "³", "⁴", "⁵", "⁶", "⁷", "⁸", "⁹"};

    private EditText editText;
    public String Function = "";
    public Context context;
    public CustomKeyboard(Context context) {
        this(context, null);
        this.context = context;
    }

    public CustomKeyboard(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomKeyboard(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        LayoutInflater.from(context).inflate(R.layout.custom_keyboard, this, true);
        setupKeys();
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

    }

    public void setEditText(EditText editText) {
        this.editText = editText;
    }

    private void setupKeys() {
        //Number buttons initialize
        findViewById(R.id.button0).setOnClickListener(this);
        findViewById(R.id.button1).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.button3).setOnClickListener(this);
        findViewById(R.id.button4).setOnClickListener(this);
        findViewById(R.id.button5).setOnClickListener(this);
        findViewById(R.id.button6).setOnClickListener(this);
        findViewById(R.id.button7).setOnClickListener(this);
        findViewById(R.id.button8).setOnClickListener(this);
        findViewById(R.id.button9).setOnClickListener(this);
        //functions initialize
        findViewById(R.id.buttonPLUS).setOnClickListener(this);
        findViewById(R.id.buttonMINUS).setOnClickListener(this);
        findViewById(R.id.buttonDIVIDE).setOnClickListener(this);
        findViewById(R.id.buttonDROB).setOnClickListener(this);
        findViewById(R.id.buttonMULTIPLY).setOnClickListener(this);
        //hard initialize
        findViewById(R.id.buttonSQRT).setOnClickListener(this::SQRT);
        findViewById(R.id.buttonpowA2).setOnClickListener(this::pow2);
        findViewById(R.id.buttonpowAB).setOnClickListener(this::powN);
        findViewById(R.id.buttonpowSettings).setOnClickListener(this::showSettingsMenu);
    }

    @SuppressLint("SetTextI18n")
    private void pow2(View view) {
        editText.setText(editText.getText().toString() + "^2");
    }
    @SuppressLint("SetTextI18n")
    private void powN(View view) {
        editText.setText(editText.getText().toString() + "^");
    }

    @SuppressLint("SetTextI18n")
    public void SQRT(View v){
        editText.setText(editText.getText().toString() + "sqrt");

    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View v) {
        Button b = (Button) v;
        editText.setText(editText.getText().toString() + b.getText().toString().replaceAll(" ", ""));
    }


    private void showSettingsMenu(View view) {
        PopupMenu popup = new PopupMenu(this.getContext(), view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.settings_menu, popup.getMenu());

        popup.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.action_settings) {
                System.out.println("РОБОТАЕТ");
                return true;
            }
            return false;
        });
        MenuItem X = popup.getMenu().findItem(R.id.Center_X);
        MenuItem Y = popup.getMenu().findItem(R.id.Center_Y);
        MenuItem SVO = popup.getMenu().findItem(R.id.save);
        final int[] cx = {0};
        final int[] cy = {0};
        X.setOnMenuItemClickListener(v -> {
            AlertDialog.Builder alert = new AlertDialog.Builder(context);
            EditText et = new EditText(context);
            alert.setMessage("Введите центр по X");
            alert.setView(et);
            alert.setPositiveButton("Да", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    cx[0] = Integer.parseInt(et.getText().toString());
                }
            });
            alert.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Log.d("юзер лох", "этот юзер точно лох");
                }
            });
            alert.show();
            return false;
        });
        Y.setOnMenuItemClickListener(v -> {
            AlertDialog.Builder alert = new AlertDialog.Builder(context);
            EditText et = new EditText(context);
            alert.setMessage("Введите центр по X");
            alert.setView(et);
            alert.setPositiveButton("Да", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    cy[0] = Integer.parseInt(et.getText().toString());
                }
            });
            alert.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Log.d("юзер лох", "этот юзер точно лох");
                }
            });
            alert.show();
            return true;
        });
        SVO.setOnMenuItemClickListener(v -> {
            int k = cx[0];
            int k2 = cy[0];
            try {
                FileOutputStream f = new FileOutputStream("Center.txt");
                f.write(String.valueOf(k).getBytes());
                f.write("\n".getBytes());
                f.write(String.valueOf(k2).getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return true;
        });

        popup.show();
    }
}