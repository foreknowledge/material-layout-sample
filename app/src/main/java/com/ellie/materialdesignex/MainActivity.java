package com.ellie.materialdesignex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_others2);

        setTrackList();
    }

    private void setTrackList() {
        RecyclerView trackList = findViewById(R.id.trackList);
        trackList.setLayoutManager(new LinearLayoutManager(this));
        trackList.setAdapter(new TrackListAdapter());
    }
}