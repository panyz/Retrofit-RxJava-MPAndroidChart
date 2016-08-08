package com.jn.chart.manager;

import com.jn.chart.data.Entry;
import com.jn.chart.formatter.ValueFormatter;
import com.jn.chart.utils.ViewPortHandler;

import java.text.DecimalFormat;

/**
 * @Author: pyz
 * @Package: com.jn.chart.manager
 * @Description: TODO
 * @Project: Retrofit-RxJavaDemo
 * @Date: 2016/8/8 14:51
 */
public class MyValueFormatter implements ValueFormatter {
    protected DecimalFormat mFormat;

    public MyValueFormatter() {
        mFormat = new DecimalFormat("###,###,##0");
    }

    public MyValueFormatter(DecimalFormat format) {
        this.mFormat = format;
    }

    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
        return mFormat.format(value) + " ℃";

    }

}
