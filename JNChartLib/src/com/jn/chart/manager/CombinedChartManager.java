package com.jn.chart.manager;

import android.content.Context;
import android.graphics.Color;

import com.jn.chart.animation.Easing;
import com.jn.chart.charts.CombinedChart;
import com.jn.chart.components.Legend;
import com.jn.chart.components.XAxis;
import com.jn.chart.components.YAxis;
import com.jn.chart.data.BarData;
import com.jn.chart.data.BarDataSet;
import com.jn.chart.data.BarEntry;
import com.jn.chart.data.CombinedData;
import com.jn.chart.data.Entry;
import com.jn.chart.data.LineData;
import com.jn.chart.data.LineDataSet;
import com.jn.chart.interfaces.datasets.IBarDataSet;
import com.jn.chart.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

/**
 * @Author: pyz
 * @Package: com.pyz.myproject.utils
 * @Description:折线柱状混合图管理类
 * @Project: JNChartDemo
 * @Date: 2016/7/21 11:13
 */
public class CombinedChartManager {


    private static String lineUnit = null;
    private static String barUnit = null;

    public static void initCombinedChart(Context context, CombinedChart combinedChart,
                                         ArrayList<String> xValues, ArrayList<Entry> yValues2Line, ArrayList<BarEntry> yValues2Bar) {
        //初始化图表的样式
        initDataStyle(context,combinedChart);

        //设置折线的样式
        LineDataSet lineDataSet = new LineDataSet(yValues2Line, lineUnit);
        lineDataSet.setHighlightEnabled(false);
        lineDataSet.setColor(Color.RED);
        lineDataSet.setCircleColor(Color.RED);
        lineDataSet.setDrawValues(true);
        //左边的Y轴为折线图的数据依赖
        lineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSet);
        //构建一个LineData  将dataSets放入
        LineData lineData = new LineData(xValues, dataSets);


        //设置柱状的样式
        BarDataSet barDataSet = new BarDataSet(yValues2Bar, barUnit);
        barDataSet.setColor(Color.parseColor("#7EC0EE"));
        barDataSet.setDrawValues(false);
        barDataSet.setValueTextColor(Color.RED);
        //右边的Y轴为柱状图的数据依赖
        barDataSet.setAxisDependency(YAxis.AxisDependency.RIGHT);

        ArrayList<IBarDataSet> barDataSets = new ArrayList<>();
        barDataSets.add(barDataSet);

        //构建一个barData  将dataSets放入
        BarData barData = new BarData(xValues, barDataSets);

        CombinedData combinedData = new CombinedData(xValues);
        combinedData.setData(barData);
        combinedData.setData(lineData);

        //将数据插入
        combinedChart.setData(combinedData);

        //设置动画效果
        combinedChart.animateY(2000, Easing.EasingOption.Linear);
        combinedChart.animateX(2000, Easing.EasingOption.Linear);
        combinedChart.invalidate();

    }

    private static void initDataStyle(Context context, CombinedChart combinedChart) {

        //设置图表是否支持触控操作
        combinedChart.setTouchEnabled(true);
        //设置图表是否支持缩放操作
        combinedChart.setScaleEnabled(false);
        //设置点击折线点时，显示其数值
//        MyMakerView mv = new MyMakerView(context, R.layout.item_mark_layout);
//        combinedChart.setMarkerView(mv);
        //设置折线的描述的样式（默认在图表的左下角）
        Legend title = combinedChart.getLegend();
        title.setForm(Legend.LegendForm.SQUARE);
        //设置x轴的样式
        XAxis xAxis = combinedChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisLineColor(Color.parseColor("#66CDAA"));
        xAxis.setAxisLineWidth(5);
        xAxis.setDrawGridLines(false);
        //设置是否显示x轴
        xAxis.setEnabled(true);

        //设置左边y轴的样式
        YAxis yAxisLeft = combinedChart.getAxisLeft();
        yAxisLeft.setTextColor(Color.RED);
        yAxisLeft.setAxisLineColor(Color.parseColor("#66CDAA"));
        yAxisLeft.setAxisLineWidth(5);
        yAxisLeft.setDrawGridLines(false);


        //设置右边y轴的样式
        YAxis yAxisRight = combinedChart.getAxisRight();
        yAxisRight.setTextColor(Color.parseColor("#7EC0EE"));
        yAxisRight.setAxisLineColor(Color.parseColor("#66CDAA"));
        yAxisRight.setAxisLineWidth(5);
        yAxisRight.setDrawGridLines(false);
        yAxisRight.setEnabled(true);

    }

    public static void setLineUnit(String lineUnit) {
        CombinedChartManager.lineUnit = lineUnit;
    }

    public static void setBarUnit(String barUnit) {
        CombinedChartManager.barUnit = barUnit;
    }
}
