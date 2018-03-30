package com.chepizhko.prestashop.api;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIService {
    @GET("/api/products?display=[name,description,id_default_image,price,reference]&format=json&limit=20&format=json")
    Call<JsonObject> callBack ();
}