package com.example.bullsandcows;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Connecttofriend extends AppCompatActivity {

    int num=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connecttofriend);
        Display(num);
    }
    public void Generate(View v)
    {
        num=(int)(Math.random()*9000)+1000;
        Display(num);
    }
    public void Display(int n)
    {
        TextView scoreView = (TextView) findViewById(R.id.four_digit_number);
        scoreView.setText(String.valueOf(n));
    }

}