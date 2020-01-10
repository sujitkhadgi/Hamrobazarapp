package com.shallow.hamrobazar.Url;


import com.shallow.hamrobazar.Api.ItemAPI;
import com.shallow.hamrobazar.Api.UserAPI;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Url {
    public static  final String base_url="http://10.0.2.2:3003/";

    public static String token="Bearer ";
    public static final Retrofit retrofit=new Retrofit.Builder()
            .baseUrl(base_url)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    public static final UserAPI userAPI =retrofit.create(UserAPI.class);
    public static final ItemAPI itemAPI =retrofit.create(ItemAPI.class);
}
