package com.example.simec3;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SettingConnects extends AppCompatActivity {
    BluetoothConnect podacha, shkaf;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_connects);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        final List<DevConns> devConnses = new ArrayList<>();
        podacha = new BluetoothConnect(SettingConnects.this, new ChangeListener() {

            @Override
            public void OnChangeListener(boolean isConnect) {

            }

            @Override
            public void OnDataReadListener(String data) {

            }

            @Override
            public void OnEnableListener(boolean isEnabled) {
                devConnses.add(new DevConns("Подача", podacha.ShowDialog()));
                recyclerView.setAdapter(new SettsConnectAdapter(devConnses));
                Toast.makeText(SettingConnects.this, "podacha", Toast.LENGTH_SHORT).show();
            }
        });
        shkaf = new BluetoothConnect(this, new ChangeListener() {
            @Override
            public void OnChangeListener(boolean isConnect) {

            }

            @Override
            public void OnDataReadListener(String data) {

            }

            @Override
            public void OnEnableListener(boolean isEnabled) {
                devConnses.add(new DevConns("Шкаф", shkaf.ShowDialog()));
                recyclerView.setAdapter(new SettsConnectAdapter(devConnses));
                Toast.makeText(SettingConnects.this, "shkaf", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
