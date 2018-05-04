package com.example.daquan.qqchat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import static com.example.daquan.qqchat.MainActivity.alMessage;
import static com.example.daquan.qqchat.MainActivity.client;

public class MainTwoActivity extends AppCompatActivity {
    private String sendName;//发送者名字
    private List<String> nameList;
    static Handler handler;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_two);
        Intent intent = getIntent();
        String name =  intent.getStringExtra("nameoneself");
        Button refresh = (Button)findViewById(R.id.refresh);//刷新
        Button send = (Button)findViewById(R.id.send);//发送
        TextView textoneself = findViewById(R.id.oneselfname);
        textoneself.setText(name);
        final EditText editText = findViewById(R.id.editText);
        final TextView textView = findViewById(R.id.textView);//消息框
        alMessage.setTextView(textView);
        final PrintWriter out;
        handler = new Handler()
        {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 1:
                        textView.setText(msg.getData().getString("123"));
                        break;
                }
            }
        };
        try {
            out = new PrintWriter(new OutputStreamWriter(client.getOutputStream(),"UTF-8"),true);
            final ListView listView = (ListView) findViewById(R.id.list_view);
            //刷新键
            refresh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    out.println(new ClientProtocol().onlineName());
                    nameList = new ArrayList<String>();
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainTwoActivity.this,android.R.layout.simple_list_item_1,nameList);
                    listView.setAdapter(adapter);
                    for(int i = 0;i < alMessage.getNames().size();i++) {
                        nameList.add(alMessage.getNames().get(i));
                    }
                    alMessage.listNames(new ArrayList<String>());//清空列表
                    alMessage.setGetYorN(false);
                    //列表点击
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            sendName = nameList.get(i);
                        }
                    });
                }
            });
            //发送键
            send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String message = editText.getText().toString();
                    out.println(new ClientProtocol().sendNameMessage(sendName, message));
                    while(true) {
                        if(alMessage.isGetYorN()) {
                            break;
                        }else {
                            continue;
                        }
                    }
                    alMessage.setGetYorN(false);
                    editText.setText(null);
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}