package com.jn.chart.manager;

import com.jn.chart.components.YAxis;
import com.jn.chart.formatter.YAxisValueFormatter;

import java.text.DecimalFormat;

/**
 * @Author: pyz
 * @Package: com.pyz.retrofitdemo
 * @Description: TODO
 * @Project: Retrofit-RxJavaDemo
 * @Date: 2016/8/8 11:59
 */
public class MyYValueFormatter implements YAxisValueFormatter {
    private DecimalFormat mFormat;

    public MyYValueFormatter(){
        mFormat = new DecimalFormat("###,###,###,##0");
    }

    @Override
    public String getFormattedValue(float value, YAxis yAxis) {
        return mFormat.format(value)+"℃";
    }
}
