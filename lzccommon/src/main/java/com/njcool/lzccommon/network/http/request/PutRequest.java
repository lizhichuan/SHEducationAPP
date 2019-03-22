package com.njcool.lzccommon.network.http.request;


import com.njcool.lzccommon.network.http.CoolHttp;
import com.njcool.lzccommon.network.http.callback.ACallback;
import com.njcool.lzccommon.network.http.core.ApiManager;
import com.njcool.lzccommon.network.http.mode.CacheResult;
import com.njcool.lzccommon.network.http.mode.MediaTypes;
import com.njcool.lzccommon.network.http.subscriber.ApiCallbackSubscriber;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @Description: Put请求
 * @author: <a href="http://www.xiaoyaoyou1212.com">DAWI</a>
 * @date: 2017-04-28 16:06
 */
public class PutRequest extends BaseHttpRequest<PutRequest> {

    protected Map<String, Object> forms = new LinkedHashMap<>();
    protected StringBuilder stringBuilder = new StringBuilder();
    protected RequestBody requestBody;
    protected MediaType mediaType;
    protected String content;


    public PutRequest(String suffixUrl) {
        super(suffixUrl);
    }

    @Override
    protected <T> Observable<T> execute(Type type) {
        if (stringBuilder.length() > 0) {
            suffixUrl = suffixUrl + stringBuilder.toString();
        }
        if (forms != null && forms.size() > 0) {
            if (params != null && params.size() > 0) {
                Iterator<Map.Entry<String, String>> entryIterator = params.entrySet().iterator();
                Map.Entry<String, String> entry;
                while (entryIterator.hasNext()) {
                    entry = entryIterator.next();
                    if (entry != null) {
                        forms.put(entry.getKey(), entry.getValue());
                    }
                }
            }
            return apiService.putForm(suffixUrl, forms).compose(this.<T>norTransformer(type));
        }
        if (requestBody != null) {
            return apiService.putBody(suffixUrl, requestBody).compose(this.<T>norTransformer(type));
        }
        if (content != null && mediaType != null) {
            requestBody = RequestBody.create(mediaType, content);
            return apiService.putBody(suffixUrl, requestBody).compose(this.<T>norTransformer(type));
        }
        return apiService.put(suffixUrl, params).compose(this.<T>norTransformer(type));
    }

    @Override
    protected <T> Observable<CacheResult<T>> cacheExecute(Type type) {
        return this.<T>execute(type).compose(CoolHttp.getApiCache().<T>transformer(cacheMode, type));
    }

    @Override
    protected <T> void execute(ACallback<T> callback) {
        DisposableObserver disposableObserver = new ApiCallbackSubscriber(callback);
        if (super.tag != null) {
            ApiManager.get().add(super.tag, disposableObserver);
        }
        if (isLocalCache) {
            this.cacheExecute(getSubType(callback)).subscribe(disposableObserver);
        } else {
            this.execute(getType(callback)).subscribe(disposableObserver);
        }
    }

    public PutRequest addUrlParam(String paramKey, String paramValue) {
        if (paramKey != null && paramValue != null) {
            if (stringBuilder.length() == 0) {
                stringBuilder.append("?");
            } else {
                stringBuilder.append("&");
            }
            stringBuilder.append(paramKey).append("=").append(paramValue);
        }
        return this;
    }

    public PutRequest addForm(String formKey, Object formValue) {
        if (formKey != null && formValue != null) {
            forms.put(formKey, formValue);
        }
        return this;
    }

    public PutRequest setRequestBody(RequestBody requestBody) {
        this.requestBody = requestBody;
        return this;
    }

    public PutRequest setString(String string) {
        this.content = string;
        this.mediaType = MediaTypes.TEXT_PLAIN_TYPE;
        return this;
    }

    public PutRequest setString(String string, MediaType mediaType) {
        this.content = string;
        this.mediaType = mediaType;
        return this;
    }

    public PutRequest setJson(String json) {
        this.content = json;
        this.mediaType = MediaTypes.APPLICATION_JSON_TYPE;
        return this;
    }

    public PutRequest setJson(JSONObject jsonObject) {
        this.content = jsonObject.toString();
        this.mediaType = MediaTypes.APPLICATION_JSON_TYPE;
        return this;
    }

    public PutRequest setJson(JSONArray jsonArray) {
        this.content = jsonArray.toString();
        this.mediaType = MediaTypes.APPLICATION_JSON_TYPE;
        return this;
    }
}
