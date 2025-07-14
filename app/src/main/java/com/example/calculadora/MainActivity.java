package com.example.calculadora;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private TextView resultText;
    private Button addButton, subtractButton, multiplyButton, divideButton, enterButton, clearButton;
    private Button nButton, iButton, pvButton, fvButton;
    private Button num1Button, num2Button, num3Button, num4Button;
    private Button num5Button, num6Button, num7Button, num8Button, num9Button, zeroButton, dotButton;
    private double num1, num2;
    private float n, i, pv, fv;
    private boolean isAddition, isSubtraction, isMultiplication, isDivision;
    private Stack<Float> pilha;

    public float calcular(Stack<Float> operandos){
        float num2 = operandos.pop();
        float num1 = operandos.pop();
        float resultado = 0;

        if(isAddition) {
            resultado = num1 + num2;
        }
        if(isSubtraction) {
            resultado = num1 - num2;
        }
        if(isMultiplication) {
            resultado = num1 * num2;
        }
        if(isDivision){
            resultado = num1/num2;
        }

        return resultado;
    }

    public float calcularFV(Stack<Float> pilha){
        pilha.pop(); //remove flag de fim de input
        n = this.pilha.pop();
        i = this.pilha.pop();
        pv = this.pilha.pop();
        fv = (float) ((float) pv * Math.pow((1+i), n));
        return fv;
    }

    public float calcularPV(Stack<Float> pilha){
        pilha.pop();//remove flag de fim de input
        n = this.pilha.pop();
        i = this.pilha.pop();
        fv = this.pilha.pop();
        pv = (float) ((float)fv/Math.pow((1+i), n));
        return pv;
    }

    public float calcularN(Stack<Float> pilha){
        pilha.pop();//remove flag de fim de input
        i = this.pilha.pop();
        fv = this.pilha.pop();
        pv = this.pilha.pop();
        n = (float) ((float) Math.log(fv/pv)/Math.log(1+i));

        return n;
    }

    public float calcularI(Stack<Float> pilha){
        pilha.pop();//remove flag de fim de input
        n = this.pilha.pop();
        fv = this.pilha.pop();
        pv = this.pilha.pop();
        i = (float) ((float) ((Math.pow((fv/pv),(1/n)))-1));

        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editText = findViewById(R.id.editText2);
        resultText = findViewById(R.id.resultText);
        clearButton = findViewById(R.id.clear_text);

        addButton = findViewById(R.id.add);
        subtractButton = findViewById(R.id.sub);
        multiplyButton = findViewById(R.id.mul);
        divideButton = findViewById(R.id.div);
        enterButton = findViewById(R.id.enter);

        nButton = findViewById(R.id.n);
        iButton = findViewById(R.id.i);
        pvButton = findViewById(R.id.PV);
        fvButton = findViewById(R.id.FV);

        num1Button = findViewById(R.id.num1);
        num2Button = findViewById(R.id.num2);
        num3Button = findViewById(R.id.num3);
        num4Button = findViewById(R.id.num4);
        num5Button = findViewById(R.id.num5);
        num6Button = findViewById(R.id.num6);
        num7Button = findViewById(R.id.num7);
        num8Button = findViewById(R.id.num8);
        num9Button = findViewById(R.id.num9);
        zeroButton = findViewById(R.id.zero);
        dotButton = findViewById(R.id.dot);

        pilha = new Stack<>();
        pilhaJuros = new Stack<>();

        nButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText.getText().length()==0){
                    pilha.add(1f);//flag de fim de input
                }else{
                    pilha.add(Float.parseFloat(editText.getText().toString()));
                }
                if(pilha.size()==4){
                    float result = calcularN(pilha);
                    resultText.setText(String.valueOf(result));
                    pilha.add(result);
                }
                editText.setText("");
            }
        });

        iButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText.getText().length()==0){
                    pilha.add(1f);//flag de fim de input
                }else{
                    pilha.add(Float.parseFloat(editText.getText().toString()));
                }
                if(pilha.size()==4){
                    float result = calcularI(pilha);
                    resultText.setText(String.valueOf(result));
                    pilha.add(result);
                }
                editText.setText("");
            }
        });

        fvButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText.getText().length()==0){
                    pilha.add(1f);//flag de fim de input
                }else{
                    pilha.add(Float.parseFloat(editText.getText().toString()));
                }
                if(pilha.size()==4){
                    float result = calcularFV(pilha);
                    resultText.setText(String.valueOf(result));
                    pilha.add(result);
                }
                editText.setText("");
            }
        });

        pvButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText.getText().length()==0){
                    pilha.add(1f);//flag de fim de input
                }else{
                    pilha.add(Float.parseFloat(editText.getText().toString()));
                }

                if(pilha.size()==4){
                    float result = calcularPV(pilha);
                    resultText.setText(String.valueOf(result));
                    pilha.add(result);
                }
                editText.setText("");
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!pilha.isEmpty()){
                    pilha.clear();
                }
                editText.setText("");
                resultText.setText("0");
                isAddition = false;
                isSubtraction = false;
                isMultiplication = false;
                isDivision = false;
            }
        });

        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pilha.add(Float.parseFloat(editText.getText().toString()));
                editText.setText("");
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pilha.add(Float.parseFloat(editText.getText().toString()));
                isAddition = true;
                isSubtraction = false;
                isMultiplication = false;
                isDivision = false;
                float result = calcular(pilha);
                resultText.setText(String.valueOf(result));
                pilha.add(result);
                editText.setText("");
            }
        });

        subtractButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pilha.add(Float.parseFloat(editText.getText().toString()));
                isSubtraction = true;
                isAddition = false;
                isMultiplication = false;
                isDivision = false;
                float result = calcular(pilha);
                resultText.setText(String.valueOf(result));
                pilha.add(result);
                editText.setText("");
            }
        });

        multiplyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pilha.add(Float.parseFloat(editText.getText().toString()));
                isMultiplication = true;
                isAddition = false;
                isSubtraction = false;
                isDivision = false;
                float result = calcular(pilha);
                resultText.setText(String.valueOf(result));
                pilha.add(result);
                editText.setText("");
            }
        });

        divideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pilha.add(Float.parseFloat(editText.getText().toString()));
                if(pilha.peek()!=0){
                    isDivision = true;
                    isAddition = false;
                    isSubtraction = false;
                    isMultiplication = false;
                    float result = calcular(pilha);
                    resultText.setText(String.valueOf(result));
                    pilha.add(result);
                    editText.setText("");
                }else{
                    Log.i("tag","divisão por 0");
                    Toast.makeText(getApplicationContext(), "indeterminado", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText(editText.getText().toString() + ".");
            }
        });

        num1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText(editText.getText().toString() + "1");
            }
        });

        num2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText(editText.getText().toString() + "2");
            }
        });

        num3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText(editText.getText().toString() + "3");
            }
        });

        num4Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText(editText.getText().toString() + "4");
            }
        });

        num5Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText(editText.getText().toString() + "5");
            }
        });

        num6Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText(editText.getText().toString() + "6");
            }
        });

        num7Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText(editText.getText().toString() + "7");
            }
        });

        num8Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText(editText.getText().toString() + "8");
            }
        });

        num9Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText(editText.getText().toString() + "9");
            }
        });

        zeroButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText(editText.getText().toString() + "0");
            }
        });
    }
}