package com.njcool.lzccommon.network.http.subscriber;


import com.njcool.lzccommon.network.http.exception.ApiException;
import com.njcool.lzccommon.network.http.mode.ApiCode;

import io.reactivex.observers.DisposableObserver;

/**
 * @Description: API统一订阅者
 * @author: <a href="http://www.xiaoyaoyou1212.com">DAWI</a>
 * @date: 2017-01-03 14:07
 */
abstract class ApiSubscriber<T> extends DisposableObserver<T> {

    ApiSubscriber() {

    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof ApiException) {
            onError((ApiException) e);
        } else {
            onError(new ApiException(e, ApiCode.Request.UNKNOWN));
        }
    }

    protected abstract void onError(ApiException e);
}
