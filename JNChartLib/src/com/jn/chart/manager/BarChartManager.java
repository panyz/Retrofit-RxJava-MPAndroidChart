package com.jn.chart.manager;

import android.content.Context;
import android.graphics.Color;

import com.jn.chart.animation.Easing;
import com.jn.chart.charts.BarChart;
import com.jn.chart.components.Legend;
import com.jn.chart.components.XAxis;
import com.jn.chart.components.YAxis;
import com.jn.chart.data.BarData;
import com.jn.chart.data.BarDataSet;
import com.jn.chart.data.BarEntry;
import com.jn.chart.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;

/**
 * @Author: pyz
 * @Package: com.pyz.myproject.utils
 * @Description: TODO
 * @Project: JNChartDemo
 * @Date: 2016/7/21 10:09
 */
public class BarChartManager {
    private static String unit = null;

    public static void initBarChart(Context context, BarChart barChart,
                                    ArrayList<String> xValues, ArrayList<BarEntry> yValues) {
        initDataStyle(context,barChart);
        //设置柱状图的样式
        BarDataSet dataSet = new BarDataSet(yValues, unit);
        dataSet.setColor(Color.parseColor("#7EC0EE"));
        dataSet.setDrawValues(true);
        dataSet.setValueTextColor(Color.RED);

//        dataSet.setValueFormatter(new PercentFormatter(new DecimalFormat("%").format()));

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(dataSet);

        //构建一个barData  将dataSets放入
        BarData barData = new BarData(xValues, dataSets);
        //将数据插入
        barChart.setData(barData);

        //设置动画效果
        barChart.animateY(2000, Easing.EasingOption.Linear);
//        barChart.animateX(2000, Easing.EasingOption.Linear);
        barChart.invalidate();
    }

    /**
     *  @Description:初始化图表的样式
     * @param context
     * @param barChart

     */
    private static void initDataStyle(Context context, BarChart barChart) {
        //设置图表是否支持触控操作
        barChart.setTouchEnabled(false);
        //设置点击折时，显示其数值
  /*      MyMakerView mv = new MyMakerView(context, R.layout.item_mark_layout);
        barChart.setMarkerView(mv);*/
        //设置柱状的描述的样式（默认在图表的左下角）
        Legend title = barChart.getLegend();
        title.setForm(Legend.LegendForm.SQUARE);
        //设置x轴的样式
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisLineColor(Color.parseColor("#66CDAA"));
        xAxis.setAxisLineWidth(5);
        xAxis.setDrawGridLines(false);
        //设置是否显示x轴
        xAxis.setEnabled(true);

        //设置左边y轴的样式
        YAxis yAxisLeft = barChart.getAxisLeft();
        yAxisLeft.setAxisLineColor(Color.parseColor("#66CDAA"));
        yAxisLeft.setAxisLineWidth(5);
        yAxisLeft.setDrawGridLines(false);

        //设置右边y轴的样式
        YAxis yAxisRight = barChart.getAxisRight();
        yAxisRight.setEnabled(false);

    }

    /**
     * 设置单位值
     * @param barUnit
     */
    public static void  setUnit(String barUnit){
        unit = barUnit;
    }
}
