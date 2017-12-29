package com.example.simec3;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.JsonWriter;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SettingConnects extends AppCompatActivity {
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
        devConnses.add(new DevConns("Подача", this));
        devConnses.add(new DevConns("Шкаф", this));
        adapter = new SettsConnectAdapter(devConnses);

        JSONObject jsObj = new JSONObject();
        try {
            jsObj.put("adapter", adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            recyclerView.setAdapter((SettsConnectAdapter) jsObj.get("adapter"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            JsonWriter writer = new JsonWriter(new FileWriter("myadapter.json"));
            writer.beginObject().
        } catch (IOException e) {
            e.printStackTrace();
        }

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
