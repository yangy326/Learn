package com.example.yangyang.demo.Callback;

public interface OnCompleteCallback<T> {

    void onLoadCompleteSuccess();
    void onLoadCompleteSuccess(T t);
    void onLoadCompleteFail();
}
