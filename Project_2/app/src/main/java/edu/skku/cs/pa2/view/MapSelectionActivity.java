package edu.skku.cs.pa2.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import edu.skku.cs.pa2.R;
import edu.skku.cs.pa2.adapter.MazeListAdapter;
import edu.skku.cs.pa2.databinding.ActivityMapSelectionBinding;
import edu.skku.cs.pa2.model.Maze;
import edu.skku.cs.pa2.model.User;
import edu.skku.cs.pa2.viewModel.MapSelectionViewModel;
import edu.skku.cs.pa2.viewModel.SignInViewModel;

public class MapSelectionActivity extends AppCompatActivity {
    private final String TAG = MapSelectionActivity.class.getSimpleName();
    private ActivityMapSelectionBinding activityMapSelectionBinding;
    private MapSelectionViewModel mapSelectionViewModel;

    private MazeListAdapter adapter;
    private List<Maze> mazeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMapSelectionBinding = DataBindingUtil.setContentView(this, R.layout.activity_map_selection);
        // view model 사용을 위한 초기화 작업
        mapSelectionViewModel = new ViewModelProvider(this, new ViewModelProvider
                .AndroidViewModelFactory(getApplication())).get(MapSelectionViewModel.class);
        mapSelectionViewModel.setParentContext(this);

        activityMapSelectionBinding.txtUserID.setText(mapSelectionViewModel.getLoginId());

        mapSelectionViewModel.getMazeListML().observe(this, mazes -> {
            this.mazeList = mapSelectionViewModel.getMazeListML().getValue();
            if(mazeList != null){
                /*  listview setting  */
                adapter = new MazeListAdapter(this, (ArrayList<Maze>) this.mazeList);
                activityMapSelectionBinding.listItem.setAdapter(adapter);
                //listView setting
            }
            //else{
                //None
            //}
        });

        mapSelectionViewModel.setGetMazeListener();

        mapSelectionViewModel.getMazeMap();
    }
}