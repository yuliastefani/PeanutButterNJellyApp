package com.example.mobprogprojectlec.UI.NavBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mobprogprojectlec.Database.SongHelper;
import com.example.mobprogprojectlec.Model.Album;
import com.example.mobprogprojectlec.Model.Song;
import com.example.mobprogprojectlec.R;
import com.example.mobprogprojectlec.UI.NavBar.SongAdapter;

import java.util.Vector;

public class AlbumDetailActivity extends AppCompatActivity {

    private Vector<Song> vSongs;
    private SongHelper songHelper;
    public static final String detAlbum = "detAlbum";
    RecyclerView songRecycleView;
    SongAdapter SongAdapter;
    ImageView albumImage, albumImageBack, includedLayout;
    TextView albumName, albumYear, albumDescription;
    ImageButton backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_detail);

        albumImageBack = findViewById(R.id.albumImageBack);
        albumImage= findViewById(R.id.albumImage);
        albumName = findViewById(R.id.albumName);
        albumYear = findViewById(R.id.albumYear);
        albumDescription = findViewById(R.id.albumDescription);
        backBtn = findViewById(R.id.backBtn);

        if (getIntent().getExtras() != null) {
            Album album = getIntent().getParcelableExtra(detAlbum, Album.class);
            Glide.with(this).load(album.getImage()).into(albumImageBack);
            Glide.with(this).load(album.getImage()).into(albumImage);
            albumName.setText(album.getName());
            albumYear.setText(String.valueOf(album.getYear()));
            albumDescription.setText(album.getDescription());


            songHelper = new SongHelper(this);
            songHelper.open();
            vSongs = songHelper.viewSongById(album.getId());
            songHelper.close();

            songRecycleView = findViewById(R.id.songAlbumRV);
            SongAdapter = new SongAdapter(this, vSongs);
            songRecycleView.setAdapter(SongAdapter);
            songRecycleView.setLayoutManager(new LinearLayoutManager(this));
        }
        else {
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        }

        backBtn.setOnClickListener(v -> finish());

        includedLayout = findViewById(R.id.includedLayout);
        int nightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

        if (nightMode == Configuration.UI_MODE_NIGHT_YES) {
            includedLayout.setImageResource(R.drawable.rounded_background_dark);
        } else {
            includedLayout.setImageResource(R.drawable.rounded_background);
        }

//        if (getSupportActionBar() != null) {
//            getSupportActionBar().setTitle("Album Detail");
//        }
//        else {
//            setTitle("Album Detail");
//        }



    }
}