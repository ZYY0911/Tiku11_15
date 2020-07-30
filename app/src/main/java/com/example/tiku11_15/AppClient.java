package com.example.tiku11_15;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.litepal.LitePal;


/**
 * @LogIn Name zhangyingyu
 * @Create by 张瀛煜 on 2020-07-15 at 10:19 ：）
 */
public class AppClient extends Application {
    public static SharedPreferences preferences;
    public static RequestQueue requestQueue;
    public static final String IP = "ip";
    public static final String PORT = "port";
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LitePal.initialize(this);
        preferences = getSharedPreferences("P", 0);
        requestQueue = Volley.newRequestQueue(this);
    }


    public static void add(JsonObjectRequest jsonObjectRequest) {
        requestQueue.add(jsonObjectRequest);
    }
}
