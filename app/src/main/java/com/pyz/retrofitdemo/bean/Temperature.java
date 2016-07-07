package com.pyz.retrofitdemo.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by pyz on 2016/5/4.
 */
public class Temperature {
    @SerializedName("max")
    public String maxTem;
    @SerializedName("min")
    public String minTem;

    public String getMaxTem() {
        return maxTem;
    }

    public void setMaxTem(String maxTem) {
        this.maxTem = maxTem;
    }

    public String getMinTem() {
        return minTem;
    }

    public void setMinTem(String minTem) {
        this.minTem = minTem;
    }
}
