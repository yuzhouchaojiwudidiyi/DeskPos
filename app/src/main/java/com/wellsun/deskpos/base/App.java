package com.wellsun.deskpos.base;

import android.app.Activity;
import android.app.Application;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.util.DisplayMetrics;
import android.util.Log;

import com.cczhr.TTS;
import com.cczhr.TTSConstants;
import com.hjq.permissions.XXPermissions;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.SPCookieStore;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.lzy.okgo.model.HttpHeaders;
import com.sunmi.peripheral.printer.InnerPrinterCallback;
import com.sunmi.peripheral.printer.InnerPrinterException;
import com.sunmi.peripheral.printer.InnerPrinterManager;
import com.sunmi.peripheral.printer.SunmiPrinterService;
import com.wellsun.deskpos.util.ToastPrint;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import me.jessyan.autosize.AutoSizeConfig;
import me.jessyan.autosize.unit.Subunits;
import me.yokeyword.fragmentation.BuildConfig;
import me.yokeyword.fragmentation.Fragmentation;
import okhttp3.OkHttpClient;

/**
 * content:
 *
 * @date: 2021/6/22
 * @author: ZhaoZheng
 */
public class App extends Application {

    /**
     * 记录当前栈里所有activity
     */
    private List<Activity> activities = new ArrayList<Activity>();

    /**
     * 应用实例
     */
    private static App appContext;
    public static TTS tts;

    /**
     * 获得实例
     */
    public static App getAppContext() {
        return appContext;
    }

    /**
     * 商米打印机
     */
    public static SunmiPrinterService printService = null;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
        //屏幕适配
        initAndroidAutoSize();
        //网络框架
        initOkgo();
        //商米打印机初始化
        initSunmiPrint();
        //语音合成
        initTts();

    }
    private void initTts() {
        tts = TTS.getInstance();//获取单例对象
        tts.init(this, TTSConstants.TTS_XIAOYAN);//初始化
    }


    /**
     * 可以在 pt、in、mm 这三个冷门单位中，选择一个作为副单位，副单位是用于规避修改 DisplayMetrics#density 所造成的对于其他使用 dp 布局的系统控件或三方库控件的不良影响，使用副单位后可直接填写设计图上的像素尺寸，不需要再将像素转化为 dp
     */
    private void initAndroidAutoSize() {
        AutoSizeConfig.getInstance().getUnitsManager()
                .setSupportDP(true)
                .setSupportSP(true)
                .setSupportSubunits(Subunits.MM);
    }


    private void initOkgo() {
        /**构建OkHttpClient.Builder*/
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("OkGo");
        //log打印级别，决定了log显示的详细程度
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);
        //log颜色级别，决定了log在控制台显示的颜色
        loggingInterceptor.setColorLevel(Level.INFO); //不打印log NONE //只打印 请求首行 和 响应首行BASIC //打印请求和响应的所有 Header HEADERS  //所有数据全部打印 BODY
        builder.addInterceptor(loggingInterceptor);
        /** 配置超时时间*/
        //全局的读取超时时间
        builder.readTimeout(3000, TimeUnit.MILLISECONDS);
        //全局的写入超时时间
        builder.writeTimeout(3000, TimeUnit.MILLISECONDS);
        //全局的连接超时时间
        builder.connectTimeout(3000, TimeUnit.MILLISECONDS);
        /**配置Cookie，以下几种任选其一就行*/
        //使用sp保持cookie，如果cookie不过期，则一直有效
        builder.cookieJar(new CookieJarImpl(new SPCookieStore(this)));
        //使用数据库保持cookie，如果cookie不过期，则一直有效
//        builder.cookieJar(new CookieJarImpl(new DBCookieStore(this)));
        //使用内存保持cookie，app退出后，cookie消失
//        builder.cookieJar(new CookieJarImpl(new MemoryCookieStore()));
        /**Https配置，以下几种方案根据需要自己设置*/
        //方法一：信任所有证书,不安全有风险
//        HttpsUtils.SSLParams sslParams1 = HttpsUtils.getSslSocketFactory();
        //方法二：自定义信任规则，校验服务端证书
//        HttpsUtils.SSLParams sslParams2 = HttpsUtils.getSslSocketFactory(new SafeTrustManager());
        //方法三：使用预埋证书，校验服务端证书（自签名证书）
