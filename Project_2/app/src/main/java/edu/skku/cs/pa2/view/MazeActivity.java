package edu.skku.cs.pa2.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import edu.skku.cs.pa2.R;
import edu.skku.cs.pa2.adapter.GridListAdapter;
import edu.skku.cs.pa2.databinding.ActivityMazeBinding;
import edu.skku.cs.pa2.model.GridItem;
import edu.skku.cs.pa2.model.MazeMap;
import edu.skku.cs.pa2.viewModel.MapSelectionViewModel;
import edu.skku.cs.pa2.viewModel.MazeViewModel;

public class MazeActivity extends AppCompatActivity {
    private final String TAG = MazeActivity.class.getSimpleName();
    private ActivityMazeBinding activityMazeBinding;
    private MazeViewModel mazeViewModel;
    private String mazeName;

    private String[] mazeInfo;
    private int mapSize;
    private int[][] mazeMap;

    private GridListAdapter gridListAdapter;
    private ArrayList<GridItem> cellList = new ArrayList<>();

    //play value
    private int posX = 0;
    private int posY = 0;
    private int goalX = 0;
    private int goalY = 0;
    private int see = 0; // 0 - top, 1 - left, 2 - bottom, 3 - right
    private int turn = 0;
    private boolean useHint;

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMazeBinding = DataBindingUtil.setContentView(this, R.layout.activity_maze);

        // view model 사용을 위한 초기화 작업
        mazeViewModel = new ViewModelProvider(this, new ViewModelProvider
                .AndroidViewModelFactory(getApplication())).get(MazeViewModel.class);
        mazeViewModel.setParentContext(this);

        Intent intent = getIntent();
        mazeName = intent.getStringExtra("name");

        mazeViewModel.setGetMazeMapListener();

        mazeViewModel.getMazeMapML().observe(this, data -> {
            MazeMap mazeMap = mazeViewModel.getMazeMapML().getValue();
            if (mazeMap != null) {
                //Log.d(TAG, "observe");
                //Log.d(TAG, mazeMap.getMaze());
                valueSetting(mazeMap);
            }
        });

        mazeViewModel.getMazeMap(this.mazeName);

        activityMazeBinding.btnHint.setOnClickListener(v -> {
            if(!this.useHint) getHint();
        });

        activityMazeBinding.btnLeft.setOnClickListener(v -> {
            moveLeft();
        });

        activityMazeBinding.btnUp.setOnClickListener(v -> {
            moveTop();
        });

        activityMazeBinding.btnDown.setOnClickListener(v -> {
            moveBottom();
        });

