package com.cybermatrixsolutions.invoicesolutions.customer_module.rest;


import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient {
    public static final String BASE_URL="http://uat.cybermatrixsolutions.com/Garudaapi_uat/";

    private static Retrofit retrofit;
    public static Retrofit getClient() {
        if (ApiClient.retrofit == null) {
            ApiClient.retrofit = new Builder()
                    .baseUrl(ApiClient.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return ApiClient.retrofit;
    }
}
