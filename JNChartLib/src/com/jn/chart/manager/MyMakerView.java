package com.jn.chart.manager;

import android.content.Context;
import android.widget.TextView;

import com.jn.chart.components.MarkerView;
import com.jn.chart.data.Entry;
import com.jn.chart.highlight.Highlight;

/**
 * @Author: pyz
 * @Package: com.pyz.myproject.utils
 * @Description: TODO
 * @Project: JNChartDemo
 * @Date: 2016/7/20 17:50
 */
public class MyMakerView extends MarkerView {
    /**
     * Constructor. Sets up the MarkerView with a custom layout resource.
     *
     * @param context
     * @param layoutResource the layout resource to use for the MarkerView
     */
    private TextView tvContent;

    public MyMakerView (Context context, int layoutResource) {
        super(context, layoutResource);
//        tvContent = (TextView) findViewById(R.id.tvContent);
    }

    /* 每次畫 MakerView 時都會觸發這個 Callback 方法，通常會在此方法內更新 View 的內容 */
    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        tvContent.setText("" + e.getVal());

    }
    /*
    * offset 是以點到的那個點作為 (0,0) 中心然後往右下角畫出來
    * 所以如果要顯示在點的上方
    * X=寬度的一半，負數
    * Y=高度的負數
     */
    @Override
    public int getXOffset(float xpos) {
        return -(getWidth() / 2);
    }

    @Override
    public int getYOffset(float ypos) {
        return -getHeight();
    }



}
