package com.example.tiku11_15.net;

import com.android.volley.VolleyError;

import org.json.JSONObject;

/**
 * @LogIn Name zhangyingyu
 * @Create by 张瀛煜 on 2020-07-11 at 17:27 ：）
 */
public interface VolleyLo {
    void onResponse(JSONObject jsonObject);
    void onErrorResponse(VolleyError volleyError);
}
