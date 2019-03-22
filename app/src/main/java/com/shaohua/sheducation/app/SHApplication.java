package com.shaohua.sheducation.app;

import com.njcool.lzccommon.app.App;
import com.njcool.lzccommon.network.common.ViseConfig;
import com.njcool.lzccommon.network.convert.GsonConverterFactory;
import com.njcool.lzccommon.network.http.CoolHttp;
import com.njcool.lzccommon.network.http.interceptor.HttpLogInterceptor;
import com.tencent.bugly.crashreport.CrashReport;
import com.vise.log.ViseLog;
import com.vise.log.inner.LogcatTree;
import com.vise.utils.assist.SSLUtil;

import java.io.File;
import java.util.HashMap;

import okhttp3.Cache;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Created by chuan on 2017/7/18.
 */

public class SHApplication extends App {

    @Override
    public void onCreate() {
        super.onCreate();

        CrashReport.initCrashReport(getApplicationContext(), "b51bb73adf", false);
//        initLog();
//        initNet();
    }

    private void initLog() {
        ViseLog.getLogConfig()
                .configAllowLog(true)//是否输出日志
                .configShowBorders(false);//是否排版显示
        ViseLog.plant(new LogcatTree());//添加打印日志信息到Logcat的树
    }

    private void initNet() {
        CoolHttp.init(this);
        CoolHttp.CONFIG()
                //配置请求主机地址
                .baseUrl("http://39.106.4.15:8080/")
                //配置全局请求头
                .globalHeaders(new HashMap<String, String>())
                //配置全局请求参数
                .globalParams(new HashMap<String, String>())
                //配置读取超时时间，单位秒
                .readTimeout(30)
                //配置写入超时时间，单位秒
                .writeTimeout(30)
                //配置连接超时时间，单位秒
                .connectTimeout(30)
                //配置请求失败重试次数
                .retryCount(3)
                //配置请求失败重试间隔时间，单位毫秒
                .retryDelayMillis(1000)
                //配置是否使用cookie
                .setCookie(true)
                //配置自定义cookie
//                .apiCookie(new ApiCookie(this))
                //配置是否使用OkHttp的默认缓存
                .setHttpCache(true)
                //配置OkHttp缓存路径
                .setHttpCacheDirectory(new File(CoolHttp.getContext().getCacheDir(), ViseConfig.CACHE_HTTP_DIR))
                //配置自定义OkHttp缓存
                .httpCache(new Cache(new File(CoolHttp.getContext().getCacheDir(), ViseConfig.CACHE_HTTP_DIR), ViseConfig.CACHE_MAX_SIZE))
                //配置自定义离线缓存
                .cacheOffline(new Cache(new File(CoolHttp.getContext().getCacheDir(), ViseConfig.CACHE_HTTP_DIR), ViseConfig.CACHE_MAX_SIZE))
                //配置自定义在线缓存
                .cacheOnline(new Cache(new File(CoolHttp.getContext().getCacheDir(), ViseConfig.CACHE_HTTP_DIR), ViseConfig.CACHE_MAX_SIZE))
                //配置开启Gzip请求方式，需要服务器支持
//                .postGzipInterceptor()
                //配置应用级拦截器
                .interceptor(new HttpLogInterceptor()
                        .setLevel(HttpLogInterceptor.Level.BODY))
                .setCookie(true)
//                .apiCookie()
                //配置网络拦截器
//                .networkInterceptor(new NoCacheInterceptor())
                //配置转换工厂
                .converterFactory(GsonConverterFactory.create())
                //配置适配器工厂
                .callAdapterFactory(RxJava2CallAdapterFactory.create())
                //配置请求工厂
//                .callFactory(new Call.Factory() {
//                    @Override
//                    public Call newCall(Request request) {
//                        return null;
//                    }
//                })
                //配置连接池
//                .connectionPool(new ConnectionPool())
                //配置主机证书验证
                .hostnameVerifier(new SSLUtil.UnSafeHostnameVerifier("http://39.106.4.15:8080/"))
                //配置SSL证书验证
                .SSLSocketFactory(SSLUtil.getSslSocketFactory(null, null, null))
        //配置主机代理
//                .proxy(new Proxy(Proxy.Type.HTTP, new SocketAddress() {}))
        ;

    }

//    /**
//     * 新header
//     *
//     * @param context
//     * @return
//     */
//    public static Map<String, String> getHeaders(Context context) {
//        Map<String, String> headers;
//        long time = System.currentTimeMillis();
//        headers = new HashMap<>();
//        headers.put("appId", AYConsts.appId);
//        headers.put("sign", getSign(CoolDateUtil.formateMillisToYYYYMMDDHHmm(time),
//                CoolSPUtil.getDataFromLoacl(context, "token")));
//        headers.put("timestamp", CoolDateUtil.formateMillisToYYYYMMDDHHmm(time));
//        headers.put("os", "android");
//        headers.put("version", CoolPublicMethod.getVersionName(context));
//        headers.put("token", CoolSPUtil.getDataFromLoacl(context, "token"));
//        return headers;
//    }
//
//    /**
//     * 签名
//     *
//     * @param time
//     * @param token
//     * @return
//     */
//    public static String getSign(String time, String token) {
//        StringBuffer sb = new StringBuffer();
//        sb.append(AYConsts.appId).append(time).append(token).append(AYConsts.appKey);
//        CoolMD5 coolMD5 = new CoolMD5();
//        return coolMD5.getMD5ofStr(sb.toString()).toLowerCase();
//    }
}
