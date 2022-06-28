package edu.skku.cs.pa2.viewModel;

import android.app.Activity;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.JsonObject;

import java.lang.ref.WeakReference;
import java.util.HashMap;

import edu.skku.cs.pa2.callback.GetMazeMapListener;
import edu.skku.cs.pa2.model.HttpConnection;
import edu.skku.cs.pa2.model.MazeMap;

public class MazeViewModel extends ViewModel {
    private final String TAG = MazeViewModel.class.getSimpleName();
    private WeakReference<Activity> mActivityRef;

    private MutableLiveData<MazeMap> mazeMapML = new MutableLiveData<>();
    private HttpConnection httpConnection = new HttpConnection();
    private MazeMap mazeMap;

    // activity setting
    public void setParentContext(Activity parentContext){
        mActivityRef = new WeakReference<>(parentContext);
    }

    private GetMazeMapListener getMazeMapListener(){
        return mazeMap -> mazeMapML.setValue(mazeMap);
    }

    public void setGetMazeMapListener(){
        httpConnection.setmGetMazeMapListener(getMazeMapListener());
    }

    public MutableLiveData<MazeMap> getMazeMapML(){
        return this.mazeMapML;
    }

    public void getMazeMap(String name){
        httpConnection.getMazeMap(name);
    }



}
