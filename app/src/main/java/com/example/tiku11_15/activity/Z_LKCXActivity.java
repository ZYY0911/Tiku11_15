package com.example.tiku11_15.activity;

import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.tiku11_15.R;
import com.example.tiku11_15.bean.LKCX;
import com.example.tiku11_15.net.VolleyLo;
import com.example.tiku11_15.net.VolleyTo;
import com.example.tiku11_15.util.MyUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @LogIn Name win10
 * @Create by 张瀛煜 on 2020/7/28 at 14:04 ：）
 */
public class Z_LKCXActivity extends BaseActivity {
    @BindView(R.id.iv_flash)
    ImageView ivFlash;
    @BindView(R.id.iv_gif_1)
    ImageView ivGif1;
    @BindView(R.id.iv_gif_2)
    ImageView ivGif2;
    @BindView(R.id.tv_tcc)
    TextView tvTcc;
    @BindView(R.id.tv_xyl)
    TextView tvXyl;
    @BindView(R.id.tv_lxl)
    TextView tvLxl;
    @BindView(R.id.tv_xfl)
    TextView tvXfl;
    @BindView(R.id.tv_yyl)
    TextView tvYyl;
    @BindView(R.id.tv_hcks_1)
    TextView tvHcks1;
    @BindView(R.id.tv_hcks_4)
    TextView tvHcks4;
    @BindView(R.id.tv_hcks_2)
    TextView tvHcks2;
    @BindView(R.id.tv_hcks_3)
    TextView tvHcks3;
    @BindView(R.id.tv_hcgs_1)
    TextView tvHcgs1;
    @BindView(R.id.tv_hcgs_2)
    TextView tvHcgs2;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_week)
    TextView tvWeek;
    @BindView(R.id.tv_wd)
    TextView tvWd;
    @BindView(R.id.tv_sd)
    TextView tvSd;
    @BindView(R.id.tv_pm)
    TextView tvPm;
    private VolleyTo volleyTo;
    private List<LKCX> lkcxes;

    @Override
    public int getLayout() {
        return R.layout.lkcx_layout;
    }

    @Override
    public String setTitle() {
        return "路况查询";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        setVolley_Road();
        AnimationDrawable animationDrawable = (AnimationDrawable) ivGif1.getDrawable();
        animationDrawable.start();
        AnimationDrawable animationDrawable1 = (AnimationDrawable) ivGif2.getDrawable();
        animationDrawable1.start();
        tvDate.setText(MyUtils.SimpDate("yyyy-M-d",new Date()));
        tvWeek.setText(MyUtils.SimpDate("E",new Date()));
        setVolley_Weather();
    }

    private void setVolley_Weather() {
        VolleyTo volleyTo = new VolleyTo();
        volleyTo.setUrl("get_all_sense")
                .setJsonObject("UserName", "user1")
                .setVolleyLo(new VolleyLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        tvWd.setText(jsonObject.optInt("temperature")+"℃");
                        tvSd.setText(jsonObject.optInt("humidity")+"%");
                        tvPm.setText(jsonObject.optInt("pm25")+"ug/m³");
                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }).start();
    }

    private void setVolley_Road() {
        volleyTo = new VolleyTo();
        volleyTo.setUrl("get_road_status")
                .setJsonObject("UserName", "user1")
                .setJsonObject("RoadId", 0)
                .setLoop(true)
                .setTime(3000)
                .setVolleyLo(new VolleyLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        lkcxes = new Gson().fromJson(jsonObject.optJSONArray("ROWS_DETAIL").toString()
                                , new TypeToken<List<LKCX>>() {
                                }.getType());
                        setRoadColor();
                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }).start();
    }

    private void setRoadColor() {
        for (int i = 0; i < lkcxes.size(); i++) {
            LKCX lkcx = lkcxes.get(i);
            switch (i){
                case 0:
                    tvXyl.setBackgroundColor(getRoadColor(lkcx.getState()));
                    break;
                case 1:
                    tvLxl.setBackgroundColor(getRoadColor(lkcx.getState()));
                    break;
                case 2:
                    tvYyl.setBackgroundColor(getRoadColor(lkcx.getState()));
                    break;
                case 3:
                    tvXfl.setBackgroundColor(getRoadColor(lkcx.getState()));
                    break;
                case 4:
                    tvHcks1.setBackgroundColor(getRoadColor(lkcx.getState()));
                    tvHcks2.setBackgroundColor(getRoadColor(lkcx.getState()));
                    tvHcks3.setBackgroundColor(getRoadColor(lkcx.getState()));
                    tvHcks4.setBackgroundColor(getRoadColor(lkcx.getState()));
                    break;
                case 5:
                    tvHcgs1.setBackgroundColor(getRoadColor(lkcx.getState()));
                    tvHcgs2.setBackgroundColor(getRoadColor(lkcx.getState()));
                    break;
                case 6:
                    tvTcc.setBackgroundColor(getRoadColor(lkcx.getState()));
                    break;
            }
        }

    }


    private int getRoadColor(int state) {
        switch (state) {
            case 1:
                return Color.parseColor("#6ab82e");
            case 2:
                return Color.parseColor("#ece93a");
            case 3:
                return Color.parseColor("#f49b25");
            case 4:
                return Color.parseColor("#e33532");
            case 5:
                return Color.parseColor("#b01e23");
            default:
                return Color.WHITE;
        }
    }

    @OnClick(R.id.iv_flash)
    public void onViewClicked() {
        setVolley_Weather();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (volleyTo!=null){
            volleyTo.setLoop(false);
            volleyTo = null;
        }
    }
}
