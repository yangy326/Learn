package com.example.yangyang.demo.Callback;

import java.io.IOException;

public interface OnAudioCallback<T> {
    void onLoadAudioSuccess(T t) throws IOException;
    void onLoadAudiokFail();
}