//        HttpsUtils.SSLParams sslParams3 = HttpsUtils.getSslSocketFactory(getAssets().open("srca.cer"));
        //方法四：使用bks证书和密码管理客户端证书（双向认证），使用预埋证书，校验服务端证书（自签名证书）
//        HttpsUtils.SSLParams sslParams4 = HttpsUtils.getSslSocketFactory(getAssets().open("xxx.bks"), "123456", getAssets().open("yyy.cer"));
//        builder.sslSocketFactory(sslParams1.sSLSocketFactory, sslParams1.trustManager);
        //配置https的域名匹配规则，详细看demo的初始化介绍，不需要就不要加入，使用不当会导致https握手失败
//        builder.hostnameVerifier(new SafeHostnameVerifier());

        /** 配置OkGo*/
        //---------这里给出的是示例代码,告诉你可以这么传,实际使用的时候,根据需要传,不需要就不传-------------//
        HttpHeaders headers = new HttpHeaders();
//        String verName = CommonUtils.getVerName(this);
//        String versionCode = CommonUtils.getVersionCode(this) + "";
//        String deviceId = CommonUtils.getDeviceId(this);
//        headers.put("version", versionCode);
//        headers.put("deviceId", "deviceId");

//        headers.put("commonHeaderKey1", "commonHeaderValue1");    //header不支持中文，不允许有特殊字符
//        headers.put("commonHeaderKey2", "commonHeaderValue2");
//        HttpParams params = new HttpParams();
//        params.put("commonParamsKey1", "commonParamsValue1");     //param支持中文,直接传,不要自己编码
//        params.put("commonParamsKey2", "这里支持中文参数");

        OkGo.getInstance().init(this)                          //必须调用初始化
                .setOkHttpClient(builder.build())               //建议设置OkHttpClient，不设置将使用默认的
                .setCacheMode(CacheMode.NO_CACHE)               //全局统一缓存模式，默认不使用缓存，可以不传
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)   //全局统一缓存时间，默认永不过期，可以不传
                .setRetryCount(2);                              //全局统一超时重连次数，默认为三次，那么最差的情况会请求4次(一次原始请求，三次重连请求)，不需要可以设置为0
//                .addCommonHeaders(headers);                     //全局公共头
//                .addCommonParams(params);                       //全局公共参数
    }


    private void initSunmiPrint() {
        try {
            boolean result = InnerPrinterManager.getInstance().bindService(this,
                    innerPrinterCallback);
        } catch (InnerPrinterException e) {
            e.printStackTrace();
        }

    }

    int printNumber = 0;
    InnerPrinterCallback innerPrinterCallback = new InnerPrinterCallback() {
        @Override
        protected void onConnected(SunmiPrinterService service) {
            //这⾥即获取到绑定服务成功连接后的远程服务接⼝句柄
            //可以通过service调⽤⽀持的打印⽅法
            printService = service;
        }

        @Override
        protected void onDisconnected() {
            //当服务异常断开后，会回调此⽅法，建议在此做重连策略
            printNumber = printNumber + 1;
            if (printNumber > 10) {
                ToastPrint.showText("打印机异常");
            } else {
                initSunmiPrint();
            }
        }
    };

    /**
     * 添加了一个activity
     */
    public void addActivity(Activity activity) {
        activities.add(activity);
    }


    /**
     * 结束指定activity 通过类名
     */
    public void finishActivityClass(Class<?> cls) {
        for (Activity temp : activities) {
            if (temp.getClass().equals(cls)) {
                finishActivity(temp);
            }
        }
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            this.activities.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 保留指定activity 通过类名
     */
    public void keepOneActivityClass(Class<?> cls) {
        for (Activity temp : activities) {
            if (temp.getClass().equals(cls)) {
                finishActivity(temp);
            }
        }
    }

    /**
     * 保留指定的Activity
     */
    public void keepOneAcitivity(Activity keepActivity) {
        for (Activity activity : activities) {
            if (activity != null) {
                if (activity != keepActivity) {
                    activity.finish();
                }
            }
        }
    }

    /**
     * 应用退出，结束所有的activity
     */
    public void exit() {
        for (Activity activity : activities) {
            if (activity != null) {
                activity.finish();
            }
        }
        System.exit(0);
    }


}
