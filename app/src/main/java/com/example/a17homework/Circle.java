package com.example.a17homework;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

public class Circle extends SurfaceView implements SurfaceHolder.Callback {

    int x = 0, y = 0, t_x = 0, t_y = 0, dy = 0, dx = 0, n=0;

    private SurfaceHolder surfaceHolder;
    private final DrawThread drawThread;

    public Circle(Context context) {
        super(context);
        getHolder().addCallback(this);
        drawThread = new DrawThread();
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        this.surfaceHolder = surfaceHolder;
        drawThread.start();
        x = getWidth() / 2;
        y = getHeight() / 2;
        t_x = (int) x;
        t_y = (int) y;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        t_x = (int) event.getX();
        t_y = (int) event.getY();
        return super.onTouchEvent(event);
    }

    public void update(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        if (t_x != x || t_y != y) {
            n = (int) Math.sqrt(Math.pow(t_x - x, 2) + Math.pow(t_y - y, 2)) / 20;
            dy = (int) (y - t_y) / n;
            dx = (int) (x - t_x) / n;
            if (t_y != y) y -= dy;
            else y = t_y;
            if (t_x != x) x -= dx;
            else x = t_x;
        }
        canvas.drawCircle((float) x, (float) y, 50, paint);
    }

    private class DrawThread extends Thread {

        private volatile boolean running = true;

        @Override
        public void run() {
            Canvas canvas;
            while (running) {
                canvas = surfaceHolder.lockCanvas();
                try {
                    sleep(100);
                    canvas.drawColor(Color.BLUE);
                    update(canvas);
                } catch (Exception e) {
                    Log.e("RRR", "run: ");
                } finally {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

    }
}
