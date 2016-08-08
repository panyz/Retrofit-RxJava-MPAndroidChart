package com.jn.chart.interfaces.dataprovider;

import com.jn.chart.data.CandleData;

public interface CandleDataProvider extends BarLineScatterCandleBubbleDataProvider {

    CandleData getCandleData();
}
