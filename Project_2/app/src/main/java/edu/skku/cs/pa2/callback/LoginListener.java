package edu.skku.cs.pa2.callback;

public interface LoginListener {
    public void onSuccess(boolean isLogin);
    public void onFailed();
}
