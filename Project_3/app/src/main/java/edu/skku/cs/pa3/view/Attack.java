package edu.skku.cs.pa3.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import edu.skku.cs.pa3.R;

public class Attack {
    Bitmap battle[] = new Bitmap[9];
    public int battleFrame;
    public int eX, eY;

    public Attack(Context context, int eX, int eY) {
        battle[0] = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.attack0);
        battle[1] = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.attack1);
        battle[2] = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.attack2);
        battle[3] = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.attack3);
        battle[4] = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.attack4);
        battle[5] = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.attack5);
        battle[6] = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.attack6);
        battle[7] = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.attack7);
        battle[8] = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.attack8);
        battleFrame = 0;
        this.eX = eX;
        this.eY = eY;
    }

    public Bitmap getBattle(int fightFrame){
        return battle[fightFrame];
    }
}
