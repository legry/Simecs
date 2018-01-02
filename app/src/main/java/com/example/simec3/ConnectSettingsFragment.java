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

import java.util.ArrayList;
import java.util.List;

public class ConnectSettingsFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<DevConns> devConnses = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.setts_conn_frgm, container, false);
        recyclerView = v.findViewById(R.id.recycler);

        recyclerView.setHasFixedSize(true);
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        devConnses.add(new DevConns("Подача", getContext()));
        devConnses.add(new DevConns("Шкаф", getContext()));
        SettsConnectAdapter adapter = new SettsConnectAdapter(devConnses);
        recyclerView.setAdapter(adapter);
        return v;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
    }
}
