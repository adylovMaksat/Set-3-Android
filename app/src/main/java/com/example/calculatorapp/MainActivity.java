package com.example.calculatorapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
// Name -  Maksatbek Adylov
// Student Id - 56422
// Set -  Set 3
public class MainActivity extends AppCompatActivity {

    TextView display;

    double previousResult = 0;
    String currentOperator = "";
    boolean operatorPressed = false;
    boolean justEvaluated = false;
    boolean errorState = false;

    String expression = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display = findViewById(R.id.display);

        int[] buttonIds = {
                R.id.button0, R.id.button1, R.id.button2,
                R.id.button3, R.id.button4, R.id.button5,
                R.id.button6, R.id.button7, R.id.button8,
                R.id.button9,
                R.id.buttonPlus, R.id.buttonMinus,
                R.id.buttonMultiply, R.id.buttonDivide,
                R.id.buttonEquals, R.id.buttonC,
                R.id.buttonPower
        };

        for (int id : buttonIds) {

            Button b = findViewById(id);

            b.setOnClickListener(v -> {

                String text = b.getText().toString();

                // ERROR STATE
                if (errorState) {

                    if (text.equals("C")) {
                        clearAll();
                    }

                    return;
                }

                // DIGITS
                if (text.matches("[0-9]")) {

                    // Start new calculation after =
                    if (justEvaluated) {

                        display.setText("");

                        previousResult = 0;
                        currentOperator = "";
                        justEvaluated = false;

                        expression = "";
                    }

                    if (operatorPressed ||
                            display.getText().toString().equals("0")) {

                        display.setText(text);
                        operatorPressed = false;

                    } else {

                        display.append(text);
                    }

                    expression += text;
                }

                // CLEAR
                else if (text.equals("C")) {

                    clearAll();
                }

                // EQUALS
                else if (text.equals("=")) {

                    if (!currentOperator.equals("")) {

                        double currentNumber =
                                Double.parseDouble(
                                        display.getText().toString());

                        double result =
                                calculate(
                                        previousResult,
                                        currentNumber,
                                        currentOperator);

                        if (!errorState) {

                            display.setText(
                                    expression + " = "
                                            + removeDecimal(result));

                            previousResult = result;

                            justEvaluated = true;
                        }
                    }
                }

                // OPERATORS
                else {

                    String op;

                    if (text.equals("num")) {
                        op = "^";
                    } else {
                        op = text;
                    }

                    double currentNumber =
                            Double.parseDouble(
                                    display.getText().toString());

                    if (currentOperator.equals("")) {

                        previousResult = currentNumber;

                    } else {

                        double result =
                                calculate(
                                        previousResult,
                                        currentNumber,
                                        currentOperator);

                        if (!errorState) {

                            previousResult = result;

                            display.setText(removeDecimal(result));
                        }
                    }

                    currentOperator = op;

                    operatorPressed = true;

                    justEvaluated = false;

                    expression += " " + text + " ";
                }

            });
        }
    }

    private double calculate(double a, double b, String op) {

        switch (op) {

            case "+":
                return a + b;

            case "-":
                return a - b;

            case "*":
                return a * b;

            case "/":

                if (b == 0) {

                    display.setText("Error");

                    errorState = true;

                    return 0;
                }

                return a / b;

            case "^":
                return Math.pow(a, b);
        }

        return b;
    }

    private void clearAll() {

        display.setText("0");

        previousResult = 0;

        currentOperator = "";

        operatorPressed = false;

        justEvaluated = false;

        errorState = false;

        expression = "";
    }

    private String removeDecimal(double value) {

        if (value == (long) value) {

            return String.valueOf((long) value);
        }

        return String.valueOf(value);
    }
}