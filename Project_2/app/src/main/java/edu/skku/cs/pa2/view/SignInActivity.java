package edu.skku.cs.pa2.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import edu.skku.cs.pa2.R;
import edu.skku.cs.pa2.databinding.ActivitySignInBinding;
import edu.skku.cs.pa2.viewModel.SignInViewModel;

public class SignInActivity extends AppCompatActivity {
    private ActivitySignInBinding activitySignInBinding;
    private SignInViewModel signInViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySignInBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in);

        // view model 사용을 위한 초기화 작업
        signInViewModel = new ViewModelProvider(this, new ViewModelProvider
                .AndroidViewModelFactory(getApplication())).get(SignInViewModel.class);
        signInViewModel.setParentContext(this);

        activitySignInBinding.btnSign.setOnClickListener(v -> {
            String id = activitySignInBinding.edtId.getText().toString();
            JsonObject loginInfo = makeJson(id);
            signInViewModel.login(loginInfo, id);

        });

    }

    private JsonObject makeJson(String inputId){
        JsonObject jsonObj = new JsonObject();
        jsonObj.addProperty("username", inputId);

        return jsonObj;
    }
}