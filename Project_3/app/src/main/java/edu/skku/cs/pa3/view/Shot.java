package edu.skku.cs.pa3.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import edu.skku.cs.pa3.R;

public class Shot {
    Bitmap shot;
    Context context;
    public int shx, shy;

    public Shot(Context context, int shx, int shy) {
        this.context = context;
        shot = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.magic_shot);
        this.shx = shx;
        this.shy = shy;
    }
    public Bitmap getShot(){
        return shot;
    }
}
