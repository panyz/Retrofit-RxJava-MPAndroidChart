package com.jn.chart.data.realm.implementation;

import com.jn.chart.data.CandleData;
import com.jn.chart.data.realm.base.RealmUtils;
import com.jn.chart.interfaces.datasets.ICandleDataSet;

import java.util.List;

import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Created by Philipp Jahoda on 19/12/15.
 */
public class RealmCandleData extends CandleData {

    public RealmCandleData(RealmResults<? extends RealmObject> result, String xValuesField, List<ICandleDataSet> dataSets) {
        super(RealmUtils.toXVals(result, xValuesField), dataSets);
    }
}
