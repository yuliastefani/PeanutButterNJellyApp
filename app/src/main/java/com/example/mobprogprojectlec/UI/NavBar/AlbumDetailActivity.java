package com.example.mobprogprojectlec.UI.NavBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
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
    ImageView albumImage;
    TextView albumName, albumYear, albumDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_detail);

        albumImage= findViewById(R.id.albumImage);
        albumName = findViewById(R.id.albumName);
        albumYear = findViewById(R.id.albumYear);
        albumDescription = findViewById(R.id.albumDescription);

        if (getIntent().getExtras() != null) {
            Album album = getIntent().getParcelableExtra(detAlbum, Album.class);
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

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Album Detail");
        }
        else {
            setTitle("Album Detail");
        }

    }
}