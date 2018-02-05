package com.i1d.icalc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import org.apache.commons.lang3.StringUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    private ImageButton btn_del;
    private TextView txt_input,txt_output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt_input = (TextView) findViewById(R.id.txt_input);
        txt_output = (TextView) findViewById(R.id.txt_output);
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
            if (infix[i].matches("\\d*\\.?\\d*")) {
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

        while (!st.empty())
            postfix.add(st.pop());

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
        Stack<Double> st = new Stack<>();
        double op1, op2, value = 0;
        while(i < postfix.size()) {
            if(postfix.get(i).matches("\\d*\\.?\\d*"))
                st.push(Double.parseDouble(postfix.get(i)));
            else {
                op2 = st.pop();
                op1 = st.pop();
                switch(postfix.get(i)) {
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

        if(value == (int)value)
            return String.valueOf(st.pop().intValue());
        else {
            DecimalFormat decformat = new DecimalFormat("#.#####");
            return String.valueOf(decformat.format(st.pop().doubleValue()));
        }
    }
}
