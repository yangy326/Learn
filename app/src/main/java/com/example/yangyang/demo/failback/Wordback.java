package com.example.yangyang.demo.failback;

public interface Wordback<T> {
    void onLoadWordSuccess(T t);
    void onLoadWordkFail(T t);
    void onLoadTokenFail();
}
