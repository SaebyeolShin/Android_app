package edu.skku.cs.pa3.presenter;

import android.util.Log;

import org.jetbrains.annotations.Contract;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import edu.skku.cs.pa3.contract.ContractLogin;
import edu.skku.cs.pa3.view.ActivityLogin;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PresenterConnection implements ContractLogin.presenter {
    private static final String TAG = PresenterConnection.class.toString() + "로그";
    private static final int LOGIN = 0;
    private static final int REGISTER = 1;
    private HttpConnection httpConn = HttpConnection.getInstance();
    private ContractLogin.view mView;

    public PresenterConnection(ContractLogin.view view) {
        this.mView = view;
    }

    @Override
    public void connection(JSONObject jData, int typeNum) {
        if (typeNum == LOGIN) {
            httpConn.requestWebServer(jData, "login", loginCallback);
        } else {
            httpConn.requestWebServer(jData, "adduser", registerCallback);
        }
    }

    //login response
    private final Callback loginCallback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            mView.success(e.toString(),LOGIN);
        }
        @Override
        public void onResponse(Call call, Response response) throws IOException {
            if (response.body() != null) {
                String body = response.body().string();
                mView.success(body,LOGIN);
            }
        }
    };

    //register response
    private final Callback registerCallback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            mView.success(e.toString(),REGISTER);
        }
        @Override
        public void onResponse(Call call, Response response) throws IOException {
            if (response.body() != null) {
                String body = response.body().string();
                mView.success(body,REGISTER);
            }
        }
    };


}





















