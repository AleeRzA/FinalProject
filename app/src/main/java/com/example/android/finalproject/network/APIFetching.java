package com.example.android.finalproject.network;

import com.example.android.finalproject.model.Products;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIFetching {


     String CONSUMER_KEY = "ck_109ac9e7df200e7cc58194b85008f65cc67d08db";
     String CONSUMER_SECRET = "cs_542f042e91f6f9c4c7cbcff7b459cc17180e86e6";
    @GET("products/?consumer_key=" +
            CONSUMER_KEY + "&consumer_secret=" +
            CONSUMER_SECRET +  "&orderby=date")
    Call<List<Products>> getRecentProducts();

    @GET("reports/top_sellers/?consumer_key=" +
            CONSUMER_KEY + "&consumer_secret=" +
            CONSUMER_SECRET)
    Call<List<Products>> getTopSellers();

//    @GET("products/attributes/?consumer_key=" +
//            CONSUMER_KEY + "&consumer_secret=" + CONSUMER_SECRET)
//    Call<List<Products>> getDates;
}
