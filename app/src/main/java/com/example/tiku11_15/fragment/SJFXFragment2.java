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
import com.example.tiku11_15.bean.SJFX;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IValueFormatter;
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
 * @Create by 张瀛煜 on 2020/7/29 at 14:44 ：）
 */
@SuppressLint("ValidFragment")
public class SJFXFragment2 extends Fragment {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.pie_chart)
    PieChart pieChart;
    Unbinder unbinder;
    private List<SJFX> yes;
    private Map<String ,Integer> map;
    private List<Integer> colors;
    private List<PieEntry> pieEntries;
    private float y,n;

    public SJFXFragment2(List<SJFX> yes, Map<String, Integer> map) {
        this.yes = yes;
        this.map = map;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sjfc_fragment1, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        pieEntries = new ArrayList<>();
        colors = new ArrayList<>();
        tvTitle.setText("有无“重复违章记录的车辆”的占比统计");
        y = (float) map.size()/(float) yes.size();
        n = 1- y;
        pieEntries.add(new PieEntry(y,"有重复违章"));
        pieEntries.add(new PieEntry(n,"无重复违章"));
        colors.add(Color.parseColor("#4572A6"));
        colors.add(Color.parseColor("#A94643"));
        PieDataSet dataSet = new PieDataSet(pieEntries,"");
        dataSet.setColors(colors);
        dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        dataSet.setValueLinePart1Length(1f);
        dataSet.setValueLinePart2Length(1f);
        dataSet.setValueLinePart1OffsetPercentage(70);
        dataSet.setValueLineColor(Color.BLACK);
        dataSet.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                DecimalFormat format = new DecimalFormat("0.0");
                return format.format(value)+"%";
            }
        });
        dataSet.setSliceSpace(3f);
        dataSet.setValueTextSize(25);
        PieData data = new PieData(dataSet);
        Legend legend = pieChart.getLegend();
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setTextSize(25);
        legend.setFormSize(25);
        pieChart.setData(data);
        pieChart.getDescription().setEnabled(false);
        pieChart.setDrawHoleEnabled(false);
        pieChart.setEntryLabelTextSize(25);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setUsePercentValues(true);
        pieChart.setTouchEnabled(false);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
