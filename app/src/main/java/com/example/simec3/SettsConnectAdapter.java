package com.example.simec3;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.List;

class SettsConnectAdapter extends RecyclerView.Adapter<SettsConnectAdapter.MyHolder> {
    private List<DevConns> devConnses;

    SettsConnectAdapter(List<DevConns> devConnses) {
        this.devConnses = devConnses;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sets_conns, parent, false));
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, final int position) {
        holder.titles.setText(devConnses.get(position).getTitles());
        devConnses.get(position).getBluetoothConnect().setEnabledListener(devices -> {
            holder.devices.setAdapter(devices);
            SettsConnectAdapter.this.notifyDataSetChanged();
            holder.devices.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    devConnses.get(position).getBluetoothConnect().setDevice(i);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        });

        devConnses.get(position).getBluetoothConnect().setConnectChangeListener(isConnect -> {
            if (isConnect) {
                holder.statuses.setText("Статус: соединение установлено");
            } else {
                holder.statuses.setText("Статус: нет соединение");
            }
            holder.devices.setEnabled(!isConnect);
        });

        holder.connects.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                devConnses.get(position).getBluetoothConnect().ConnectCreat();
            } else {
                devConnses.get(position).getBluetoothConnect().ConnectDestroy();
            }
        });

        holder.autconns.setOnCheckedChangeListener((compoundButton, b) -> {
            devConnses.get(holder.getAdapterPosition()).getBluetoothConnect().AutoConnect(b);
            holder.connects.setChecked(b);
            holder.connects.setEnabled(!b);
            holder.devices.setEnabled(!b);
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
