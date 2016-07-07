package com.pyz.retrofitdemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.pyz.retrofitdemo.bean.DailyForecast;
import com.pyz.retrofitdemo.bean.Temperature;
import com.pyz.retrofitdemo.bean.WeatherInfo;
import com.pyz.retrofitdemo.bean.WeatherInfoReq;
import com.pyz.retrofitdemo.bean.WeatherResult;
import com.pyz.retrofitdemo.model.WeatherInfoModel;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends Activity {
    private Button request;
    private TextView tv;
    private WeatherInfoModel weatherInfoModel;

    private StringBuffer sb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        weatherInfoModel = WeatherInfoModel.getInstance(getApplicationContext());
        initViews();
        initParams();
        initEvent();
    }


    private void initEvent() {
        final Gson gson = new Gson();
         sb = new StringBuffer();
        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //创建访问的API请求
                weatherInfoModel.queryWeatherByRxJava(initParams())
                        .subscribeOn(Schedulers.io())// 指定观察者在io线程（第一次指定观察者线程有效）
                        .doOnSubscribe(new Action0() {//在启动订阅前（发送事件前）执行的方法
                            @Override
                            public void call() {
                                Log.e("RxJava","在网络请求前执行");
                            }
                        })
                        .flatMap(new Func1<WeatherInfo, Observable<WeatherResult>>() {
                            @Override
                            public Observable<WeatherResult> call(WeatherInfo weatherInfo) {
                                return Observable.from(weatherInfo.getHeWeatherDataList());
                            }
                        })
                        .flatMap(new Func1<WeatherResult, Observable<DailyForecast>>() {
                            @Override
                            public Observable<DailyForecast> call(WeatherResult weatherResult) {
                                return Observable.from(weatherResult.getDaily_forecast());
                            }
                        })
                        .map(new Func1<DailyForecast, Temperature>() {
                            @Override
                            public Temperature call(DailyForecast dailyForecast) {
                                return dailyForecast.getTmp();
                            }
                        })
                        .subscribeOn(AndroidSchedulers.mainThread())// 指定观察者在主线程
                        .observeOn(AndroidSchedulers.mainThread())//指定订阅者在主线程
                        .subscribe(new Subscriber<Temperature>() {
                            @Override
                            public void onCompleted() {
                                tv.setText(sb);
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(Temperature temperature) {
                                Log.e("RxJava",gson.toJson(temperature));
                                sb.append(gson.toJson(temperature)+"\n");
                            }
                        });

            }
        });
    }

    /*
    *初始化请求参数
     */
    private WeatherInfoReq initParams() {
        WeatherInfoReq weatherInfoReq = new WeatherInfoReq();
        weatherInfoReq.apiKey = Constant.API_KEY;
        weatherInfoReq.city = Constant.CITY;
        return weatherInfoReq;
    }

    /*
    *初始化控件
     */
    private void initViews() {
        request = (Button) this.findViewById(R.id.request);
        tv = (TextView) this.findViewById(R.id.tv);
    }
}

