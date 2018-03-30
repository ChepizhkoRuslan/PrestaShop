package com.chepizhko.prestashop;


import android.app.Application;
import android.util.Base64;

import java.io.UnsupportedEncodingException;

public class App extends Application{

    private static String KEY = "XHKM6A6BLCA5MNYZQBX2GXBAAKSTPMK2";
    private static String basicAuth;
    @Override
    public void onCreate() {
        super.onCreate();
        basicAuth = getEncodedData();

    }
    public String getEncodedData() {
        return "Basic " + Base64.encodeToString(String.format( "%s:%s",KEY, "").getBytes(), Base64.NO_WRAP);
    }

    public static String getBasicAuth() {
        return basicAuth;
    }
    public static String getAuthToken() {
        byte[] data = new byte[0];
        try {
            data = (KEY + ":" + "").getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "Basic " + Base64.encodeToString(data, Base64.NO_WRAP);
    }
}
