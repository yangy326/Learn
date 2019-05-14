package com.example.yangyang.demo.Utils;

import android.os.Environment;

import java.io.File;

public class GetAudioPathUtil {
    public static String getAudioPath(){
        File parent = Environment.getExternalStorageDirectory();
        File child;
        String brand = android.os.Build.BRAND;
        switch (brand){
            case "HUAWEI":
                child = new File(parent,"Sounds/CallRecord");
                break;
            case "XIAOMI":
                child = new File(parent,"MIUI/sound_recorder/call_rec");
                break;
            case "MEIZU":
                child = new File(parent,"Recorder");
                break;
            case "OPPO":
                child = new File(parent,"Recordings");
                break;
            case "VIVO":
                child = new File(parent,"Record/Call");
                break;
            case "SAMSUNG":
                child = new File(parent,"Sounds");
                break;
                default:
                    child = new File(parent,"");
                    break;
        }
        return child.getPath();

    }


}
