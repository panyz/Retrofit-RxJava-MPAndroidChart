package com.pyz.retrofitdemo.model;

import android.content.Context;

import com.pyz.retrofitdemo.RetrofitWrapper;
import com.pyz.retrofitdemo.bean.WeatherInfo;
import com.pyz.retrofitdemo.bean.WeatherInfoReq;
import com.pyz.retrofitdemo.netrequest.NetRequest;

import retrofit2.Call;
import rx.Observable;

/**
 * Created by pyz on 2016/5/4.
 */
public class WeatherInfoModel {
    private static  WeatherInfoModel weatherInfoModel;
    private NetRequest netRequest;

    public WeatherInfoModel(Context context) {
        netRequest = (NetRequest) RetrofitWrapper.getInstance().create(NetRequest.class);
    }

    public static WeatherInfoModel getInstance(Context context) {
        if (weatherInfoModel == null) {
            weatherInfoModel = new WeatherInfoModel(context);
        }
        return weatherInfoModel;
    }

    /**
     * 查询天气
     *
     * @param weatherInfoReq
     * @return
     */
    public Call<WeatherInfo> queryWeather(WeatherInfoReq weatherInfoReq) {
        Call<WeatherInfo> infoCall = netRequest.getWeather(weatherInfoReq.apiKey, weatherInfoReq.city);
        return infoCall;
    }

    public Observable<WeatherInfo> queryWeatherByRxJava(WeatherInfoReq weatherInfoReq) {
        Observable<WeatherInfo> infoCall = netRequest.getWeatherByRxJava(weatherInfoReq.apiKey, weatherInfoReq.city);
        return infoCall;
    }
}
