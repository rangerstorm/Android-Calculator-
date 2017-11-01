package com.example.dell.calculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.regex.Pattern;


public class Calculator extends AppCompatActivity {
    DecimalFormat formatter;

    {
        formatter = new DecimalFormat("###,###,###,###,###,###.##########################");
    }

    private TextView _screen;
    private String display = String.valueOf(0);
    private String currentOperator = "";
    private String result = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        _screen = (TextView)findViewById(R.id.textView);
        _screen.setText(display);
    }

    private void updateScreen(){
        _screen.setText(display);
    }

    public void onClickNumber(View v){
        if(result != ""){
            clear();
            updateScreen();
        }
        Button b = (Button) v;

        if(display.equals("0")) {

            display = b.getText().toString();
        }
        else{

            display += b.getText().toString();
        }
                updateScreen();
    }

    private boolean isOperator(char op){
        switch (op){
            case '+':
            case '-':
            case '÷':return true;
            case '%':return true;
            case '×':return true;
            default: return false;
        }
    }

    public void onClickOperator(View v){
        if(display == "") return;

        Button b = (Button)v;

        if(result != ""){
            String _display = result;
            clear();
            display = _display;
        }

        if(currentOperator != ""){
            Log.d("CalcX", ""+display.charAt(display.length()-1));
            if(isOperator(display.charAt(display.length()-1))){
                display = display.replace(display.charAt(display.length()-1), b.getText().charAt(0));
                updateScreen();
                return;
            }else{
                getResult();
                display = result;
                result = "";
            }
            currentOperator = b.getText().toString();
        }
        display += b.getText();
        currentOperator = b.getText().toString();
        updateScreen();
    }
    private void clear(){
        display = String.valueOf(0);
        currentOperator = "";
        result = "";
    }
    public void onClickBack(View v) {
        String str = _screen.getText().toString();
        if (str.length() > 1) {
            str = str.substring(0, str.length() - 1);
            _screen.setText(str);
        } else if (str.length() <= 1) {
            clear();
            updateScreen();
        }
    }

    public void onClickClear(View v){
        clear();
        updateScreen();
    }

    private String operate(String a, String b, String op){
        switch (op){
            case "+":
                return  formatter.format(Double.valueOf(a) + Double.valueOf(b));
            case "-":
                return formatter.format(Double.valueOf(a) - Double.valueOf(b));

            case "÷": try{
                return formatter.format(Double.valueOf(a) / Double.valueOf(b));
            }catch (Exception e){
                Log.d("Calc", e.getMessage());
            }
            case "%":
                try {
                    return formatter.format((Double.valueOf(a) / Double.valueOf(b)) * 100);
                }
                catch (Exception e){
                    Log.d("Calc", e.getMessage());
                }
            case "×":
                try {
                    return formatter.format(Double.valueOf(a) * Double.valueOf(b));
                }
                catch(Exception e){
                    Log.d("Calc", e.getMessage());
                }

            default: return String.valueOf(0);
        }
    }

    private boolean getResult(){
        if(currentOperator == "") return false;
        String[] operation = display.split(Pattern.quote(currentOperator));
        if(operation.length < 2) return false;
        result = String.valueOf(operate(operation[0], operation[1], currentOperator));
        return true;
    }

    public void onClickEqual(View v){
        if(display == "") return;
        if(!getResult()) return;
        _screen.setText(display + "\n" + String.valueOf(result));
    }
}
