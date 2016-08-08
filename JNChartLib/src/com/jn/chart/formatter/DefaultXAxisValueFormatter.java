package com.jn.chart.formatter;

import com.jn.chart.utils.ViewPortHandler;

/**
 * Created by Philipp Jahoda on 14/09/15.
 * Default formatter class for adjusting x-values before drawing them.
 * This simply returns the original value unmodified.
 */
public class DefaultXAxisValueFormatter implements XAxisValueFormatter {

    @Override
    public String getXValue(String original, int index, ViewPortHandler viewPortHandler) {
        return original; // just return original, no adjustments
    }
}
