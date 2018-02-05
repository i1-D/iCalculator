package com.i1d.icalc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    private Button btn0,btn00,btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btn_clear;
    private Button btn_result,btn_add,btn_sub,btn_div,btn_mul,btn_mod,btn_dot;
    private ImageButton btn_del;
    private TextView txt_input,txt_output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initControl();
    }

    private void initControl() {

        txt_input = (TextView) findViewById(R.id.txt_input);txt_output = (TextView) findViewById(R.id.txt_output);

        btn0 = (Button) findViewById(R.id.btn_0);btn00 = (Button) findViewById(R.id.btn_00);btn1= (Button) findViewById(R.id.btn_1);
        btn2 = (Button) findViewById(R.id.btn_2);btn3 = (Button) findViewById(R.id.btn_3);btn4 = (Button) findViewById(R.id.btn_4);
        btn5 = (Button) findViewById(R.id.btn_5);btn6 = (Button) findViewById(R.id.btn_6);btn7 = (Button) findViewById(R.id.btn_7);
        btn8 = (Button) findViewById(R.id.btn_8);btn9 = (Button) findViewById(R.id.btn_9);btn_del = (ImageButton) findViewById(R.id.btn_del);
        btn_clear = (Button) findViewById(R.id.btn_clear);btn_result = (Button) findViewById(R.id.btn_result);btn_add = (Button) findViewById(R.id.btn_add);
        btn_sub = (Button) findViewById(R.id.btn_sub);btn_mul = (Button) findViewById(R.id.btn_mul);btn_div = (Button) findViewById(R.id.btn_div);
        btn_mod = (Button) findViewById(R.id.btn_mod);btn_dot = (Button) findViewById(R.id.btn_dot);

    }

    public void btn_O_Click(View view) {
        operandFunction('0');
    }

    public void btn_1_Click(View view) {
        operandFunction('1');
    }

    public void btn_2_Click(View view) {
        operandFunction('2');
    }

    public void btn_3_Click(View view) {
        operandFunction('3');
    }

    public void btn_4_Click(View view) {
        operandFunction('4');
    }

    public void btn_5_Click(View view) {
        operandFunction('5');
    }

    public void btn_6_Click(View view) {
        operandFunction('6');
    }

    public void btn_7_Click(View view) {
        operandFunction('7');
    }

    public void btn_8_Click(View view) {
        operandFunction('8');
    }

    public void btn_9_Click(View view) {
        operandFunction('9');
    }

    public void btn_O0_Click(View view) {
        operandFunction('0');
        operandFunction('0');
    }

    public void btn_dot_Click(View view) {
        operandFunction('.');
    }

    public void btn_clear_Click(View view) {
        txt_input.setText("");
        txt_output.setText("");
    }

    public void btn_del_Click(View view) {
        if(!StringUtils.isAllEmpty(txt_input.getText())) {
            int len = txt_input.length();
            char last = txt_input.getText().charAt(len-1);
            if (last == ' ')
                txt_input.setText(txt_input.getText().subSequence(0, len - 3));
            else
                txt_input.setText(txt_input.getText().subSequence(0, len - 1));
        }
    }

    public void btn_add_Click(View view) {
        operatorFunction('+');
    }

    public void btn_sub_Click(View view) {
        operatorFunction('−');
    }

    public void btn_mul_Click(View view) {
        operatorFunction('×');
    }

    public void btn_div_Click(View view) {
        operatorFunction('÷');
    }

    public void btn_mod_Click(View view) {
        operatorFunction('%');
    }

    public void btn_result_Click(View view) {
        String[] inputArray = txt_input.getText().toString().split(" ");
        txt_output.setText(calculateExp(convertExp(inputArray)));
    }

    public void operandFunction(char operand) {
        txt_input.setText(txt_input.getText() + Character.toString(operand));
    }

    public void operatorFunction(char operator) {
        if(!StringUtils.isAllEmpty(txt_input.getText())) {
            int len = txt_input.length();
            char last = txt_input.getText().charAt(len - 1);
            if (last == ' ')
                txt_input.setText(txt_input.getText().toString().subSequence(0, len - 3) + " " + Character.toString(operator) + " ");
            else
                txt_input.setText(txt_input.getText() + " " + Character.toString(operator) + " ");
        }
    }

    public List<String> convertExp(String[] infix) {
        int i = 0;
        char temp;
        List<String> postfix = new ArrayList<>();
        Stack<String> st = new Stack<>();

        while (i < infix.length) {
            if (StringUtils.isNumeric(infix[i])) {
                postfix.add(infix[i]);
                i++;
            } else {
                while (!st.empty() && (getPriority(st.lastElement().charAt(0)) > getPriority(infix[i].charAt(0)))) {
                    postfix.add(st.pop());
                }
                st.push(infix[i]);
                i++;
            }
        }

        while (!st.empty()) {
            postfix.add(st.pop());
        }

        return postfix;
    }

    public int getPriority(char op) {
        if(op=='÷' || op == '×' || op=='%')
            return 1;
        else
            return 0;
    }

    public String calculateExp(List<String> postfix) {
        int i = 0;
        Stack<Float> st = new Stack<>();
        float op1, op2, value = 0;
        while(i < postfix.size())
        {
            if(StringUtils.isNumeric(postfix.get(i)))
                st.push(Float.parseFloat(postfix.get(i)));
            else
            {
                op2 = st.pop();
                op1 = st.pop();
                switch(postfix.get(i))
                {
                    case "+":
                        value = op1 + op2;
                        break;
                    case "−":
                        value = op1 - op2;
                        break;
                    case "÷":
                        value = op1 / op2;
                        break;
                    case "×":
                        value = op1 * op2;
                        break;
                    case "%":
                        value = (int)op1 % (int)op2;
                        break;
                }
                st.push(value);
            }
            i++;
        }
        return String.valueOf(st.pop().floatValue());
    }
}
