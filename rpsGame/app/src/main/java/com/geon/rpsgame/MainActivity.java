package com.geon.rpsgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    //버튼 클래스 배열
    Button[] btnGame=new Button[3];
    //버튼 id
    Integer[] btnGameID={R.id.btnGame1, R.id.btnGame2, R.id.btnGame3};
    ImageView myImage, comImage;
    TextView tvResult;
    int myChoice, comChoice;
    //이미지 배열
    Integer[] gameImage={R.drawable.img1, R.drawable.img2, R.drawable.img3};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //버튼 배열을 버튼id에 캐스팅
        for(int i=0; i<btnGame.length; i++){
            btnGame[i]=findViewById(btnGameID[i]);
        }
        myImage = findViewById(R.id.myImage);
        comImage = findViewById(R.id.comImage);
        tvResult = findViewById(R.id.tvResult);

        for(int i=0; i<btnGame.length; i++) {
            final int index=i;
            btnGame[index].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    myChoice = index+1;
                    gameProcess(myChoice);
                }
            });
        }

    }//onCreate
    //게임 처리 메서드
        void gameProcess(int myChoice){
            comChoice=(int)(Math.random()*3)+1;
            myImage.setImageResource(gameImage[myChoice-1]);
            comImage.setImageResource(gameImage[comChoice-1]);
            if(myChoice == comChoice) {
                tvResult.setText("무승부");
            }else if((myChoice == 1 && comChoice == 2) || (myChoice == 2 && comChoice == 3) || (myChoice == 3 && comChoice == 1)) {
               tvResult.setText("승리");
            }else {
                tvResult.setText("패배");
            }
        }
    }//MainActivity
