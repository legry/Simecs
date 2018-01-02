package com.example.simec3;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;


public class SettingConnects extends AppCompatActivity implements View.OnClickListener {
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("simec")) {
                ((TextView)findViewById(R.id.from_service)).setText(String.valueOf(intent.getIntExtra("mybluth", 0)));
                ((TextView)findViewById(R.id.from_activity)).setText(String.valueOf(intent.getIntExtra("BluthSetts", 0)));
            }
        }
    };
    ComponentName serviceName = new ComponentName("com.example.mybluetoothservice", "com.example.mybluetoothservice.MyBlueth"),
    activityName = new ComponentName("com.example.mybluetoothservice", "com.example.mybluetoothservice.BluthSetts");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_connects);
        findViewById(R.id.start_service).setOnClickListener(this);
        findViewById(R.id.start_activity).setOnClickListener(this);
        findViewById(R.id.stop_service).setOnClickListener(this);
        registerReceiver(receiver, new IntentFilter("simec"));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start_service:
                startService(new Intent().setComponent(serviceName).putExtra("action", "simec"));
                break;
            case R.id.stop_service:
                stopService(new Intent().setComponent(serviceName));
                break;
            case R.id.start_activity:
                startActivity(new Intent().setComponent(activityName).putExtra("action", "simec"));
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}
