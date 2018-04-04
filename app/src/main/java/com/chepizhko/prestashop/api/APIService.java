package com.chepizhko.prestashop.api;

import com.chepizhko.prestashop.model.PrestaShop;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface APIService {

    @GET("api/products?display=[name,description,id_default_image,price,reference]&limit=20")
    Call<PrestaShop> callBack (@Header("Authorization") String credential);


}