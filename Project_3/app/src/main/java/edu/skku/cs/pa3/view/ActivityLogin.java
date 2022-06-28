package edu.skku.cs.pa3.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import org.jetbrains.annotations.Contract;
import org.json.JSONException;
import org.json.JSONObject;

import edu.skku.cs.pa3.R;
import edu.skku.cs.pa3.contract.ContractLogin;
import edu.skku.cs.pa3.presenter.PresenterConnection;

public class ActivityLogin extends AppCompatActivity implements View.OnClickListener, ContractLogin.view {
    private static final String TAG = "로그";
    private static final int LOGIN = 0;
    private static final int REGISTER = 1;
    private Button btRegister, btLogin;
    private PresenterConnection presenter;
    private EditText edId, edPassword;
    private TextView tvError;
    private Boolean isResult = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        presenter = new PresenterConnection(this);

        tvError = findViewById(R.id.tv_error);
        btRegister = findViewById(R.id.bt_register);
        btLogin = findViewById(R.id.bt_login);
        edId = findViewById(R.id.ed_id);
        edPassword = findViewById(R.id.ed_password);

        btLogin.setOnClickListener(this);
        btRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        //jSON으로 데이터 변환
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", edId.getText().toString());
            jsonObject.put("passwd", edPassword.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //login,register 버튼 눌렀을때 아이디와 비밀번호를 가지고 통신 시작
        switch (view.getId()) {
            case R.id.bt_login:
                presenter.connection(jsonObject, LOGIN);
                break;

            case R.id.bt_register:
                presenter.connection(jsonObject, REGISTER);
                break;
        }
    }

    //서버 통신 후 response의 값을 통한 UI 작업 메서드
    @Override
    public void success(String result, int typeNum) {
        //response의 첫글자만 isResult에 저장 true 이면 t, false 이면 f가 저장됨.
        String str = result.substring(11, 12);
        isResult = str.equals("t");

        runOnUiThread(new Runnable() {
            @SuppressLint("SetTextI18n")
            @Override
            public void run() {
                if (isResult) {
                    Intent i = new Intent(ActivityLogin.this, MainActivity.class);
                    startActivity(i);

                    Toast.makeText(ActivityLogin.this, edId.getText().toString() + "님 환영합니다.", Toast.LENGTH_SHORT).show();
                } else if (typeNum == LOGIN) {
                    tvError.setText(getString(R.string.login_fail));
                } else if (typeNum == REGISTER) {
                    tvError.setText(getString(R.string.register_fail));
                } else {
                    tvError.setText("error : " + result);
                }
            }
        });
    }
}




































