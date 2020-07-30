package com.example.tiku11_15.fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tiku11_15.R;
import com.example.tiku11_15.activity.Z_SHZSActivity;
import com.example.tiku11_15.bean.SHZS;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @LogIn Name win10
 * @Create by 张瀛煜 on 2020/7/28 at 21:19 ：）
 */
@SuppressLint("ValidFragment")
public class KQZLFragment extends Fragment {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.bar_chart)
    BarChart barChart;
    Unbinder unbinder;
    private Z_SHZSActivity activity;
    private List<SHZS> shzsList;
    private boolean isLoop = true;
    private List<BarEntry> barEntries;
    private List<Integer> integers;
    private List<String> xValue;

    public KQZLFragment(Z_SHZSActivity activity) {
        this.activity = activity;
    }

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            setData();
            return false;
        }
    });

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.kqzl_fragment, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        new Thread(new Runnable() {
            @Override
            public void run() {
                do {
                    handler.sendEmptyMessage(0);
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } while (isLoop);
            }
        }).start();
    }

    private void setData() {
        shzsList = activity.shzsList;
        if (shzsList.size() == 0) {
            return;
        }
        if (barEntries == null) {
            barEntries = new ArrayList<>();
            integers = new ArrayList<>();
            xValue = new ArrayList<>();
        } else {
            barEntries.clear();
            integers.clear();
            xValue.clear();
        }
        for (int i = 0; i < shzsList.size(); i++) {
            SHZS shzs = shzsList.get(i);
            barEntries.add(new BarEntry(i,shzs.getPm25()));
            integers.add(shzs.getPm25());
            xValue.add(((i+1)*3)+"");
        }
        BarDataSet dataSet = new BarDataSet(barEntries,"");
        Collections.sort(integers, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1-o2;
            }
        });
        tvTitle.setText("过去1分钟空气质量最差值："+integers.get(integers.size()-1));
        dataSet.setColor(Color.GRAY);
        BarData data = new BarData(dataSet);
        barChart.getDescription().setText("(S)");
        barChart.getLegend().setEnabled(false);
        YAxis yAxis = barChart.getAxisLeft();
        yAxis.setDrawAxisLine(false);
        yAxis.setAxisMaximum(320);
        yAxis.setAxisMinimum(0);
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xValue));
        xAxis.setLabelCount(xValue.size());
        xAxis.setGranularity(1f);
        xAxis.setDrawGridLines(false);
        barChart.getAxisRight().setEnabled(false);
        barChart.setData(data);
        barChart.setTouchEnabled(false);
        barChart.invalidate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isLoop = false;
        unbinder.unbind();
    }
}
