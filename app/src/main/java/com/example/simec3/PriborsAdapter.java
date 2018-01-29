package com.example.simec3;


import android.content.Context;
import android.os.RemoteException;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ArduinoAIDL.IArduino;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

class PriborsAdapter extends RecyclerView.Adapter<PriborsAdapter.MyHolder> {
    private List<Pribors> osnovs;
    private final IArduino iArduino;
    private String[] cmnds;
    private Map<String, String> arduinos = new HashMap<>();
    PriborsAdapter(Context context, List<Pribors> osnovs, IArduino iArduino) {
        this.osnovs = osnovs;
        this.iArduino = iArduino;
        cmnds = context.getResources().getStringArray(R.array.pribor_comands);
        for (int i = 0; i < 3; i++) {
            if (i < 2) {
                arduinos.put(Integer.toString(i), "stanok");
            } else if (i < 3) {
                arduinos.put(Integer.toString(i), "podacha");
            }
        }
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.pribors, parent, false));
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, int position) {
        holder.titles.setText(osnovs.get(position).getTitles());
        holder.showcoments.setText(osnovs.get(position).getShowcoments());
        new Thread(() -> {
            while (true) {
                try {
                    String rslt = "----";
                    synchronized (iArduino) {
                        rslt = iArduino.comandBridge(arduinos.get(Integer.toString(position)), cmnds[position]);
                    }
                    holder.shows.setText(rslt);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return osnovs.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        private TextView titles, shows, showcoments;
        MyHolder(View itemView) {
            super(itemView);
            titles = itemView.findViewById(R.id.titles);
            shows = itemView.findViewById(R.id.shows);
            showcoments = itemView.findViewById(R.id.show_coments);
        }
    }
}
