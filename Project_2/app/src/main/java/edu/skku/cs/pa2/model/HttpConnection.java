package edu.skku.cs.pa2.model;

import android.util.Log;

import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.List;

import edu.skku.cs.pa2.callback.GetMazeListener;
import edu.skku.cs.pa2.callback.GetMazeMapListener;
import edu.skku.cs.pa2.callback.LoginListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.POST;

public class HttpConnection {
    private final String TAG = HttpConnection.class.getSimpleName();
    private final String base_url = "http://115.145.175.57:10099";

    private LoginListener mLoginListener;
    private GetMazeListener mGetMazeListener;
    private GetMazeMapListener mGetMazeMapListener;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(base_url)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    RetrofitService retrofitService = retrofit.create(RetrofitService.class);

    public void setmLoginListener(LoginListener loginListener) {
        this.mLoginListener = loginListener;
    }

    public void getLoginResult(JsonObject jsonObject) {
        //Log.d(TAG, "getLoginResult");
        retrofitService.getLoginResult(jsonObject).enqueue(new Callback<LoginResult>() {
            @Override
            public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {
                if (response.isSuccessful()) {
                    LoginResult data = response.body();
                    //Log.d(TAG, "getLoginResult success");
                    //Log.d(TAG, response.toString());
                    //Log.d(TAG, String.valueOf(data.isSuccess()));
                    mLoginListener.onSuccess(data.isSuccess());
                } else {
                    //Log.d(TAG, "getLoginResult failed");
                    mLoginListener.onFailed();
                }
            }

            @Override
            public void onFailure(Call<LoginResult> call, Throwable t) {
                //Log.d(TAG, t.getMessage());
                mLoginListener.onFailed();
            }
        });
    }

    public void setMGetMazeListener(GetMazeListener getMazeListener) {
        this.mGetMazeListener = getMazeListener;
    }

    public void getMazeList() {
        //Log.d(TAG, "getMazeMap");
        retrofitService.getMaze().enqueue(new Callback<List<Maze>>() {
            @Override
            public void onResponse(Call<List<Maze>> call, Response<List<Maze>> response) {
                if (response.isSuccessful()) {
                    //Log.d(TAG, response.toString());
                    mGetMazeListener.onSuccess(response.body());
                } else {
                    //Log.d(TAG, "getMazeMap failed");
                    mGetMazeListener.onFailed();
                }
            }

            @Override
            public void onFailure(Call<List<Maze>> call, Throwable t) {
                //Log.d(TAG, t.getMessage());
                mGetMazeListener.onFailed();
            }
        });
    }

    public void setmGetMazeMapListener(GetMazeMapListener mazeMapListener){
        this.mGetMazeMapListener = mazeMapListener;
    }

    public void getMazeMap(String name) {
        //Log.d(TAG, "getMazeMap");
        retrofitService.getMazeMap(name).enqueue(new Callback<MazeMap>() {
            @Override
            public void onResponse(Call<MazeMap> call, Response<MazeMap> response) {
                if (response.isSuccessful()) {
                    //Log.d(TAG, response.toString());
                    mGetMazeMapListener.onSuccess(response.body());
                }
                //else {
                    //Log.d(TAG, response.toString());
                //}
            }

            @Override
            public void onFailure(Call<MazeMap> call, Throwable t) {
                Log.d(TAG, t.getMessage());
            }
        });
    }
}
