package edu.skku.cs.pa2.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import edu.skku.cs.pa2.R;
import edu.skku.cs.pa2.model.Maze;
import edu.skku.cs.pa2.view.MazeActivity;

public class MazeListAdapter extends BaseAdapter {
    private ArrayList<Maze> data;
    private Context context;

    public MazeListAdapter(Context context, ArrayList<Maze> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public Object getItem(int position) {
        if (data == null) {
            return null;
        }
        position %= data.size();
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        position %= data.size();
        View v = LayoutInflater.from(context).inflate(R.layout.listview_item, null);
        TextView txtName = v.findViewById(R.id.txt_maze);
        TextView txtSize = v.findViewById(R.id.txt_size);
        Button button = v.findViewById(R.id.btn_start);

        Maze maze = data.get(position);

        txtName.setText(maze.getName());
        txtSize.setText(String.valueOf(maze.getSize()));

        button.setOnClickListener(v1 -> {
            //버튼 시작
            Intent intent = new Intent(context, MazeActivity.class);
            intent.putExtra("name", txtName.getText().toString());
            context.startActivity(intent);
        });
        return v;
    }
}
