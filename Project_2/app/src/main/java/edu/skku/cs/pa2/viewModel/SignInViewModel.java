package edu.skku.cs.pa2.viewModel;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;

import com.google.gson.JsonObject;

import java.lang.ref.WeakReference;

import edu.skku.cs.pa2.callback.LoginListener;
import edu.skku.cs.pa2.model.HttpConnection;
import edu.skku.cs.pa2.model.User;
import edu.skku.cs.pa2.view.MapSelectionActivity;

public class SignInViewModel extends ViewModel {
    private WeakReference<Activity> mActivityRef;
    private HttpConnection httpConnection = new HttpConnection();
    private String id;

    public SignInViewModel(){
        httpConnection.setmLoginListener(getLoginListener());
    }

    // activity setting
    public void setParentContext(Activity parentContext){
        mActivityRef = new WeakReference<>(parentContext);
    }

    public void login(JsonObject json, String id){
        httpConnection.getLoginResult(json);
        this.id = id;
    }
    
    public LoginListener getLoginListener(){
        return new LoginListener() {
            @Override
            public void onSuccess(boolean isLogin) {
                if(isLogin){
                    User user = User.getInstance();
                    user.setName(id);
                    Intent intent = new Intent(mActivityRef.get(), MapSelectionActivity.class);
                    mActivityRef.get().startActivity(intent);
                }else{
                    Toast.makeText(mActivityRef.get(), "Wrong User Name", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailed() {
                    Toast.makeText(mActivityRef.get(), "Network error", Toast.LENGTH_SHORT).show();
            }
        };
    }

}
