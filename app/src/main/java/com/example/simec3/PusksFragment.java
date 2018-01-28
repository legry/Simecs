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

public class PusksFragment extends Fragment {
    private RecyclerView recyclerView;
    IArduino iArduino;
    List<Pusks> dops;
    public void setiArduino(IArduino iArduino) {
        this.iArduino = iArduino;
        recyclerView.setAdapter(new PusksAdapter(dops));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.recycler, container, false);
        recyclerView = v.findViewById(R.id.rec);
        recyclerView.setHasFixedSize(true);
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        dops = new ArrayList<>();
        String[] doptitles = getResources().getStringArray(R.array.doptitles);
        for (String doptitle : doptitles) {
            dops.add(new Pusks(doptitle));
        }
        return v;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        } else if (newConfig.orientation ==Configuration.ORIENTATION_LANDSCAPE) {
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        }
    }
}
