package com.ellie.materialdesignex;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

public class TrackListAdapter extends RecyclerView.Adapter<TrackListViewHolder> {

    List<Track> trackList = Arrays.asList(
            new Track(1, "All We Got (feat. Kanye West & Chicago Children's Choir)", "03:23"),
            new Track(2, "No Problem (feat. Lil Wayne and 2 Chainz)", "01:21"),
            new Track(3, "Summer Friends (feat. Jeremih and Francis and the Lights)", "04:50"),
            new Track(4, "D.R.A.M. Sings Special", "01:41")
    );

    @NonNull
    @Override
    public TrackListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TrackListViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_track_constraint, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TrackListViewHolder holder, int position) {
        holder.bind(trackList.get(position));
    }

    @Override
    public int getItemCount() {
        return trackList.size();
    }
}
