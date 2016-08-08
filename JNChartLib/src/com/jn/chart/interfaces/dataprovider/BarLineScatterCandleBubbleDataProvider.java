package com.jn.chart.interfaces.dataprovider;

import com.jn.chart.components.YAxis.AxisDependency;
import com.jn.chart.data.BarLineScatterCandleBubbleData;
import com.jn.chart.utils.Transformer;

public interface BarLineScatterCandleBubbleDataProvider extends ChartInterface {

    Transformer getTransformer(AxisDependency axis);
    int getMaxVisibleCount();
    boolean isInverted(AxisDependency axis);
    
    int getLowestVisibleXIndex();
    int getHighestVisibleXIndex();

    BarLineScatterCandleBubbleData getData();
}
