package com.example.opm;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.PopupMenu;
import android.widget.Toast;
public class CustomKeyboard extends GridLayout implements View.OnClickListener {
    private String[] listSTEPENI = new String[] {"⁰", "¹", "²", "³", "⁴", "⁵", "⁶", "⁷", "⁸", "⁹"};

    private EditText editText;
    public String Function = "";
    public CustomKeyboard(Context context) {
        this(context, null);
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
        findViewById(R.id.buttonSQRT).setOnClickListener(this);
        findViewById(R.id.buttonCOS).setOnClickListener(this);
        findViewById(R.id.buttonSIN).setOnClickListener(this);
        findViewById(R.id.buttonTG).setOnClickListener(this);
        findViewById(R.id.buttonCTG).setOnClickListener(this);
        findViewById(R.id.buttonDROB).setOnClickListener(this);
        findViewById(R.id.buttonRAVNO).setOnClickListener(this);
        findViewById(R.id.buttonPLUS).setOnClickListener(this);
        findViewById(R.id.buttonDIVIDE).setOnClickListener(this);
        findViewById(R.id.buttonMULTIPLY).setOnClickListener(this);
        findViewById(R.id.buttonMINUS).setOnClickListener(this);
        findViewById(R.id.buttonX).setOnClickListener(this);
        findViewById(R.id.buttonY).setOnClickListener(this);
        findViewById(R.id.buttonABS).setOnClickListener(this);
        findViewById(R.id.buttonLess).setOnClickListener(this);
        findViewById(R.id.buttonLeftBracket).setOnClickListener(this);
        findViewById(R.id.buttonRightBracket).setOnClickListener(this);
        findViewById(R.id.buttonLessSame).setOnClickListener(this);
        findViewById(R.id.buttonMoreSame).setOnClickListener(this);
        findViewById(R.id.buttonMore).setOnClickListener(this);
        findViewById(R.id.buttonSQRTN).setOnClickListener(this::N);
        findViewById(R.id.buttonpowAB).setOnClickListener(this::N);
        findViewById(R.id.buttonDeleteSymbol).setOnClickListener(this::onClickDELETE);
        findViewById(R.id.buttonRightMOVE).setOnClickListener(this::RIGHT_MOVE);
        findViewById(R.id.buttonLeftMOVE).setOnClickListener(this::LEFT_MOVE);
        findViewById(R.id.buttonpowSettings).setOnClickListener(this::showSettingsMenu);

        Toast.makeText(this.getContext(), "до сюда добрались", Toast.LENGTH_SHORT).show();
    }

    private void showSettingsMenu(View view) {
        PopupMenu popup = new PopupMenu(this.getContext(), view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.settings_menu, popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.action_settings) {
                    System.out.println("РОБОТАЕТ");
                    return true;
                }
                return false;
            }
        });

        popup.show();
    }


    private void N(View view) {
        Button button = (Button) view;
        final EditText editText1 = new EditText(this.getContext());
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setTitle("Введите b");
        builder.setView(editText1);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String n = editText1.getText().toString();
                if (Integer.parseInt(n) > 9 || Integer.parseInt(n) < 0) {
                    dialog.cancel();
                } else {
                    String text = editText.getText().toString();
                    if (button.getText().toString().equals("√")){
                        editText.setText(text + listSTEPENI[Integer.parseInt(n)
                                ] + button.getText().toString().replaceAll(" ", "").replaceAll("ᵑ", ""));
                        Function = Function + n + "√";
                    }
                    else {
                        editText.setText(text + button.getText()
                                .toString().replaceAll(" ", "") + listSTEPENI[Integer.parseInt(n)]);
                        Function = Function + "^" + n;
                    }
                }
            }
        }).setNegativeButton("ОТМЕНА", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    private void RIGHT_MOVE(View view) {
        int position = editText.getSelectionStart();
        if (position < editText.length()){
            editText.setSelection(position + 1);
        }
    }
    private void LEFT_MOVE(View view) {
        int position = editText.getSelectionStart();
        if (position > 0){
            editText.setSelection(position - 1);
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View v) {
        Button button = (Button) v;
        editText.setText(editText.getText() + button.getText().toString().replaceAll(" ", ""));
        if(button.getText() == "a"){
            Function += "^2";
        }
        else {
            Function += button.getText().toString();
        }
    }

    private void onClickDELETE(View v) throws IndexOutOfBoundsException{
        String text = editText.getText().toString();
        editText.setText(text.substring(0, text.length() - 1));
    }
}