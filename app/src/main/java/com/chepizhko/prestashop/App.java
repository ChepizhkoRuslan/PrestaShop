package com.chepizhko.prestashop;

import android.app.Application;
import android.util.Base64;

import com.chepizhko.prestashop.api.APIService;
import com.chepizhko.prestashop.auth.BasicAuthInterceptor;

import java.io.UnsupportedEncodingException;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class App extends Application {
    private static String KEY = "XHKM6A6BLCA5MNYZQBX2GXBAAKSTPMK2";
    private static OkHttpClient client;
    private Retrofit retrofit;
    private static APIService service;

    @Override
    public void onCreate() {
        super.onCreate();

        client = new OkHttpClient.Builder()
                .addInterceptor(new BasicAuthInterceptor(getAuthToken()))
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl("http://ps1722.weeteam.net/")
//                .addConverterFactory(new ToStringConverterFactory())
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .client(client)
                .build();
        service = retrofit.create(APIService.class);

    }

    public static OkHttpClient getClient() {
        return client;
    }

    public static APIService getService() {
        return service;
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
