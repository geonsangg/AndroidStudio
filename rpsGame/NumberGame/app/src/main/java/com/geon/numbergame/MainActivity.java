package com.geon.numbergame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    EditText edtNumber;
    Button btnGameStart, btnResult, btnEnd;
    TextView hintAndresult;
    ImageView imgGame;
    int myNum, comNum, count, choice;
    Random rand=new Random();
    String[] menu={"편의점커피","음료수","과자한봉지","꽝(없음)","꿀밤한대","삼각김밥"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtNumber = findViewById(R.id.edtNumber);
        btnGameStart = findViewById(R.id.btnGameStart);
        btnResult = findViewById(R.id.btnResult);
        hintAndresult = findViewById(R.id.hintAndResult);
        imgGame = findViewById(R.id.imgGame);
        btnEnd = findViewById(R.id.btnEnd);
        //게임시작 버튼 이벤트
        //  객체.메서드(클릭하면 셋팅하도록) ( 보여주는 인터페이스)
        btnGameStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count=0;
                comNum=(int)(Math.random()*100)+1;
                btnResult.setEnabled(true);
                btnGameStart.setEnabled(false);
                hintAndresult.setText("자! 게임이 시작되었습니다.");
                imgGame.setImageResource(R.drawable.number);
            }
        });
        //결과확인 버튼 이벤트
        btnResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                myNum = Integer.parseInt(edtNumber.getText().toString());
                count++;
                if (myNum > comNum) {
                    hintAndresult.setText("당신의 숫자가 큽니다 좀 더 작은 수를 넣어보세요.\n(" + count + "번 틀림");
                    imgGame.setImageResource(R.drawable.wrong);
                } else if (myNum < comNum) {
                    hintAndresult.setText("당신의 숫자가 작습니다 좀 더 큰 수를 넣어보세요.\n(" + count + "번 틀림");
                    imgGame.setImageResource(R.drawable.wrong);
                } else {
                    choice=rand.nextInt(menu.length);
                    hintAndresult.setText("축하합니다. 정답입니다!\n(도전 횟수 = " + count + ")\n내기상품 => " + menu[choice]);
                    imgGame.setImageResource(R.drawable.good);
                    btnResult.setEnabled(false);
                    btnGameStart.setEnabled(true);
                                    }
                edtNumber.setText("");
            }catch (NumberFormatException e) {
                    hintAndresult.setText("숫자를 입력해주세요.");
                }
            }
        });
        //앱종료 버튼 이벤트
        btnEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}