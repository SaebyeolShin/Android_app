package edu.skku.cs.pa2.viewModel;

import android.app.Activity;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.lang.ref.WeakReference;
import java.util.List;

import edu.skku.cs.pa2.callback.GetMazeListener;
import edu.skku.cs.pa2.model.HttpConnection;
import edu.skku.cs.pa2.model.Maze;
import edu.skku.cs.pa2.model.User;

public class MapSelectionViewModel extends ViewModel {
    private final String TAG = MapSelectionViewModel.class.getSimpleName();
    private WeakReference<Activity> mActivityRef;
    private HttpConnection httpConnection = new HttpConnection();
    private User user = User.getInstance();

    private MutableLiveData<List<Maze>> mazeListML = new MutableLiveData<>();


    public String getLoginId(){
        //Log.d(TAG, "login Id : " + user.getName());
        return user.getName();
    }

    // activity setting
    public void setParentContext(Activity parentContext){
        mActivityRef = new WeakReference<>(parentContext);
    }

    public void setGetMazeListener(){
        httpConnection.setMGetMazeListener(getMazeListener());
    }

    public MutableLiveData<List<Maze>> getMazeListML(){
        return this.mazeListML;
    }

    private GetMazeListener getMazeListener(){
        return new GetMazeListener() {
            @Override
            public void onSuccess(List<Maze> mazeList) {
                mazeListML.setValue(mazeList);
            }

            @Override
            public void onFailed() {

            }
        };
    }

    public void getMazeMap(){
        httpConnection.getMazeList();
    }

}
