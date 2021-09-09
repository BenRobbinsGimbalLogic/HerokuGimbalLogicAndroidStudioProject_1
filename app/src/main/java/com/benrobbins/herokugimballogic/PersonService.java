package com.benrobbins.herokugimballogic;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

interface PersonService {

    @GET("db/getNames")
    Call<String> getNames();

    @GET("db/addPerson")
    Call<String> insertUser(@Query("name") String name);
}
