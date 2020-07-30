package com.example.tiku11_15.fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tiku11_15.R;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @LogIn Name win10
 * @Create by 张瀛煜 on 2020/7/29 at 14:51 ：）
 */
@SuppressLint("ValidFragment")
public class SJFXFragment3 extends Fragment {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.bar_chart)
    HorizontalBarChart barChart;
    Unbinder unbinder;
    private List<BarEntry> barEntries;
    private List<String> xValue;
    private List<Integer> colors;
    private int a, b, c;
    private float A, B, C;

    public SJFXFragment3(int a, int b, int c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sjfc_fragment3, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tvTitle.setText("违章车辆的违章次数占比分布统计图");
        A = (float) a / (float) (a + b + c) * 100;
        B = (float) b / (float) (a + b + c) * 100;
        C = (float) c / (float) (a + b + c) * 100;
        barEntries = new ArrayList<>();
        xValue = new ArrayList<>();
        colors = new ArrayList<>();
        barEntries.add(new BarEntry(0, A));
        barEntries.add(new BarEntry(1, B));
        barEntries.add(new BarEntry(2, C));
        colors.add(Color.parseColor("#7DC53E"));
        colors.add(Color.parseColor("#4068AE"));
        colors.add(Color.parseColor("#A8000A"));
        BarDataSet dataSet = new BarDataSet(barEntries, "");
        dataSet.setColors(colors);
        BarData data = new BarData(dataSet);
        data.setBarWidth(0.3f);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextColor(Color.RED);
        data.setValueTextSize(30);
        setX();
        setY();
        barChart.setData(data);
        barChart.setTouchEnabled(false);
        barChart.getLegend().setEnabled(false);
        barChart.invalidate();
        barChart.getDescription().setEnabled(false);
    }

    private void setY() {
        YAxis yAxis = barChart.getAxisLeft();
        yAxis.setTextSize(25f);
        yAxis.setStartAtZero(true);
        yAxis.setEnabled(false);
        YAxis yAxis1 = barChart.getAxisRight();
        yAxis1.setStartAtZero(true);
        yAxis1.setValueFormatter(new PercentFormatter());
        yAxis1.setLabelCount(8);
        yAxis1.setTextSize(10f);


    }

    private void setX() {
        xValue.add("1-2条违章");
        xValue.add("3-5条违章");
        xValue.add("5条以上违章");
        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xValue));
        xAxis.setTextSize(20f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setLabelRotationAngle(-30);
        xAxis.setDrawGridLines(false);
        xAxis.setLabelCount(3);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
