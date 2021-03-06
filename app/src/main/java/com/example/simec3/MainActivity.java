package com.example.simec3;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
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

import com.example.ArduinoAIDL.IArduino;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawer;
    volatile public static byte pusk_data = 0b00000000;
    Toolbar toolbar;

    IArduino iArduino;

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iArduino = IArduino.Stub.asInterface(service);
            ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
            VPAdapter vpAdapter = new VPAdapter(getSupportFragmentManager());
            vpAdapter.addPages(new PriborsFragment(), "Приборы");
            vpAdapter.addPages(new PusksFragment(), "пускатели");
            viewPager.setAdapter(vpAdapter);
            TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
            tabs.setupWithViewPager(viewPager);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            iArduino = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.example.mybluetoothservice", "com.example.mybluetoothservice.MyBlueth"));
        startService(intent);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, toolbar, R.string.drawer_open, R.string.drawer_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.alarm_stop);
        fab.setOnClickListener(view -> pusk_data = 0b00000000);

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
