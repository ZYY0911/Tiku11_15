package com.example.tiku11_15.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.tiku11_15.R;
import com.example.tiku11_15.adapter.SHZSAdapter;
import com.example.tiku11_15.bean.SHZS;
import com.example.tiku11_15.bean.Weather;
import com.example.tiku11_15.fragment.COFragment;
import com.example.tiku11_15.fragment.KQZLFragment;
import com.example.tiku11_15.fragment.SDFragment;
import com.example.tiku11_15.fragment.WDFragment;
import com.example.tiku11_15.net.VolleyLo;
import com.example.tiku11_15.net.VolleyTo;
import com.example.tiku11_15.util.MyUtils;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @LogIn Name win10
 * @Create by 张瀛煜 on 2020/7/28 at 14:27 ：）
 */
public class Z_SHZSActivity extends BaseActivity {
    @BindView(R.id.iv_flash)
    ImageView ivFlash;
    @BindView(R.id.line_chart)
    LineChart lineChart;
    @BindView(R.id.gird_view)
    GridView girdView;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.linear_layout)
    LinearLayout linearLayout;
    @BindView(R.id.tv_now_temperature)
    TextView tvNowTemperature;
    @BindView(R.id.tv_today_info)
    TextView tvTodayInfo;
    private List<Weather> weathers;
    private VolleyTo volleyTo;
    public List<SHZS> shzsList;
    private SHZSAdapter adapter;
    private List<Fragment> fragments;
    private String arr[] = {"空气质量", "湿度", "相对湿度", "二氧化碳"};

    @Override
    public int getLayout() {
        return R.layout.shzs_layout;
    }

    @Override
    public String setTitle() {
        return "生活助手";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        fragments = new ArrayList<>();
        shzsList = new ArrayList<>();
        setVolley_Weather();
        setVolley_Sense();
        setViewPager();
    }

    private void setVolley_Sense() {
        volleyTo = new VolleyTo();
        volleyTo.setUrl("get_all_sense")
                .setJsonObject("UserName", "user1")
                .setLoop(true)
                .setTime(3000)
                .setVolleyLo(new VolleyLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        shzsList.add(new Gson().fromJson(jsonObject.toString(), SHZS.class));
                        if (shzsList.size() == 21) {
                            shzsList.remove(0);
                        }
                        setSenseView();
                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }).start();
    }

    private void setViewPager() {
        fragments.add(new KQZLFragment(this));
        fragments.add(new WDFragment(this));
        fragments.add(new SDFragment(this));
        fragments.add(new COFragment(this));
        viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager()));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                for (int j = 0; j < linearLayout.getChildCount(); j++) {
                    TextView textView = (TextView) linearLayout.getChildAt(j);
                    if (i == j) {
                        if (i == fragments.size() - 1) {
                            textView.setBackgroundResource(R.drawable.blue_bg);
                        } else {
                            textView.setBackgroundResource(R.drawable.bg);
                        }
                    } else {
                        textView.setBackgroundResource(R.drawable.no_bg);
                    }
                }

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        for (int i = 0; i < arr.length; i++) {
            TextView textView = new TextView(this);
            textView.setTextColor(Color.BLACK);
            textView.setTextSize(20f);
            textView.setText(arr[i]);
            textView.setGravity(Gravity.CENTER);
            textView.setWidth(200);
            if (i == 0) {
                textView.setBackgroundResource(R.drawable.bg);
            }
            linearLayout.addView(textView);
        }
    }

    private void setSenseView() {
        if (adapter == null) {
            adapter = new SHZSAdapter(this, shzsList);
            girdView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    private void setVolley_Weather() {
        VolleyTo volleyTo = new VolleyTo();
        volleyTo.setUrl("get_weather_info")
                .setJsonObject("UserName", "user1")
                .setDialog(this)
                .setVolleyLo(new VolleyLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        tvNowTemperature.setText(jsonObject.optInt("temperature") + "°");
                        JSONArray jsonArray = jsonObject.optJSONArray("ROWS_DETAIL");
                        tvTodayInfo.setText("今天：" + jsonArray.optJSONObject(1).optString("interval") + "℃");
                        weathers = new Gson().fromJson(jsonArray.toString(), new TypeToken<List<Weather>>() {
                        }.getType());
                        setWeatherChart();
                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }).start();
    }

    private void setWeatherChart() {
        List<Entry> maxEntry = new ArrayList<>();
        List<Entry> minEntry = new ArrayList<>();
        for (int i = 0; i < weathers.size(); i++) {
            String[] arr = weathers.get(i).getInterval().split("~");
            maxEntry.add(new Entry(i, Integer.parseInt(arr[1])));
            minEntry.add(new Entry(i, Integer.parseInt(arr[0])));
        }
        LineDataSet dataSet = new LineDataSet(maxEntry, "");
        LineDataSet dataSet1 = new LineDataSet(minEntry, "");
        dataSet.setCircleColor(Color.RED);
        dataSet.setColor(Color.RED);
        dataSet.setDrawCircleHole(false);
        dataSet.setCircleHoleRadius(5);
        dataSet.setLineWidth(4);
        dataSet1.setCircleColor(Color.BLUE);
        dataSet1.setColor(Color.BLUE);
        dataSet1.setDrawCircleHole(false);
        dataSet1.setCircleHoleRadius(5);
        dataSet1.setLineWidth(4);
        List<ILineDataSet> iLineDataSets = new ArrayList<>();
        iLineDataSets.add(dataSet);
        iLineDataSets.add(dataSet1);
        LineData data = new LineData(iLineDataSets);
        lineChart.setData(data);
        lineChart.getAxisRight().setEnabled(false);
        YAxis yAxis = lineChart.getAxisLeft();
        yAxis.setDrawAxisLine(false);
        yAxis.setTextColor(Color.TRANSPARENT);
        lineChart.animateXY(1500, 1000);
        lineChart.getXAxis().setEnabled(false);
        lineChart.getLegend().setEnabled(false);
        lineChart.getDescription().setEnabled(false);
        lineChart.setTouchEnabled(false);
        lineChart.invalidate();
    }

    @OnClick(R.id.iv_flash)
    public void onViewClicked() {
        setVolley_Weather();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (volleyTo != null) {
            volleyTo.setLoop(false);
            volleyTo = null;
        }
    }

    class PagerAdapter extends FragmentPagerAdapter {

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return fragments.get(i);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }
}
