package edu.skku.cs.pa3.presenter;

import org.json.JSONObject;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;


public class HttpConnection {
    public static final MediaType JSON = MediaType.get("application/json; charest=utf-8");
    private OkHttpClient client;
    private static HttpConnection instance = new HttpConnection();


    public static HttpConnection getInstance() {
        return instance;
    }

    private HttpConnection() {
        this.client = new OkHttpClient();
    }

    public void requestWebServer(JSONObject jData, String lastUrl, Callback callback) {
        RequestBody body = RequestBody.create(jData.toString(),JSON);

        Request request = new Request.Builder()
                .url("https://xxxxxuser.amazonaws.com/dev/"+lastUrl)
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
    }
}

























