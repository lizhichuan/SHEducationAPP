package com.njcool.lzccommon.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.njcool.lzccommon.R;

import java.io.File;

/**
 * Created by lizhichuan on 16/7/6.
 */
public class CoolGlideUtil {

    /**
     * 加载网络图片
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void urlInto(Context context, String url, ImageView imageView) {
        if (TextUtils.isEmpty(url)) {
            Glide.with(context).
                    load(R.mipmap.img_failure).
                    asBitmap().
//                crossFade().//淡入显示,注意:如果设置了这个,则必须要去掉asBitmap
//                    placeholder(R.mipmap.ic_launcher).
        error(R.mipmap.img_failure).
                    skipMemoryCache(false).
                    fitCenter().thumbnail(0.1f).
                    into(imageView);
        } else {
            if (url.startsWith("http")) {
                Glide.with(context).load(url).
                        asBitmap().
//                crossFade().//淡入显示,注意:如果设置了这个,则必须要去掉asBitmap
        placeholder(R.mipmap.img_failure).
                        error(R.mipmap.img_failure).
                        skipMemoryCache(false).
                        centerCrop().thumbnail(0.1f).
                        into(imageView);
            } else {
                Glide.with(context).load(url).
                        asBitmap().
//                crossFade().//淡入显示,注意:如果设置了这个,则必须要去掉asBitmap
        placeholder(R.mipmap.img_failure).
                        error(R.mipmap.img_failure).
                        skipMemoryCache(false).
                        fitCenter().thumbnail(0.1f).
                        into(imageView);
            }
        }
    }


    public static void urlInto(Context context, String url, ImageView imageView, int failRes) {
        if (TextUtils.isEmpty(url)) {
            Glide.with(context).
                    load(failRes).
                    asBitmap().
//                crossFade().//淡入显示,注意:如果设置了这个,则必须要去掉asBitmap
//                    placeholder(R.mipmap.ic_launcher).
        error(failRes).
                    skipMemoryCache(false).
                    fitCenter().thumbnail(0.1f).
                    into(imageView);
        } else {
            if (url.startsWith("http")) {
                Glide.with(context).load(url).
                        asBitmap().
//                crossFade().//淡入显示,注意:如果设置了这个,则必须要去掉asBitmap
        placeholder(failRes).
                        error(failRes).
                        skipMemoryCache(false).
                        centerCrop().thumbnail(0.1f).
                        into(imageView);
            } else {
                Glide.with(context).load(url).
                        asBitmap().
//                crossFade().//淡入显示,注意:如果设置了这个,则必须要去掉asBitmap
        placeholder(failRes).
                        error(failRes).
                        skipMemoryCache(false).
                        fitCenter().thumbnail(0.1f).
                        into(imageView);
            }
        }
    }


    /**
     * 加载网络图片
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void urlInto2(Context context, String url, ImageView imageView) {
        if (TextUtils.isEmpty(url)) {
            Glide.with(context).load(R.mipmap.img_failure).
                    asBitmap().
//                crossFade().//淡入显示,注意:如果设置了这个,则必须要去掉asBitmap
        placeholder(R.mipmap.img_failure).
                    error(R.mipmap.img_failure).
                    skipMemoryCache(false).
                    thumbnail(0.1f).
                    into(imageView);
        } else {
            if (url.startsWith("http")) {
                Glide.with(context).load(url).
                        asBitmap().
//                crossFade().//淡入显示,注意:如果设置了这个,则必须要去掉asBitmap
//        placeholder(R.mipmap.ic_launcher).
        error(R.mipmap.img_failure).
                        skipMemoryCache(false).
                        thumbnail(0.1f).
                        into(imageView);
            } else {
                Glide.with(context).load(R.mipmap.img_failure).
                        asBitmap().
//                crossFade().//淡入显示,注意:如果设置了这个,则必须要去掉asBitmap
//        placeholder(R.mipmap.ic_launcher).
        error(R.mipmap.img_failure).
                        skipMemoryCache(false).
                        thumbnail(0.1f).
                        into(imageView);
            }
        }
    }

    /**
     * 加载资源图片
     *
     * @param context
     * @param resourceId
     * @param imageView
     */
    public static void ResInto(Context context, int resourceId, ImageView imageView) {
        Glide.with(context).load(resourceId).
                asBitmap().
//                crossFade().//淡入显示,注意:如果设置了这个,则必须要去掉asBitmap
        placeholder(R.mipmap.img_failure).
                error(R.mipmap.img_failure).
                skipMemoryCache(false).
                fitCenter().thumbnail(0.1f).
                into(imageView);
    }

    /**
     * 加载本地图片文件
     *
     * @param context
     * @param file
     * @param imageView
     */
    public static void FileInto(Context context, File file, ImageView imageView) {
        Glide.with(context).load(file).
//                asBitmap().
        crossFade().//淡入显示,注意:如果设置了这个,则必须要去掉asBitmap
//                placeholder(R.mipmap.img_failure).
        error(R.mipmap.img_failure).
//                fitCenter().thumbnail(0.1f).
        into(imageView);
    }

    /**
     * 加载本地图片文件
     *
     * @param context
     * @param path
     * @param imageView
     */
    public static void FileInto(Context context, String path, ImageView imageView) {
        File file = new File(path);
        Glide.with(context).load(file).
//                asBitmap().
        crossFade().//淡入显示,注意:如果设置了这个,则必须要去掉asBitmap
//                placeholder(R.mipmap.img_failure).
        error(R.mipmap.img_failure).
//                fitCenter().thumbnail(0.1f).
        into(imageView);
    }

    /**
     * 加载资源gif图片
     *
     * @param context
     * @param resId
     * @param imageView
     */
    public static void GifInto(Context context, String resId, ImageView imageView) {
        Glide.with(context).load(resId).
//                asBitmap().
        asGif().//注意:这里显示的指明了要加载的是gif图片,当然即使不指明,glide也会自己判断.
                crossFade().//淡入显示,注意:如果设置了这个,则必须要去掉asBitmap
                placeholder(R.mipmap.img_failure).
                error(R.mipmap.img_failure).
                skipMemoryCache(false).
                fitCenter().thumbnail(0.1f).
                into(imageView);
    }

    /**
     * 加载资源gif图片
     *
     * @param context
     * @param resId
     * @param imageView
     */
    public static void GifInto(Context context, int resId, ImageView imageView) {
        Glide.with(context).load(resId).
//                asBitmap().
        asGif().//注意:这里显示的指明了要加载的是gif图片,当然即使不指明,glide也会自己判断.
                crossFade().//淡入显示,注意:如果设置了这个,则必须要去掉asBitmap
                placeholder(R.mipmap.img_failure).
                error(R.mipmap.img_failure).
                skipMemoryCache(false).
                fitCenter().thumbnail(0.1f).
                into(imageView);
    }
}
