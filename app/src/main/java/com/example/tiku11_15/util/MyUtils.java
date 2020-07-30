package com.example.tiku11_15.util;

import android.app.AlertDialog;
import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @LogIn Name zhangyingyu
 * @Create by 张瀛煜 on 2020-07-15 at 18:30 ：）
 */
public class MyUtils {
    public  static  void showDialog(Context context, String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("提示");
        builder.setMessage(msg);
        builder.setPositiveButton("确定",null);
        builder.create().show();
    }


    public static  String SimpDate(String type, Date date){
        SimpleDateFormat format = new SimpleDateFormat(type);
        return format.format(date);
    }
}

