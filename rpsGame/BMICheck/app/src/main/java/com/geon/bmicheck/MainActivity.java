package com.geon.bmicheck;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText edtHeight, edtWeight;
    Button btnBIMCheck;
    TextView txtResult;
    ImageView imageBMI;
    int height, weight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 자바에서 선언 = 찾아서 연결해준다(RES에 id중 edtHeight에)
        edtHeight=findViewById(R.id.edtHeight);
        edtWeight=findViewById(R.id.edtWeight);
        btnBIMCheck=findViewById(R.id.btnBMICheck);
        txtResult=findViewById(R.id.txtResult);
        imageBMI=findViewById(R.id.imageBMI);
        //버튼 클릭 이벤트
        btnBIMCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                height=Integer.parseInt(edtHeight.getText().toString());
                weight=Integer.parseInt(edtWeight.getText().toString());
                int standardWeight = (int)((height - 100) * 0.9);
                if(weight > standardWeight + 5) {
                    txtResult.setText("비만입니다. 운동하세요.");
                    imageBMI.setImageResource(R.drawable.ob);
                } else if(weight >= standardWeight -5) {
                    txtResult.setText("정상입니다. 꾸준히 유지하세요.");
                    imageBMI.setImageResource(R.drawable.good);
                } else {
                    txtResult.setText("마른체형입니다. 밥 많이 드세요.");
                    imageBMI.setImageResource(R.drawable.thin);
                }
            }
        });
     }
}