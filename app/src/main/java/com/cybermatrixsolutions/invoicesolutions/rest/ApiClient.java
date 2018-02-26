package com.cybermatrixsolutions.invoicesolutions.rest;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



///  This is singletone class example ///

public class ApiClient {
    public static final String BASE_URL="http://uat.cybermatrixsolutions.com/Garudaapi_uat/";

    private static Retrofit retrofit = null;
    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
