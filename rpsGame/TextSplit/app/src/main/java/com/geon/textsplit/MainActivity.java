package com.geon.textsplit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    TextView tvTitle, txtResult;
    RadioButton[] rdoQ =new RadioButton[4];
    Integer[] rdoqID = {R.id.rdoQ1, R.id.rdoQ2, R.id.rdoQ3, R.id.rdoQ4};
    Integer[] testImage = {R.drawable.test1, R.drawable.test2, R.drawable.test3, R.drawable.test4};
    Button btnResult;
    ImageView imgItem;
    int choice;
    String[] spStr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvTitle = findViewById(R.id.tvTitle);
        txtResult = findViewById(R.id.txtResult);
        for(int i=0; i< rdoQ.length; i++){
            rdoq[i] = findViewById(rdoqID[i]);
        }
        btnResult = findViewById(R.id.btnResult);
        imgItem = findViewById(R.id.imhItem);
        //raw폴더에 있는 test.txt를 가져와서 split매서드로 분리해서 보관
        try{
            InputStream is = getResources().openRawResource(R.raw.test); //res안에raw폴더안에 text를 가져온다.
            byte[] txt = new byte[is.available()]; //is의 바이트 크기만큼
            is.read(txt);
            String str = new String(txt);
            spStr = str.split("#");
            tvTitle.setText("질문 : " + spStr[0] + "(10년후 나의 모습");
            for(int i=0; i<rdoQ.length; i++){
                rdoQ[i].setText(spStr[i+1]);
            }
        }catch (IOException e) {
            Toast.makeText(getApplicationContext(), "파일을 읽을 수 없습니다.", Toast.LENGTH_SHORT).show();
        }
        //라디오버튼을 클릭할 때마다 해당 이미지를 imageView에 보여주기
        for(int i=0; i<rdoQ.length; i++) {
            final int index=i;
            rdoQ[index].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    choice = index+5;
                    imgItem.setImageResource(testImage[index]);
                    txtResult.setText("");
                }
            });
        }

        //결과 보기 버튼 이벤트로 테스트 결과 보여주기
        btnResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtResult.setText(spStr[choice]);
            }
        });
        }
}