package com.chepizhko.prestashop.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface APIService {

    @GET("api/productsdisplay=[name,description,id_default_image,price,reference]&limit=20")
    Call<ResponseBody> callBack (@Header("Authorization") String credential);

}