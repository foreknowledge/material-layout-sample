package com.ellie.materialdesignex;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TrackListViewHolder extends RecyclerView.ViewHolder {

    TextView textTrackNumber;
    TextView textTitle;
    TextView textArtist;
    TextView textDuration;

    int purple = Color.parseColor("#551DB8");

    public TrackListViewHolder(@NonNull View itemView) {
        super(itemView);

        textTrackNumber = itemView.findViewById(R.id.trackNumber);
        textTitle = itemView.findViewById(R.id.songTitle);
        textArtist = itemView.findViewById(R.id.artistName);
        textDuration = itemView.findViewById(R.id.duration);
    }

    public void bind(Track track) {
        textTrackNumber.setText(String.valueOf(track.trackNumber));
        textTitle.setText(track.title);
        textDuration.setText(track.duration);

        if (track.trackNumber == 2) {
            textTrackNumber.setTextColor(purple);
            textTitle.setTextColor(purple);
            textArtist.setTextColor(purple);
            textDuration.setTextColor(purple);
        }
    }
}
