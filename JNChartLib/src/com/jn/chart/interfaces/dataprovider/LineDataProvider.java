package com.jn.chart.interfaces.dataprovider;

import com.jn.chart.components.YAxis;
import com.jn.chart.data.LineData;

public interface LineDataProvider extends BarLineScatterCandleBubbleDataProvider {

    LineData getLineData();

    YAxis getAxis(YAxis.AxisDependency dependency);
}
