package com.geon.javaex;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        LinearLayout layout = new LinearLayout(this);
        setContentView(layout,layoutParams);
        Button btnHello = new Button(this);
        TextView tvMessage = new TextView(this);
        btnHello.setText("인사하기");
        tvMessage.setTextSize(20);
        layout.addView(btnHello);
        layout.addView(tvMessage);
        btnHello.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvMessage.setText("안녕하세요 여러분");
            }
        });

    }
}