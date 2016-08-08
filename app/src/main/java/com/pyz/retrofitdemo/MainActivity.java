package com.pyz.retrofitdemo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.jn.chart.charts.LineChart;
import com.jn.chart.data.Entry;
import com.jn.chart.manager.LineChartManager;
import com.pyz.retrofitdemo.bean.DailyForecast;
import com.pyz.retrofitdemo.bean.WeatherInfo;
import com.pyz.retrofitdemo.bean.WeatherInfoReq;
import com.pyz.retrofitdemo.bean.WeatherResult;
import com.pyz.retrofitdemo.model.WeatherInfoModel;

import java.util.ArrayList;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends Activity {
    private Button request;
    private LineChart chart;
    private WeatherInfoModel weatherInfoModel;
    private int i = 0;
    private ProgressDialog pd;

    private ArrayList<String> xValues = new ArrayList<>();
    private ArrayList<Entry> yValues2Max = new ArrayList<Entry>();
    private ArrayList<Entry> yValues2Min = new ArrayList<Entry>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        weatherInfoModel = WeatherInfoModel.getInstance(getApplicationContext());
        initViews();
        initParams();
        initEvent();
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
        chart = (LineChart) this.findViewById(R.id.weatherChart);
    }

    private void initEvent() {
        final Gson gson = new Gson();
        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //创建访问的API请求
                weatherInfoModel.queryWeatherByRxJava(initParams())
                        .subscribeOn(Schedulers.io())// 指定观察者在io线程（第一次指定观察者线程有效）
                        .doOnSubscribe(new Action0() {//在启动订阅前（发送事件前）执行的方法
                            @Override
                            public void call() {
                                pd = new ProgressDialog(MainActivity.this);
                                pd.setMessage("请稍后...");
                                pd.show();
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
                        .observeOn(AndroidSchedulers.mainThread())//指定订阅者在主线程
                        .subscribe(new Subscriber<DailyForecast>() {
                            @Override
                            public void onCompleted() {
                                pd.dismiss();
                                chart.setDescription("广州气温预测");
                                LineChartManager.setLineName("最高温度");
                                LineChartManager.setLineName1("最低温度");
                                LineChartManager.initDoubleLineChart(MainActivity.this, chart, xValues, yValues2Max, yValues2Min);
                            }

                            @Override
                            public void onError(Throwable e) {
                                pd.dismiss();
                            }

                            @Override
                            public void onNext(DailyForecast dailyForecast) {
                                int j = i++;
                                xValues.add(dailyForecast.getDate());
                                yValues2Max.add(new Entry(Float.valueOf(dailyForecast.getTmp().getMaxTem()), j));
                                yValues2Min.add(new Entry(Float.valueOf(dailyForecast.getTmp().getMinTem()), j));
                            }
                        });

            }
        });
    }


}
