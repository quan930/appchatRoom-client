package com.example.daquan.qqchat;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {
    static Socket client;
    static ChatMessage alMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        Button button = findViewById(R.id.button);
        final EditText editText = findViewById(R.id.nametext);
        try {
            client = new Socket("47.94.13.255", 5000);
            final PrintWriter out = new PrintWriter(new OutputStreamWriter(client.getOutputStream(),"UTF-8"),true);
            alMessage = new ChatMessage(client);
            Thread t = new Thread(alMessage);
            t.start();//开启接收消息的线程
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String name = editText.getText().toString();
                    out.println(new ClientProtocol().giveName(name));
                    //启动新activity
                    while(alMessage.isGetYorN()){
                        if(alMessage.getMakeNameMessage().equals("创建成功")){
                            Intent intent = new Intent(MainActivity.this,MainTwoActivity.class);
                            intent.putExtra("nameoneself",name);
                            alMessage.setGetYorN(false);
                            finish();
                            startActivity(intent);
                            break;
                        }else {
                            editText.setText("名字重复,重新注册");
                            alMessage.setGetYorN(false);
                            break;
                        }
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
