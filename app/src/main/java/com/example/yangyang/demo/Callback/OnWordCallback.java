package com.example.yangyang.demo.Callback;

public interface OnWordCallback<T> {

    void onLoadWordSuccess(T t);
    void onLoadWordkFail(T t);
}
