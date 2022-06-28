package edu.skku.cs.pa2.model;

import com.google.gson.annotations.SerializedName;

public class LoginResult {

    @SerializedName("success")
    private boolean isSuccess;

    public boolean isSuccess() {
        return isSuccess;
    }
}
