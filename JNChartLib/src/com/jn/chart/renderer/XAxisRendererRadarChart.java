
package com.jn.chart.renderer;

import android.graphics.Canvas;
import android.graphics.PointF;

import com.jn.chart.charts.RadarChart;
import com.jn.chart.components.XAxis;
import com.jn.chart.utils.Utils;
import com.jn.chart.utils.ViewPortHandler;

public class XAxisRendererRadarChart extends XAxisRenderer {

    private RadarChart mChart;

    public XAxisRendererRadarChart(ViewPortHandler viewPortHandler, XAxis xAxis, RadarChart chart) {
        super(viewPortHandler, xAxis, null);

        mChart = chart;
    }

    @Override
    public void renderAxisLabels(Canvas c) {

        if (!mXAxis.isEnabled() || !mXAxis.isDrawLabelsEnabled())
            return;

        final float labelRotationAngleDegrees = mXAxis.getLabelRotationAngle();
        final PointF drawLabelAnchor = new PointF(0.5f, 0.0f);

        mAxisLabelPaint.setTypeface(mXAxis.getTypeface());
        mAxisLabelPaint.setTextSize(mXAxis.getTextSize());
        mAxisLabelPaint.setColor(mXAxis.getTextColor());

        float sliceangle = mChart.getSliceAngle();

        // calculate the factor that is needed for transforming the value to
        // pixels
        float factor = mChart.getFactor();

        PointF center = mChart.getCenterOffsets();

        int mod = mXAxis.mAxisLabelModulus;
        for (int i = 0; i < mXAxis.getValues().size(); i += mod) {
            String label = mXAxis.getValues().get(i);

            float angle = (sliceangle * i + mChart.getRotationAngle()) % 360f;

            PointF p = Utils.getPosition(center, mChart.getYRange() * factor
                    + mXAxis.mLabelRotatedWidth / 2f, angle);

            drawLabel(c, label, i, p.x, p.y - mXAxis.mLabelRotatedHeight / 2.f,
                    drawLabelAnchor, labelRotationAngleDegrees);
        }
    }

	/**
	 * XAxis LimitLines on RadarChart not yet supported.
	 *
	 * @param c
	 */
	@Override
	public void renderLimitLines(Canvas c) {
		// this space intentionally left blank
	}
}
