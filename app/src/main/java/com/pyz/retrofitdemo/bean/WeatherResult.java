package com.pyz.retrofitdemo.bean;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by pyz on 2016/5/4.
 */
public class WeatherResult {

    @SerializedName("status")
    public String status;
    @SerializedName("daily_forecast")
    public ArrayList<DailyForecast> daily_forecast;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<DailyForecast> getDaily_forecast() {
        return daily_forecast;
    }

    public void setDaily_forecast(ArrayList<DailyForecast> daily_forecast) {
        this.daily_forecast = daily_forecast;
    }
}
