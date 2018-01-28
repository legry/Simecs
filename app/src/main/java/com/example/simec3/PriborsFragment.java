package com.example.simec3;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ArduinoAIDL.IArduino;

import java.util.ArrayList;
import java.util.List;

public class PriborsFragment extends Fragment {
    private RecyclerView recyclerView;
    IArduino iArduino;
    List<Pribors> osnovs;
    public void setiArduino(IArduino iArduino) {
        this.iArduino = iArduino;
        recyclerView.setAdapter(new PriborsAdapter(osnovs));
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.recycler, container, false);
        recyclerView = v.findViewById(R.id.rec);
        recyclerView.setHasFixedSize(true);
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        osnovs = new ArrayList<>();
        String[] titles = getResources().getStringArray(R.array.titles);
        String[] showcoments = getResources().getStringArray(R.array.showcoments);
        for (int i = 0; i < titles.length; i++) {
            osnovs.add(new Pribors(titles[i], showcoments[i]));
        }
        return v;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        } else if (newConfig.orientation ==Configuration.ORIENTATION_LANDSCAPE) {
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        }
    }
}
