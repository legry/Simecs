package com.example.simec3;

import android.content.Context;
import android.os.Handler;
import android.os.RemoteException;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.ArduinoAIDL.IArduino;

import java.util.List;

import static com.example.simec3.MainActivity.pusk_data;

class PusksAdapter extends RecyclerView.Adapter<PusksAdapter.MyHolder> {
    private Handler handler;
    private List<Pusks> dops;
    private final IArduino iArduino;
    private String[] cmnds;

    PusksAdapter(Context context, List<Pusks> dops, IArduino iArduino) {
        this.dops = dops;
        this.iArduino = iArduino;
        handler = new Handler();
        cmnds = context.getResources().getStringArray(R.array.pusk_comands);
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.puskateli, parent, false));
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, int position) {
        holder.textView.setText(dops.get(position).getTitles());
        holder.puskatel.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {

            }
            return true;
        });
        holder.puskatel.setSelected(!holder.puskatel.isSelected());
        new Thread(() -> {
            while (true) {
                try {
                    String rslt = "----";
                    synchronized (iArduino) {
                        rslt = iArduino.setPusk("stanok", cmnds[position]);
                    }
                    handler.post((new PriborsAdapter.SetShows(holder.shows, rslt)));
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
        return dops.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageButton puskatel;

        MyHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.titledop);
            puskatel = itemView.findViewById(R.id.puskatel);
        }
    }
}
