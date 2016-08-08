package com.jn.chart.interfaces.dataprovider;

import com.jn.chart.data.BubbleData;

public interface BubbleDataProvider extends BarLineScatterCandleBubbleDataProvider {

    BubbleData getBubbleData();
}
