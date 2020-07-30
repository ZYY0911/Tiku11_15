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
public class SJFXFragment6 extends Fragment {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.bar_chart)
    BarChart barChart;
    Unbinder unbinder;
    private Map<Integer, Integer> mapyesTime;
    private List<BarEntry> barEntries;
    private List<Integer> colors;
    private List<String> xValue;
    private int size;

    public SJFXFragment6(Map<Integer, Integer> mapyesTime) {
        this.mapyesTime = mapyesTime;
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
        tvTitle.setText("每日时段内车辆违章的占比统计");
        for (int i = 0; i < 12; i++) {
            size += mapyesTime.get(i);
        }

        barEntries = new ArrayList<>();
        colors = new ArrayList<>();
        xValue = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            barEntries.add(new BarEntry(i, (float) mapyesTime.get(i) / (float) size));
        }
        colors.add(Color.parseColor("#405DA8"));
        colors.add(Color.parseColor("#220D14"));
        colors.add(Color.parseColor("#08005E"));
        colors.add(Color.parseColor("#3E4F22"));
        colors.add(Color.parseColor("#A194CA"));
        colors.add(Color.parseColor("#64812C"));
        colors.add(Color.parseColor("#78282A"));
        colors.add(Color.parseColor("#EEB212"));
        colors.add(Color.parseColor("#3D6AA5"));
        colors.add(Color.parseColor("#D15311"));
        colors.add(Color.parseColor("#782761"));
        colors.add(Color.parseColor("#4E161F"));
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
        legend.setDrawInside(true);//绘制在图标内
        setX();
        setY();
        barChart.getDescription().setEnabled(false);
        barChart.getLegend().setEnabled(false);
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
                DecimalFormat format = new DecimalFormat("0.00");
                return format.format(value * 100) + "%";
            }
        });
        yAxis1.setLabelCount(7);
    }

    private void setX() {
        xValue.add("0:00:01-2:00:00");
        xValue.add("2:00:01-4:00:00");
        xValue.add("4:00:01-6:00:00");
        xValue.add("6:00:01-8:00:00");
        xValue.add("8:00:01-10:00:00");
        xValue.add("10:00:01-12:00:00");
        xValue.add("12:00:01-14:00:00");
        xValue.add("14:00:01-16:00:00");
        xValue.add("16:00:01-18:00:00");
        xValue.add("18:00:01-20:00:00");
        xValue.add("20:00:01-22:00:00");
        xValue.add("22:00:01-24:00:00");
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xValue));
        xAxis.setDrawGridLines(false);
        xAxis.setLabelRotationAngle(-50);
        xAxis.setLabelCount(xValue.size());
        xAxis.setTextSize(20f);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
