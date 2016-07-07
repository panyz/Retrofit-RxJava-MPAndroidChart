package com.pyz.retrofitdemo.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by pyz on 2016/5/4.
 */
public class WeatherInfo {
    @SerializedName("HeWeather data service 3.0")
    public List<WeatherResult> HeWeatherDataList;

    public List<WeatherResult> getHeWeatherDataList() {
        return HeWeatherDataList;
    }

    public void setHeWeatherDataList(List<WeatherResult> heWeatherDataList) {
        HeWeatherDataList = heWeatherDataList;
    }
}
