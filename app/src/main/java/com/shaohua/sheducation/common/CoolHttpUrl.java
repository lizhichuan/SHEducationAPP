package com.shaohua.sheducation.common;


import android.content.Context;

import com.shaohua.sheducation.app.SHApplication;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lizhichuan on 16/7/5.
 */
public class CoolHttpUrl {


    /**
     * 获取PhoneApi实例
     *
     * @return
     */
    public static CoolHttpUrl getApi() {
        return ApiHolder.phoneApi;
    }

    static class ApiHolder {
        private static CoolHttpUrl phoneApi = new CoolHttpUrl(SHApplication.getInstance().getApplicationContext());
    }

    private ShHttpService service;

    private CoolHttpUrl(Context context) {
//        if (BuildConfig.DEBUG) {
//        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
//        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cookieJar(new CookieManger(context)).build();
//        }

        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(ConstsUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(ShHttpService.class);
    }


    /**
     * 获取PhoneService实例
     *
     * @return
     */
    public ShHttpService getService() {
        return service;
    }
}
