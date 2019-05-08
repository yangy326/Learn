package com.example.yangyang.demo.failback;

import java.io.IOException;

public interface Audioback<T> {
    void onLoadAudioSuccess(T t) throws IOException;
    void onLoadAudiokFail();
}
