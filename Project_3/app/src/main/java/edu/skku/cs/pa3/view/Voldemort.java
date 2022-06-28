package edu.skku.cs.pa3.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;

import edu.skku.cs.pa3.R;

public class Voldemort {
    Context context;
    Bitmap Enemy;
    public int ex, ey;
    public int enemySpeed;
    Random random;

    public Voldemort(Context context) {
        this.context = context;
        Enemy = BitmapFactory.decodeResource(context.getResources(), R.drawable.voldemort);
        random = new Random();
        ex = 200 + random.nextInt(400);
        ey = 0;
        enemySpeed = 14 + random.nextInt(10);
    }

    public Bitmap getEnemy(){
        return Enemy;
    }

    public int getVoldmortWidth(){
        return Enemy.getWidth();
    }

}
