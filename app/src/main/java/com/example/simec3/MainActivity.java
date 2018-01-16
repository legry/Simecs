package com.example.simec3;

import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
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
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawer;
    volatile public static byte pusk_data = 0b00000000;
    Toolbar toolbar;
    MyDrawer myDrawer;

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
        fab.setOnClickListener(view -> pusk_data = 0b00000000);
        SurfaceView mySurface = ((SurfaceView) findViewById(R.id.surfaceView));
        SurfaceHolder surfaceHolder = mySurface.getHolder();
        surfaceHolder.addCallback(new SurfaceHolder.Callback() {

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
                if (myDrawer != null) {
                    myDrawer.setRunning(false);
                    while (retry) {
                        try {
                            myDrawer.join();
                            retry = false;
                        } catch (InterruptedException ignored) {
                        }
                    }
                }
            }
        });
        mySurface.setOnTouchListener((v, m) -> {
            switch (m.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    myDrawer.setUpdn(true);
                    break;
                case MotionEvent.ACTION_UP:
                    myDrawer.setUpdn(false);
                    break;
            }
            return true;
        });
    }

    private class MyDrawer extends Thread {
        final SurfaceHolder surfaceHolder;
        private boolean run;
        private Paint pnt;
        private boolean updn;
        int i = 0;
        Rect rect;

        MyDrawer(SurfaceHolder surfaceHolder) {
            this.surfaceHolder = surfaceHolder;
            pnt = new Paint();
            pnt.setStyle(Paint.Style.FILL);
            pnt.setColor(getColor(android.R.color.holo_purple));
            rect = new Rect(0, 0, 200, 200);
        }

        @Override
        public void run() {

            Canvas canvas = null;
            while (run) {
                if (((updn) && (i < 50)) || (!(updn) && (i > 0))) {
                    try {
                        synchronized (surfaceHolder) {
                            if ((updn) && (i < 50)) {
                                rect.top += 1;
                                rect.left += 1;
                                rect.right -= 1;
                                rect.bottom -= 1;
                                i++;
                            } else if (!(updn) && (i > 0)) {

                                rect.top -= 1;
                                rect.left -= 1;
                                rect.right += 1;
                                rect.bottom += 1;

                                i--;
                            }
                            canvas = surfaceHolder.lockCanvas();
                            if (canvas != null) {
                                canvas.drawColor(getColor(android.R.color.white));
                                canvas.drawRect(rect, pnt);
                            }
                        }
                        try {
                            sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } finally {
                        if (canvas != null) {
                            surfaceHolder.unlockCanvasAndPost(canvas);
                        }
                    }
                }
            }
        }

        void setRunning(boolean run) {
            this.run = run;
        }

        void setUpdn(boolean updn) {
            this.updn = updn;
        }
    }

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
