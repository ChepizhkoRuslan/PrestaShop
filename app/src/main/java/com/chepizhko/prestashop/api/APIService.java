package com.chepizhko.prestashop.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface APIService {

    @GET("api/products?display=[name,description,id_default_image,price,reference]")
    Call<ResponseBody> callBack (@Header("Authorization") String credential, @Query("limit") String limit);


}