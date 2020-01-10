package com.shallow.hamrobazar.Api;

import com.shallow.hamrobazar.Model.Item;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ItemAPI {
    //get all the recent items
    @GET("recent")
    Call<List<Item>> getAllRecentItems();

    //get all the trending items
    @GET("trend")
    Call<List<Item>> getAllTrendingItems();

}
