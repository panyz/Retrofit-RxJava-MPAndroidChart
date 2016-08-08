package com.jn.chart.data.realm.implementation;

import com.jn.chart.data.PieData;
import com.jn.chart.data.realm.base.RealmUtils;
import com.jn.chart.interfaces.datasets.IPieDataSet;

import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Created by Philipp Jahoda on 19/12/15.
 */
public class RealmPieData extends PieData {

    public RealmPieData(RealmResults<? extends RealmObject> result, String xValuesField, IPieDataSet dataSet) {
        super(RealmUtils.toXVals(result, xValuesField), dataSet);
    }
}
