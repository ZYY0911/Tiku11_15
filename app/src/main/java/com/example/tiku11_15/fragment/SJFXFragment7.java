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
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @LogIn Name win10
 * @Create by 张瀛煜 on 2020/7/29 at 14:51 ：）
 */
@SuppressLint("ValidFragment")
public class SJFXFragment7 extends Fragment {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.bar_chart)
    HorizontalBarChart barChart;
    Unbinder unbinder;
    private List<Map.Entry<String, Integer>> lsitType;
    private int All;
    private List<BarEntry> barEntries;
    private List<String> xValue;
    private List<Integer> colors;

    public SJFXFragment7(List<Map.Entry<String, Integer>> lsitType) {
        this.lsitType = lsitType;
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
        tvTitle.setText("排名前十位的交通违法行为占比统计");
        barEntries = new ArrayList<>();
        xValue = new ArrayList<>();
        colors = new ArrayList<>();
        for (int i = 0; i < lsitType.size(); i++) {
            All += lsitType.get(i).getValue();
        }
        for (int i = 0, j = 9; i < lsitType.size(); i++, j--) {
            xValue.add(lsitType.get(j).getKey());
            barEntries.add(new BarEntry(j, (float) lsitType.get(i).getValue() / All));
        }
        colors.add(Color.parseColor("#A8000C"));
        colors.add(Color.parseColor("#3E6A9F"));
        colors.add(Color.parseColor("#62802B"));
        colors.add(Color.parseColor("#D3550F"));
        colors.add(Color.parseColor("#4B3566"));
        colors.add(Color.parseColor("#E8AA19"));
        colors.add(Color.parseColor("#A6B96F"));
        colors.add(Color.parseColor("#F09855"));
        colors.add(Color.parseColor("#7C95BA"));
        BarDataSet dataSet = new BarDataSet(barEntries, "");
        dataSet.setColors(colors);
        dataSet.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                DecimalFormat format = new DecimalFormat("0.0");
                return format.format(value * 100) + "%";
            }
        });
        BarData data = new BarData(dataSet);
        data.setBarWidth(0.6f);
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
        yAxis.setStartAtZero(true);
        yAxis.setEnabled(false);
        YAxis yAxis1 = barChart.getAxisRight();
        yAxis1.setStartAtZero(true);
        yAxis1.setLabelCount(8);
        yAxis1.setTextSize(10f);
        yAxis1.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                DecimalFormat format = new DecimalFormat("0.00");
                return format.format(value * 100) + "%";
            }
        });


    }

    private void setX() {
        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xValue));
        xAxis.setTextSize(20f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setLabelCount(xValue.size());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
