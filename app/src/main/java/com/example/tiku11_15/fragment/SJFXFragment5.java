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
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
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
 * @Create by 张瀛煜 on 2020/7/29 at 14:55 ：）
 */
@SuppressLint("ValidFragment")
public class SJFXFragment5 extends Fragment {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.bar_chart)
    BarChart barChart;
    Unbinder unbinder;
    private Map<String, Integer> mapyesSex, mapnoSex;
    private List<BarEntry> barEntries;
    private List<Integer> colors;
    private List<String> xValue;
    private int all_men, all_wamne;
    private float man_yes, man_no, weman_yes, weman_no;

    public SJFXFragment5(Map<String, Integer> mapyesSex, Map<String, Integer> mapnoSex) {
        this.mapnoSex = mapnoSex;
        this.mapyesSex = mapyesSex;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sjfc_fragment4, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tvTitle.setText("平台上男性和女性有无车辆违章的占比统计");
        barEntries = new ArrayList<>();
        colors = new ArrayList<>();
        xValue = new ArrayList<>();
        all_men = mapnoSex.get("男") + mapyesSex.get("男");
        all_wamne = mapnoSex.get("女") + mapyesSex.get("女");
        man_yes = (float) mapyesSex.get("男") / (float) all_men;
        man_no = 1 - man_yes;
        weman_yes = (float) mapyesSex.get("女") / (float) all_wamne;
        weman_no = 1 - weman_yes;
        barEntries.add(new BarEntry(0, new float[]{weman_no, weman_yes}));
        barEntries.add(new BarEntry(1, new float[]{man_no, man_yes}));
        colors.add(Color.parseColor("#D95A10"));
        colors.add(Color.parseColor("#55850A"));
        BarDataSet dataSet = new BarDataSet(barEntries, "");
        dataSet.setColors(colors);
        dataSet.setStackLabels(new String[]{"有违法", "无违法"});
        dataSet.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                DecimalFormat format = new DecimalFormat("00.0");
                return format.format(value * 100) + "%";
            }
        });
        BarData data = new BarData(dataSet);
        data.setBarWidth(0.4f);
        data.setValueTextSize(20f);
        data.setValueTextColor(Color.RED);
        barChart.setData(data);
        Legend legend = barChart.getLegend();
        legend.setTextSize(25);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        legend.setFormSize(30);
        legend.setDrawInside(true);
        setX();
        setY();
        barChart.getDescription().setEnabled(false);
        barChart.setTouchEnabled(false);
    }
    private void setY() {
        YAxis yAxis = barChart.getAxisRight();
        yAxis.setStartAtZero(true);
        yAxis.setEnabled(false);

        YAxis yAxis1 = barChart.getAxisLeft();
        yAxis1.setTextSize(20f);
        yAxis1.setStartAtZero(true);
        yAxis1.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                DecimalFormat format = new DecimalFormat("0");
                return format.format(value * 1000);
            }
        });
        yAxis1.setLabelCount(7);
    }

    private void setX() {
        xValue.add("女性");
        xValue.add("男性");
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xValue));
        xAxis.setDrawGridLines(false);
        xAxis.setLabelCount(2);
        xAxis.setTextSize(10f);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
