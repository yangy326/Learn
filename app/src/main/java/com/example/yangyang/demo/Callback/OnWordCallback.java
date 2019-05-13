package com.example.yangyang.demo.Callback;

import java.io.IOException;

public interface OnWordCallback<T> {

    void onLoadWordSuccess(T t) throws IOException;
    void onLoadWordkFail(T t);
    void onLoadTokenFail();
}
