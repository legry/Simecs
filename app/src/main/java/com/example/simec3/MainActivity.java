package com.example.simec3;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawer;
    volatile public static byte pusk_data = 0b00000000;
    Toolbar toolbar;
    Bitmap bmp;
    private boolean running = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, toolbar, R.string.drawer_open, R.string.drawer_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        VPAdapter vpAdapter = new VPAdapter(getSupportFragmentManager());
        vpAdapter.addPages(new PriborsFragment(), "Приборы");
        vpAdapter.addPages(new PusksFragment(), "пускатели");
        viewPager.setAdapter(vpAdapter);
        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.alarm_stop);
        fab.setOnClickListener(view -> {pusk_data = 0b00000000; running = !running;});
        bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        ((SurfaceView) findViewById(R.id.surfaceView)).getHolder().addCallback(new SurfaceHolder.Callback() {
            MyDrawer myDrawer;
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                myDrawer = new MyDrawer(surfaceHolder);
                myDrawer.setRunning(true);
                myDrawer.start();
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                boolean retry = true;
                myDrawer.setRunning(false);
                while (retry) {
                    try {
                        myDrawer.join();
                        retry = false;
                    } catch (InterruptedException e) {
                    }
                }
            }
        });
        final Handler handler = new Handler();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(runnable);
            }
        }, 0, 100);
    }

    private class MyDrawer extends Thread {
        final SurfaceHolder surfaceHolder;
        private int x = 0;

        MyDrawer(SurfaceHolder surfaceHolder) {
            this.surfaceHolder = surfaceHolder;
        }

        @Override
        public void run() {
            Canvas canvas = null;
            while (running)
                try {
                    canvas = surfaceHolder.lockCanvas();
                    synchronized (surfaceHolder) {
                        x++;
                        try {
                            Draw(canvas, x, 1);
                            sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } finally {
                    if (canvas != null) {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }
        }

        void setRunning(boolean run) {
            running = run;
        }
    }

    protected void Draw(Canvas canvas, int x, int xSpeed)
    {
        if (x == 100 - bmp.getWidth())
        {
            xSpeed = -1;
        }
        if (x == 0)
        {
            xSpeed = 1;
        }
        x = x + xSpeed;
        canvas.drawColor(Color.BLACK);
        canvas.drawBitmap(bmp, x, 10, null);
    }

    Runnable runnable = () -> {
        toolbar.setTitle(String.valueOf(pusk_data));
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.setts_app, menu);
        return true;
    }

    private void openBluthSetts() {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.example.mybluetoothservice", "com.example.mybluetoothservice.BluthSetts"));
        intent.putExtra("action", "simec3");
        intent.putExtra("devices", getResources().getStringArray(R.array.devices));
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.corr:

                break;
            case R.id.connects:
                openBluthSetts();
                break;
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }

}
