package com.example.yangyang.demo.failback;

public interface Completeback<T> {
    void onLoadCompleteSuccess();
    void onLoadCompleteSuccess(T t);
    void onLoadCompleteFail();
}
