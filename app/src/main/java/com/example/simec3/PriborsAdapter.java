package com.example.simec3;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import static com.example.simec3.MainActivity.pusk_data;

class PriborsAdapter extends RecyclerView.Adapter<PriborsAdapter.MyHolder> {
    private List<Pribors> osnovs;
    PriborsAdapter(List<Pribors> osnovs) {
        this.osnovs = osnovs;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.pribors, parent, false));
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, int position) {
        holder.titles.setText(osnovs.get(position).getTitles());
        holder.showcoments.setText(osnovs.get(position).getShowcoments());
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
