package edu.skku.cs.pa2.model;

import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitService {

    @POST("/users")
    Call<LoginResult> getLoginResult(@Body JsonObject jsonObject);

    @GET("/maps")
    Call<List<Maze>> getMaze();

    @GET("/maze/map")
    Call<MazeMap> getMazeMap(@Query("name") String name);

}
