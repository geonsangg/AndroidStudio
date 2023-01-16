package com.geon.schedulelist;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button btnScheduleSave;
    ListView scheduleList;
    TextView tvContent;
    List<String> title;
    List<String> content;
    ArrayAdapter<String> adapter;
    View dialogView;
    MyScheduleDB myScheduleDB; //데이터베이스 선언
    SQLiteDatabase sqlDB; //DML(select, insert, update, delete)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnScheduleSave = findViewById(R.id.btnScheduleSave);
        scheduleList = findViewById(R.id.scheduleList);
        tvContent = findViewById(R.id.tvContent);
        title = new ArrayList<String>();
        content = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,title);
        scheduleList.setAdapter(adapter);
        myScheduleDB = new MyScheduleDB(this);
        //저장한 스케줄을 전부 보여줌.
        sqlDB = myScheduleDB.getReadableDatabase();
        Cursor cursor;
        cursor=sqlDB.rawQuery("SELECT * FROM scheduleTBL", null);
        while (cursor.moveToNext()){
            title.add(cursor.getString(0));
            content.add(cursor.getString(1));
        }
        adapter.notifyDataSetChanged();
        cursor.close();
        sqlDB.close();
        //스케줄 등록 버튼 이벤트
        btnScheduleSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogView = View.inflate(MainActivity.this,R.layout.dialog,null);
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("스케줄 등록");
                builder.setIcon(R.drawable.happy);
                builder.setView(dialogView);
                EditText dialogTitle = dialogView.findViewById(R.id.dialogTitle);
                EditText dialogContent = dialogView.findViewById(R.id.dialogContent);
                //등록 버튼 이벤트
                builder.setNegativeButton("등록", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String dTile = dialogTitle.getText().toString();
                        String dContent = dialogContent.getText().toString();
                        title.add(dTile);
                        content.add(dContent);
                        adapter.notifyDataSetChanged();
                        sqlDB=myScheduleDB.getWritableDatabase();
                        sqlDB.execSQL("INSERT INTO scheduleTBL VALUES('" + dTile + "','" + dContent +"')");
                        sqlDB.close();
                        Toast.makeText(getApplicationContext(),"스케줄이 등록되었습니다.",Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setPositiveButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(),"등록이 취소되었습니다.",Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });
        //리스트 뷰를 클릭 이벤트
        scheduleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                tvContent.setText(content.get(i));
            }
        });
        //리스트 뷰를 길게 클릭 이벤트
        scheduleList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("스케줄 삭제");
                builder.setIcon(R.drawable.delicon);
                builder.setMessage("선택한 스케줄을 삭제하시겠습니까?");
                builder.setNegativeButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        sqlDB=myScheduleDB.getWritableDatabase();
                        sqlDB.execSQL("DELETE FROM scheduleTBL WHERE title = '" + title.get(position) + "';");
                        sqlDB.close();
                        title.remove(position);
                        content.remove(position);
                        adapter.notifyDataSetChanged();
                        tvContent.setText("");
                        Toast.makeText(getApplicationContext(),"선택된 스케줄이 삭제되었습니다.",Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setPositiveButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(),"삭제가 취소되었습니다.",Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
                return false;
            }
        });
    }//onCreate
    //내부 클래스
    public class MyScheduleDB extends SQLiteOpenHelper{
        //생성자를 통해서 DB생성
        public MyScheduleDB(@Nullable Context context) {
            super(context, "schedulDB", null, 1);
        }

        //테이블 생성
        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL("CREATE TABLE scheduleTBL(title Text, content TEXT);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }
    }
}//MainActivity