package com.geon.diary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    DatePicker datePick;
    EditText edtDiary;
    Button btnSave;
    int cYear,cMonth,cDay;
    String fileName, diaryContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        datePick = findViewById(R.id.datePic);
        edtDiary = findViewById(R.id.edtDiary);
        btnSave = findViewById(R.id.btnSave);
        Calendar now = Calendar.getInstance();
        cYear = now.get(Calendar.YEAR);
        cMonth = now.get(Calendar.MONDAY);
        cDay = now.get(Calendar.DAY_OF_MONTH);
        fileName = cYear + "_" + (cMonth+1) + "_" + cDay + ".txt";
        edtDiary.setText(readDiary(fileName));
        //달력 초기화 이벤트
        datePick.init(cYear, cMonth, cDay, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int year, int month, int day) {
                fileName=year + "_" + (month+1) + "_" + day + ".txt";
                edtDiary.setText(readDiary(fileName));
            }
        });
        //저장 버튼 클릭 이벤트
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    FileOutputStream fos = openFileOutput(fileName, Context.MODE_PRIVATE);
                    diaryContent=edtDiary.getText().toString();
                    fos.write(diaryContent.getBytes());
                    fos.close();
                    Toast.makeText(getApplicationContext(),fileName + "가 저장되었습니다.",Toast.LENGTH_SHORT).show();
                }catch (IOException e){
                    Toast.makeText(getApplicationContext(),"일기를 저장할 수 없습니다.",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }//onCreate
    //저장된 일기를 읽어오는 메서드
    String readDiary(String fileName){
        String diaryStr="";
        try {
            FileInputStream fis=openFileInput(fileName);
            byte[] content = new byte[fis.available()];
            fis.read(content);
            diaryStr=new String(content);
            fis.close();
            btnSave.setText("수정하기");
        }catch (IOException e) {
            edtDiary.setHint("일기없음");
            edtDiary.setText("");
            btnSave.setText("새로 저장");
        }
        return diaryStr;
    }
}//MainActivity