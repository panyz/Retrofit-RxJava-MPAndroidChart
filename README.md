### **前言** ###
上周我写了一篇[MPAndroidChart的使用技巧](https://panyz.github.io/2016/08/02/MPAndroidChart%E7%9A%84%E4%BD%BF%E7%94%A8%E6%8A%80%E5%B7%A7/) ,得到了不少人的响应。至少自己写的文章还是有人去看，很是激励。毕竟我在学习Android开发的时候也是一直看着同行前辈们在技术社区上的干货输出，慢慢地进步起来。当在一些技术点上有了自己的一般见解，也应当回馈社区。

感概完了，现在还是言归正传吧。当掌握了一个技术点之后，怎样在项目中运用到这个技术点，还需要自己好好琢磨琢磨一下的。上次在[MPAndroidChart的使用技巧](https://panyz.github.io/2016/08/02/MPAndroidChart%E7%9A%84%E4%BD%BF%E7%94%A8%E6%8A%80%E5%B7%A7/)案例中，数据都是自己写死的。现在就来看看MPAndroidChart怎样结合后台的传过来的数据展示图表吧。

在这次的案例中，我使用的是近段时间以来很潮很火的Retrofit+RxJava（这两种开发技术堪称一对CP啊！）来对后台进行网络请求。如果对Retrofit使用还不是很熟悉的话，可以看看之前我的写过一篇[ Retrofit网络请求框架基础操作](http://blog.csdn.net/ppyyzz628/article/details/51322593)。而对于RxJava，我并未进行深入的研究，但可以看看一些大神写的博客，例如这位[大头鬼Bruce](http://blog.csdn.net/lzyzsd?viewmode=contents)。（PS：在案例中我对Retrofit请求时做了一些封装，在点击button前，未显示图表，点击button之后，才显示图表）

### **Talk is cheap,show you the code** ###

- 导入MPAndroidChart的jar包（这一步可以参考[MPAndroidChart的使用技巧](https://panyz.github.io/2016/08/02/MPAndroidChart%E7%9A%84%E4%BD%BF%E7%94%A8%E6%8A%80%E5%B7%A7/)）

- 添加依赖

    compile fileTree(dir: 'libs', include: ['*.jar'])
    testCompile 'junit:junit:4.12'
    compile 'com.android.support:appcompat-v7:23.3.0'
    compile 'com.squareup.okhttp3:okhttp:3.2.0'
    compile 'com.squareup.retrofit2:retrofit:2.0.1'
    compile 'com.squareup.retrofit2:converter-gson:2.0.0-beta4'
    compile 'com.squareup.retrofit2:adapter-rxjava:2.0.1'
    compile 'io.reactivex:rxandroid:1.1.0'
    compile 'io.reactivex:rxjava:1.1.0'
    compile 'com.orhanobut:logger:1.11'
    compile project(':JNChartLib')


- 创建请求接口(使用的接口地址：http://apis.baidu.com/heweather/weather/free )

```java 
public interface NetRequest {
    @GET("/heweather/weather/free?/")
    Observable<WeatherInfo> getWeatherByRxJava(@Header("apiKey")String apiKey, @Query("city")String city);
}
```

- 创建网络接口服务的封装类(通过单例模式来获取网络请求接口的对象)
```java
public class RetrofitWrapper {
    private static RetrofitWrapper instance;
    private Context mContext;
    private Retrofit retrofit;
    private RetrofitWrapper() {
        Gson gson = new Gson();
        retrofit = new Retrofit.Builder().baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }
    public static RetrofitWrapper getInstance() {
        if (instance == null) {
            synchronized (RetrofitWrapper.class){
                if (instance==null){
                    instance = new RetrofitWrapper();
                }
            }
        }
        return instance;
    }
    /**
     * 创建请求接口的对象
     * @param service
     * @param <T>
     * @return
     */
    public <T> T create(final Class<T> service) {
        return retrofit.create(service);
    }
}
```

- 创建功能模块类（查询天气功能）
```java
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
    public Observable<WeatherInfo> queryWeatherByRxJava(WeatherInfoReq weatherInfoReq) {
        Observable<WeatherInfo> infoCall = netRequest.getWeatherByRxJava(weatherInfoReq.apiKey, weatherInfoReq.city);
        return infoCall;
    }
}
```

- 最后就是在MainActivity中进行操作啦
```java
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
                weatherInfoModel.queryWeatherByRxJava(initParams())
                        .subscribeOn(Schedulers.io())
                        .doOnSubscribe(new Action0() {
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
                        .observeOn(AndroidSchedulers.mainThread())
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
```

### **总结** ###
这次的案例代码还是比较简单的，相信大家看起来应该容易明白。如果要说难点，应该就是RxJava的使用上，因为RxJava是响应式编程，这与我们之前编程的思想有点不同，但它的好处就是代码简洁，优雅，随意地变换线程，而且重要的是它的操作符真的非常牛逼，如果你深入去研究的话，都会对着电脑竖起你的大拇指。而这次的图表中，如果大家看了效果图之后，会发现这次y轴上和图表中的数据都带有单位符号，这是我重写了ValueFormatter和YAxisValueFormatter这两个类，具体的写法大家可以看看源码。本文没有贴出一些bean类和常量类，如需请看源码！最后，**小弟不才，还望多多指教！**

- **案例代码的github地址**

>  [Retrofit-RxJava-MPAndroidChart-](https://github.com/panyz/Retrofit-RxJava-MPAndroidChart-)

**效果图**
![点击前](http://img.blog.csdn.net/20160808162734173)
![点击后](http://img.blog.csdn.net/20160808162825528)
