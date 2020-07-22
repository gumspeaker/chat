package com.example.demo.common;

import java.util.HashMap;
import java.util.Map;

public class FIleType   {
    public static HashMap<String,String> map=new HashMap();
    static void init(){
        map.put(".mp3","voice");
        map.put(".wav","voice");
        map.put(".png","pic");
    }

    public static HashMap getMap() {
        init();
        return map;
    }
}


