package com.example.daquan.qqchat;

import android.widget.TextView;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.example.daquan.qqchat.MainActivity.alMessage;

//全部消息
//处理消息类//命令行界面
public class ChatMessage implements Runnable{
    private Socket client;
    private List<String>messages = new ArrayList<String>();//处理后消息
    private List<String> names = new ArrayList<String>();//名字列表
    private int personNum = 0;//上线总人数
    private String makeNameMessage;//注册名字的信息
    private boolean getYorN = false;//得到消息
    private String oneMessage;//单发成功消息
    private TextView textView;
    public boolean isGetYorN() {
        return getYorN;
    }
    public void setGetYorN(boolean getYorN) {
        this.getYorN = getYorN;
    }
    //返回名字列表
    public List<String> getNames() {
        while(true) {//接收指令
            if(getYorN) {
                break;
            }else {
                continue;
            }
        }
        return names;
    }
    //单发消息
    public String getOneMessage() {
        while(true) {//接收指令
            if(getYorN) {
                break;
            }else {
                continue;
            }
        }
        return oneMessage;
    }
    //返回人数
    public int getPersonNum() {//查看人数看完就关
        while(true) {//接收指令
            if(getYorN) {
                break;
            }else {
                continue;
            }
        }
//		personNumYorN = false;
        return personNum;
    }
    //消息列表
    public List<String> getMessages() {
        return messages;
    }
    //返回注册名字信息
    public String getMakeNameMessage() {
        return makeNameMessage;
    }
    //清空名字列表
    public void listNames(List<String> names) {
        this.names = names;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        try {
            @SuppressWarnings("resource")
            Scanner in = new Scanner(client.getInputStream());
            //isOnline= true;//上下线
            while(true){//
                String message = in.nextLine();//读入数据
//				String s = message.substring(0,4);
                if(message.substring(0,4).equals("*na*")) {//名字指令
                    makeNameMessage = message.substring(4);
                    getYorN = true;
                    continue;
                }else {
                    if(message.substring(0,4).equals("*sp*")) {//人数指令
                        personNum = Integer.parseInt(message.substring(4));
                        getYorN = true;
                        continue;
                    }else {
                        if(message.substring(0,4).equals("Name")) {//人名指令
                            String[] sarray = message.substring(4).split("Name");
                            for(String ss:sarray){
                                names.add(ss);
                            }
                            getYorN = true;
                            continue;
                        }else {
                            if(message.substring(0,4).equals("*ou*")) {//退出指令
                                getYorN = true;
                                break;
                            }else {
                                if(message.substring(0,4).equals("*11*")) {
                                    oneMessage = message.substring(4);
                                    getYorN = true;
                                    continue;
                                }else {
                                    messages.add(message);
                                    StringBuilder messageList = new StringBuilder();
                                    for(int i = 0; i < alMessage.getMessages().size(); i++) {
                                        messageList.append(("第"+(i+1)+"条\t"+alMessage.getMessages().get(i)+"\n"));

                                    }
                                    textView.setText(messageList);
                                    continue;
                                }
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            this.run();
        }
    }
    //将socket client 传入
    public ChatMessage(Socket client) {
        this.client = client;
    }
}