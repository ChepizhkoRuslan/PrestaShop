package com.chepizhko.prestashop.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface APIService {

    @GET("api/products?display=[name,description,id_default_image,price,reference]&limit=20")
    Call<String> callBack (@Header("Authorization") String credential);

    @GET("api/products?display=[name,description,id_default_image,price,reference]&limit=20")
    Call<ResponseBody> callBack2 (@Header("Authorization") String credential);
}