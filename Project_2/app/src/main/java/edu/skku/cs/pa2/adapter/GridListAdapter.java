package edu.skku.cs.pa2.adapter;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

import edu.skku.cs.pa2.R;
import edu.skku.cs.pa2.model.GridItem;

public class GridListAdapter extends BaseAdapter {
    private final String TAG = GridListAdapter.class.getSimpleName();
    private ArrayList<GridItem> items;
    private Context context;
    private int size;
    private int see;

    public GridListAdapter(){}

    public void setItems(ArrayList<GridItem> items, int size){
        this.items = items;
        this.size = size;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return items == null ? 0 : items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        context = parent.getContext();
        GridItem gridItem = items.get(position);

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.girdview_item, parent, false);
            int length = getPx(350)/size;
            convertView.setLayoutParams(new ViewGroup.LayoutParams(length, length));
        }

        LinearLayout linearGround = convertView.findViewById(R.id.linear_ground);
        ImageView img_display = convertView.findViewById(R.id.img_display);

        int top = 0;
        int left = 0;
        int bottom = 0;
        int right = 0;

        int marginDp = getPx(3);

        ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) linearGround.getLayoutParams();

        if(gridItem.isTop()){
            top = marginDp;
        }
        if(gridItem.isLeft()){
            left = marginDp;
        }
        if(gridItem.isBottom()){
            bottom = marginDp;
        }
        if(gridItem.isRight()){
            right = marginDp;
        }

        marginParams.setMargins(left, top, right, bottom);

        boolean isInPeople = gridItem.isInPeople();
        boolean isInGoal = gridItem.isInGoal();
        boolean isInHint = gridItem.isInHint();

        if(isInPeople){
            img_display.setImageResource(R.drawable.user);
            img_display.setRotation(this.see);
            gridItem.setInHint(false);
        }else if(isInGoal){
            img_display.setImageResource(R.drawable.goal);
        }else if(isInHint){
            img_display.setImageResource(R.drawable.hint);
        }else{
            img_display.setImageResource(0);
        }

        if(size == 10){
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(getPx(25), getPx(25));
            img_display.setLayoutParams(layoutParams);
        }

        return convertView;
    }

    public int getPx(int dp){
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }

    public void move(int see){
        this.see = see;
        notifyDataSetChanged();
    }

    public void setHint(){
        notifyDataSetChanged();
    }


}
