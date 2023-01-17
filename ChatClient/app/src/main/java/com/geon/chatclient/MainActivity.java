package com.geon.chatclient;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {
    EditText edtMessage;
    Button btnSend;
    TextView recieveMessage;
    InputStream is;
    ObjectInputStream ois;
    OutputStream os;
    ObjectOutputStream oos;
    Socket socket;
    String sMsg, rMsg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        edtMessage = findViewById(R.id.edtMessage);
        btnSend = findViewById(R.id.btnSend);
        recieveMessage = findViewById(R.id.recieveMessage);
        //전송 버튼 이벤트
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    socket = new Socket("211.238.140.12",7000);
                    os = socket.getOutputStream();
                    oos = new ObjectOutputStream(os);
                    sMsg = edtMessage.getText().toString();
                    oos.writeObject("건상>>" + sMsg);
                    is = socket.getInputStream();
                    ois = new ObjectInputStream(is);
                    rMsg = (String)ois.readObject();
                    recieveMessage.setText(rMsg);
                } catch (Exception e){

                }
            }
        });
    }
}