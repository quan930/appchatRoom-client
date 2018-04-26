package com.example.daquan.qqchat;

public class ClientProtocol {
    //群发
    public String allSend(String message) {
        String newStr = new String("*al*"+message);
        return newStr;
    }

    //单发
    public String sendNameMessage(String name,String message) {
        String newStr = new String("*11*"+name+"**"+message);
        return newStr;
    }

    //文件
    public String file() {
        String newStr = new String();
        return newStr;
    }

    //显示上线人数
    public String onlineNumber() {
        String newStr = new String("*sp*");
        return newStr;
    }

    //显示当前上线的名字
    public String onlineName() {
        String newStr = new String("*sn*");
        return newStr;
    }

    //退出
    public String signOut() {
        String newStr = new String("*ou*");
        return newStr;
    }

    //起名字
    public String giveName(String name) {
        String newStr = new String("*na*"+name);
        return newStr;
    }
}

