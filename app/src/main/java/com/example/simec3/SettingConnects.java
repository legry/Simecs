package com.example.simec3;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public class SettingConnects extends AppCompatActivity {
    BluetoothConnect podacha, shkaf;
    private RecyclerView recyclerView;
    SettsConnectAdapter adapter;
    List<DevConns> devConnses;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_connects);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        devConnses = new ArrayList<>();
        adapter = new SettsConnectAdapter(devConnses);
        recyclerView.setAdapter(adapter);
        podacha = new BluetoothConnect(SettingConnects.this, new ChangeListener() {

            @Override
            public void OnChangeListener(boolean isConnect) {

            }

            @Override
            public void OnDataReadListener(String data) {

            }

            @Override
            public void OnEnableListener(ArrayAdapter<String> devices) {
                devConnses.add(new DevConns("Подача", devices, podacha));
                recyclerView.getAdapter().notifyDataSetChanged();
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
            public void OnEnableListener(ArrayAdapter<String> devices) {
                devConnses.add(new DevConns("Шкаф", devices, shkaf));
                recyclerView.getAdapter().notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
    }
}
