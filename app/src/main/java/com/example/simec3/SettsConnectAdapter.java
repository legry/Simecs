package com.example.simec3;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.List;

class SettsConnectAdapter extends RecyclerView.Adapter<SettsConnectAdapter.MyHolder> {
    private List<DevConns> devConnses;
    private BluetoothConnect btConn;

    SettsConnectAdapter(List<DevConns> devConnses, BluetoothConnect btConn) {
        this.devConnses = devConnses;
        this.btConn = btConn;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sets_conns, parent, false));
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        holder.titles.setText(devConnses.get(position).getTitles());
        holder.devices.setAdapter(devConnses.get(position).getDevices());
        holder.devices.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                btConn.setDevice(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return devConnses.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView titles, statuses;
        CheckBox autconns;
        Spinner devices;
        ToggleButton connects;
        MyHolder(View itemView) {
            super(itemView);
            titles = itemView.findViewById(R.id.dev_titles);
            statuses = itemView.findViewById(R.id.dev_statuses);
            autconns = itemView.findViewById(R.id.autconns);
            devices = itemView.findViewById(R.id.devices);
            connects = itemView.findViewById(R.id.connects);
        }
    }
}
