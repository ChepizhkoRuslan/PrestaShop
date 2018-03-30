package com.chepizhko.prestashop.api;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface APIService {

    @GET("api/products?display=[name,description,id_default_image,price,reference]limit=20")
    Call<JsonObject> callBack (@Header("Authorization") String credentials);

}