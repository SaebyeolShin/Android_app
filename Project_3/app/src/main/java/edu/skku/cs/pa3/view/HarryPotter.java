package edu.skku.cs.pa3.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;

import edu.skku.cs.pa3.R;

public class HarryPotter {
    Context context;
    Bitmap Harry;
    public int ox, oy;
    Random random;

    public HarryPotter(Context context) {
        this.context = context;
        Harry = BitmapFactory.decodeResource(context.getResources(), R.drawable.harry);
        random = new Random();
        ox = random.nextInt(Battle.screenWidth);
        oy = Battle.screenHeight - Harry.getHeight();
    }

    public Bitmap getHarry(){
        return Harry;
    }

    public int getHarryWidth(){
        return Harry.getWidth();
    }
}
