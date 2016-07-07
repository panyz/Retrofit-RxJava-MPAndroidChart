package com.pyz.retrofitdemo.netrequest;

import com.pyz.retrofitdemo.bean.WeatherInfo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by pyz on 2016/4/29.
 */
public interface NetRequest {

    @GET("/heweather/weather/free?/")
    Call<WeatherInfo> getWeather(@Header("apiKey")String apiKey, @Query("city")String city);

    @GET("/heweather/weather/free?/")
    Observable<WeatherInfo> getWeatherByRxJava(@Header("apiKey")String apiKey, @Query("city")String city);

}