        activityMazeBinding.btnRight.setOnClickListener(v -> {
            moveRight();
        });

    }

    private void setGridColNum(int size) {
        activityMazeBinding.gridMaze.setNumColumns(size);
    }

    // 설계
    private void valueSetting(MazeMap mazeMap) {
        this.mazeInfo = mazeMap.getMaze().split("\\n");
        this.mapSize = Integer.parseInt(mazeInfo[0]);
        this.mazeMap = new int[mapSize][mapSize];

        setGridColNum(this.mapSize);

        for (int i = 0; i < this.mapSize; i++) {
            String[] line = this.mazeInfo[i + 1].split(" ");
            for (int j = 0; j < this.mapSize; j++) {
                this.mazeMap[i][j] = Integer.parseInt(line[j]);
            }
        }

        // debug
        //Log.d(TAG, String.valueOf(this.mapSize));
        //for (int i = 0; i < this.mapSize; i++) {
        //    for (int j = 0; j < this.mapSize; j++) {
        //        Log.d(TAG, String.valueOf(this.mazeMap[i][j]));
        //    }
        //}

        cellSetting();
    }

    private void cellSetting() {
        for (int i = 0; i < this.mapSize; i++) {
            for (int j = 0; j < this.mapSize; j++) {
                GridItem gridItem = new GridItem();
                int wall = this.mazeMap[i][j];
                //Log.d(TAG, String.valueOf(wall));
                if (wall - 8 >= 0) {
                    //Log.d(TAG, "top");
                    wall -= 8;
                    gridItem.setTop(true);
                }
                if (wall - 4 >= 0) {
                    //Log.d(TAG, "left");
                    wall -= 4;
                    gridItem.setLeft(true);
                }
                if (wall - 2 >= 0) {
                    //Log.d(TAG, "bottom");
                    wall -= 2;
                    gridItem.setBottom(true);
                }
                if (wall - 1 >= 0) {
                    //Log.d(TAG, "right");
                    wall -= 1;
                    gridItem.setRight(true);
                }
                this.cellList.add(gridItem);
            }
        }
        settingMap();
    }


    // observe - map setting
    private void settingMap() {
        gridListAdapter = new GridListAdapter();
        gridListAdapter.setItems(this.cellList, this.mapSize);

        playSetting();
    }

    private void playSetting() {
        this.goalX = this.mapSize - 1;
        this.goalY = this.mapSize - 1;
        int nowLocation = this.posX * this.mapSize + this.posY;
        GridItem nowCell = this.cellList.get(nowLocation);

        nowCell.setInPeople(true);

        int goalLocation = this.goalX * this.mapSize + this.goalY;
        GridItem goalCell = this.cellList.get(goalLocation);

        goalCell.setInGoal(true);

        activityMazeBinding.gridMaze.setAdapter(gridListAdapter);
    }

    // 방향키 누를시 turn 1 증가(이동시에만, 이동 불가능 시 증가 X)
    private void moveTop() {
        // 갈 수 있는지 여부 확인, 벽에 막혀있는지
        int nowLocation = this.posX * this.mapSize + this.posY;
        GridItem nowCell = this.cellList.get(nowLocation);

        if (nowCell.isTop()) {
            return;
        }

        nowCell.setInPeople(false);
        int dest_posX = this.posX -= 1;
        int dest_posY = this.posY;
        int nextLoaction = dest_posX * this.mapSize + dest_posY;
        GridItem nextCell = this.cellList.get(nextLoaction);

        nextCell.setInPeople(true);

        setUserLocation(dest_posX, dest_posY);

        add_turn();

        this.gridListAdapter.move(0);
    }

    private void moveLeft() {
        int nowLocation = this.posX * this.mapSize + this.posY;
        GridItem nowCell = this.cellList.get(nowLocation);

        if (nowCell.isLeft()) {
            return;
        }

        nowCell.setInPeople(false);
        int dest_posX = this.posX;
        int dest_posY = this.posY -= 1;
        int nextLoaction = dest_posX * this.mapSize + dest_posY;
        GridItem nextCell = this.cellList.get(nextLoaction);

        nextCell.setInPeople(true);

        setUserLocation(dest_posX, dest_posY);

        add_turn();

        this.gridListAdapter.move(270);
    }

    private void moveBottom() {
        int nowLocation = this.posX * this.mapSize + this.posY;
        GridItem nowCell = this.cellList.get(nowLocation);

        if (nowCell.isBottom()) {
            return;
        }

        nowCell.setInPeople(false);
        int dest_posX = this.posX + 1;
        int dest_posY = this.posY;
        int nextLoaction = dest_posX * this.mapSize + dest_posY;
        GridItem nextCell = this.cellList.get(nextLoaction);

        nextCell.setInPeople(true);

        setUserLocation(dest_posX, dest_posY);

        add_turn();

        this.gridListAdapter.move(180);
    }

    private void moveRight() {
        int nowLocation = this.posX * this.mapSize + this.posY;
        GridItem nowCell = this.cellList.get(nowLocation);

        if (nowCell.isRight()) {
            return;
        }

        nowCell.setInPeople(false);

        int dest_posX = this.posX;
        int dest_posY = this.posY + 1;
        int nextLoaction = dest_posX * this.mapSize + dest_posY;
        GridItem nextCell = this.cellList.get(nextLoaction);

        nextCell.setInPeople(true);

        setUserLocation(dest_posX, dest_posY);
        add_turn();

        this.gridListAdapter.move(90);
    }

    private void setUserLocation(int x, int y) {
        this.posX = x;
        this.posY = y;

        if (this.posX == this.goalX && this.posY == this.goalY) {
            Toast.makeText(this, "Finish!", Toast.LENGTH_SHORT).show();
        }
    }

    private void add_turn() {
        turn += 1;
        activityMazeBinding.txtTurn.setText("Turn : " + turn);
    }

    private void getHint() {
        Bfs bfs = new Bfs(this.mapSize);
        int hintLocation = bfs.bfs(this.posX, this.posY);
        //Log.d(TAG, String.valueOf(hintLocation));
        setHint(hintLocation);
    }

    private void setHint(int hintLocation){
        GridItem hintCell = this.cellList.get(hintLocation);
        hintCell.setInHint(true);
        this.gridListAdapter.setHint();
        this.useHint = true;
    }

    public class Dot {
        private int x;
        private int y;
        private int routeNum;

        public Dot(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getRouteNum() {
            return routeNum;
        }

        public void setRouteNum(int routeNum) {
            this.routeNum = routeNum;
        }
    }

    public class Bfs {
        int nV; // 정점의 수
        int nE; // 간선의 수
        int[][] arr2d; // 정점간 연결관계 저장 배열
        boolean[] check; // 방문한 정점 체크 배열
        int count=0;


        public Bfs(int size) {
            nV = size;
            check = new boolean[size * size];
        }

        public int bfs(int posX, int posY) {
            Queue<Dot> q = new LinkedList<>(); // 선입선출( 먼저들어온것이 먼저 나간다.) 의 특징을 가지는 큐를 활용해서 bfs 탐색을 시작
            Dot dot = new Dot(posX, posY);
            int startIndex = dot.getX() * mapSize + dot.getY();
            check[startIndex] = true;
            q.offer(dot); // 처음 시작지점 큐에 넣는다.
            while (!q.isEmpty()) { // 큐에 있는 모든 정점에 방문할때까지 반복
                count += 1;
                Dot temp = q.poll(); // 큐에 있는 방문한 정점을 하나 빼줌
                int nowIndex = temp.getX() * mapSize + temp.getY();

                //Log.w(TAG, "방문한 정점은 -> " + nowIndex + " ");
                GridItem gridItem = cellList.get(nowIndex);
                //Log.d(TAG, gridItem.toString());
                if (gridItem.isInGoal()) {
                    int result=-1;
                    if(temp.getRouteNum() == 1){
                        result = startIndex - mapSize;
                    }else if(temp.getRouteNum() == 2){
                        result = startIndex - 1;
                    }else if(temp.getRouteNum() == 3){
                        result = startIndex + mapSize;
                    }else if(temp.getRouteNum() == 4){
                        result = startIndex +1;
                    }
                    return result;
                }

                if (!gridItem.isTop()) {
                    int nextIndex = (temp.getX()-1) * mapSize + temp.getY();
                    if (!check[nextIndex]) {
                        //Log.d(TAG, "top");
                        Dot nextDot = new Dot(temp.getX() - 1, temp.getY());
                        if(count == 1){
                            nextDot.setRouteNum(1);
                        }else{
                            nextDot.setRouteNum(temp.getRouteNum());
                        }
                        q.offer(nextDot);
                        check[nowIndex] = true;
                    }
                }
                if (!gridItem.isLeft()) {
                    int nextIndex = temp.getX() * mapSize + temp.getY()-1;
                    if (!check[nextIndex]) {
                        //Log.d(TAG, "left");
                        Dot nextDot = new Dot(temp.getX(), temp.getY() - 1);
                        if(count == 1){
                            nextDot.setRouteNum(2);
                        }else{
                            nextDot.setRouteNum(temp.getRouteNum());
                        }
                        q.offer(nextDot);
                        check[nowIndex] = true;
                    }
                }
                if (!gridItem.isBottom()) {
                    int nextIndex = (temp.getX()+1) * mapSize + temp.getY();
                    if (!check[nextIndex]) {
                        //Log.d(TAG, "bottom");
                        Dot nextDot = new Dot(temp.getX() + 1, temp.getY());
                        if(count == 1){
                            nextDot.setRouteNum(3);
                        }else{
                            nextDot.setRouteNum(temp.getRouteNum());
                        }
                        q.offer(nextDot);
                        check[nowIndex] = true;
                    }
                }
                if (!gridItem.isRight()) {
                    int nextIndex = temp.getX() * mapSize + temp.getY()+1;
                    if (!check[nextIndex]) {
                        //Log.d(TAG, "right");
                        Dot nextDot = new Dot(temp.getX(), temp.getY() + 1);
                        if(count == 1){
                            nextDot.setRouteNum(4);
                        }else{
                            nextDot.setRouteNum(temp.getRouteNum());
                        }
                        q.offer(nextDot);
                        check[nextIndex] = true;
                    }
                }
            }
            return -1;
        }
    }

}