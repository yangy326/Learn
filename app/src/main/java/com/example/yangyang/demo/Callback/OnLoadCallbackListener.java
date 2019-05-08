package com.example.yangyang.demo.Callback;

public interface OnLoadCallbackListener<T> {
    void onLoadCallbackSuccess(T t);

    void onLoadCallbackFail();

}
