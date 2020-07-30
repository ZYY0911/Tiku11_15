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
public class SJFXFragment4 extends Fragment {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.bar_chart)
    BarChart barChart;
    Unbinder unbinder;
    private Map<String, Integer> mapyesAge, mapnoAge;
    private List<BarEntry> barEntries;
    private List<Integer> colors;
    private List<String> xValue;
    private int allyes, allno;

    public SJFXFragment4(Map<String, Integer> mapyesAge, Map<String, Integer> mapnoAge) {
        this.mapyesAge = mapyesAge;
        this.mapnoAge = mapnoAge;
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
        tvTitle.setText("年龄群体车辆违章的占比统计");
        barEntries = new ArrayList<>();
        colors = new ArrayList<>();
        xValue = new ArrayList<>();
        for (int i = 9; i > 4; i--) {
            allyes += mapyesAge.get(i + "");
        }
        for (int i = 9; i > 4; i--) {
            allno += mapnoAge.get(i + "");
        }
        for (int i = 0, j = 9; i < 5; i++, j--) {
            barEntries.add(new BarEntry(i, new float[]{(float) mapyesAge.get(j + "") / (float) allyes, (float) mapnoAge.get(j + "") / (float) allno}));
        }
        colors.add(Color.parseColor("#D95A10"));
        colors.add(Color.parseColor("#55850A"));
       BarDataSet  dataSet = new BarDataSet(barEntries, "");
        dataSet.setColors(colors);
        dataSet.setStackLabels(new String[]{"有违章", "无违章"});
        dataSet.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                DecimalFormat format = new DecimalFormat("00.0");
                return format.format(value * 100) + "%";
            }
        });
      BarData  data = new BarData(dataSet);
        data.setBarWidth(0.4f);
        data.setValueTextSize(20f);
        data.setValueTextColor(Color.RED);
        barChart.setData(data);
        Legend legend = barChart.getLegend();
        legend.setTextSize(25);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
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
                return format.format(value * 1000) ;
            }
        });
        yAxis1.setLabelCount(7);
    }

    private void setX() {
        xValue.add("90后");
        xValue.add("80后");
        xValue.add("70后");
        xValue.add("60后");
        xValue.add("50后");
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xValue));
        xAxis.setDrawGridLines(false);
        xAxis.setLabelCount(5);
        xAxis.setTextSize(10f);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
