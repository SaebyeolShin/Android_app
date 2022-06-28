package edu.skku.cs.pa3.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Handler;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

import edu.skku.cs.pa3.R;

public class Battle extends View {
    private static final String TAG = "로그";
    Context context;
    Bitmap background, lifeImage;
    Handler handler;
    long UPDATE_MILLIS = 30;
    public static int screenWidth, screenHeight;
    int points = 0;
    int life = 3;
    Paint scorePaint;
    int TEXT_SIZE = 80;
    boolean paused = false;
    HarryPotter harryPotter;
    Voldemort voldemort;
    Random random;
    public ArrayList<Shot> enemyShots, ourShots;
    Attack attack;
    ArrayList<Attack> attacks;
    boolean enemyShotAction = false;

    final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            invalidate();
        }
    };

    public Battle(Context context) {
        super(context);
        this.context = context;
        Display display = ((Activity) getContext()).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;
        random = new Random();
        enemyShots = new ArrayList<>();
        ourShots = new ArrayList<>();
        attacks = new ArrayList<>();
        harryPotter = new HarryPotter(context);
        voldemort = new Voldemort(context);
        handler = new Handler();
        background = BitmapFactory.decodeResource(context.getResources(), R.drawable.background);
        lifeImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.life);
        scorePaint = new Paint();
        scorePaint.setColor(Color.RED);
        scorePaint.setTextSize(TEXT_SIZE);
        scorePaint.setTextAlign(Paint.Align.LEFT);
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawBitmap(background, 0, 0, null);
        canvas.drawText("Success : " + points, 0, TEXT_SIZE, scorePaint);
        for (int i = life; i >= 1; i--) {
            canvas.drawBitmap(lifeImage, screenWidth - lifeImage.getWidth() * i, 0, null);
        }
        if (life == 0) {
            paused = true;
            handler = null;
            Intent intent = new Intent(context, GameOver.class);
            intent.putExtra("points", points);
            context.startActivity(intent);
            ((Activity) context).finish();
        }
        voldemort.ex += voldemort.enemySpeed;
        if (voldemort.ex + voldemort.getVoldmortWidth() >= screenWidth) {
            voldemort.enemySpeed *= -1;
        }
        if (voldemort.ex <= 0) {
            voldemort.enemySpeed *= -1;
        }
        if (enemyShotAction == false) {
            if (voldemort.ex >= 200 + random.nextInt(400)) {
                Shot enemyShot = new Shot(context, voldemort.ex + voldemort.getVoldmortWidth() / 2, voldemort.ey);
                enemyShots.add(enemyShot);
                enemyShotAction = true;
            }
            if (voldemort.ex >= 400 + random.nextInt(800)) {
                Shot enemyShot = new Shot(context, voldemort.ex + voldemort.getVoldmortWidth() / 2, voldemort.ey);
                enemyShots.add(enemyShot);
                enemyShotAction = true;
            } else {
                Shot enemyShot = new Shot(context, voldemort.ex + voldemort.getVoldmortWidth() / 2, voldemort.ey);
                enemyShots.add(enemyShot);
                enemyShotAction = true;
            }
        }
        canvas.drawBitmap(voldemort.getEnemy(), voldemort.ex, voldemort.ey, null);
        if (harryPotter.ox > screenWidth - harryPotter.getHarryWidth()) {
            harryPotter.ox = screenWidth - harryPotter.getHarryWidth();
        } else if (harryPotter.ox < 0) {
            harryPotter.ox = 0;
        }

        canvas.drawBitmap(harryPotter.getHarry(), harryPotter.ox, harryPotter.oy, null);
        for (int i = 0; i < 1; i++) {
            enemyShots.get(i).shy += 15;
            canvas.drawBitmap(enemyShots.get(i).getShot(), enemyShots.get(i).shx, enemyShots.get(i).shy, null);
            if ((enemyShots.get(i).shx >= harryPotter.ox)
                    && enemyShots.get(i).shx <= harryPotter.ox + harryPotter.getHarryWidth()
                    && enemyShots.get(i).shy >= harryPotter.oy
                    && enemyShots.get(i).shy <= screenHeight) {
                life--;
                enemyShots.remove(i);
                attack = new Attack(context, harryPotter.ox, harryPotter.oy);
                attacks.add(attack);
            } else if (enemyShots.get(i).shy >= screenHeight) {
                enemyShots.remove(i);
            }
            if (enemyShots.size() < 1) {
                enemyShotAction = false;
            }
        }

        for (int i = 0; i < ourShots.size(); i++) {
            ourShots.get(i).shy -= 15;
            canvas.drawBitmap(ourShots.get(i).getShot(), ourShots.get(i).shx, ourShots.get(i).shy, null);
            if ((ourShots.get(i).shx >= voldemort.ex)
                    && ourShots.get(i).shx <= voldemort.ex + voldemort.getVoldmortWidth()
                    && ourShots.get(i).shy <= voldemort.getVoldmortWidth()
                    && ourShots.get(i).shy >= voldemort.ey) {
                points++;
                ourShots.remove(i);
                attack = new Attack(context, voldemort.ex, voldemort.ey);
                attacks.add(attack);
            } else if (ourShots.get(i).shy <= 0) {
                ourShots.remove(i);
            }
        }
        // Do the explosion
        for (int i = 0; i < attacks.size(); i++) {
            canvas.drawBitmap(attacks.get(i).getBattle(attacks.get(i).battleFrame), attacks.get(i).eX, attacks.get(i).eY, null);
            attacks.get(i).battleFrame++;
            if (attacks.get(i).battleFrame > 8) {
                attacks.remove(i);
            }
        }
        if (!paused)
            handler.postDelayed(runnable, UPDATE_MILLIS);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int touchX = (int) event.getX();
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (ourShots.size() < 1) {
                Shot ourShot = new Shot(context, harryPotter.ox + harryPotter.getHarryWidth() / 2, harryPotter.oy);
                ourShots.add(ourShot);
            }
        }
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            harryPotter.ox = touchX;
        }

        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            harryPotter.ox = touchX;
        }
        return true;
    }
}

