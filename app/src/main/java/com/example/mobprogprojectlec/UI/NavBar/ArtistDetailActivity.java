package com.example.mobprogprojectlec.UI.NavBar;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.example.mobprogprojectlec.Database.AlbumHelper;
import com.example.mobprogprojectlec.Database.ArtistHelper;
import com.example.mobprogprojectlec.Model.Album;
import com.example.mobprogprojectlec.Model.Artist;
import com.example.mobprogprojectlec.R;
import com.example.mobprogprojectlec.UI.NavBar.AlbumAdapter;
import com.example.mobprogprojectlec.UI.NavBar.ArtistAdapter;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.Vector;

public class ArtistDetailActivity extends AppCompatActivity {

    private Vector<Album> vAlbums;
    private AlbumHelper albumHelper;
    public static final String detArtist = "detArtist";
    RecyclerView albumRecycleView;
    AlbumAdapter AlbumAdapter;
    ImageView artistImage, artistImageBack;
    TextView artistName, artistDescription;
    ImageButton backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_detail);

        artistImageBack = findViewById(R.id.artistImageBack);
        artistImage= findViewById(R.id.artistImage);
        artistName = findViewById(R.id.artistName);
        artistDescription = findViewById(R.id.artistDescription);
        backBtn = findViewById(R.id.backBtn);

        if(getIntent().getExtras() != null){
            Artist artist = getIntent().getParcelableExtra(detArtist, Artist.class);
            Glide.with(this).load(artist.getImage()).into(artistImageBack);
            Glide.with(this).load(artist.getImage()).into(artistImage);
            artistName.setText(artist.getName());
            artistDescription.setText(artist.getDescription());

            albumHelper = new AlbumHelper(this);
            albumHelper.open();
            vAlbums = albumHelper.viewAlbumbyID(artist.getId());
            albumHelper.close();

            albumRecycleView = findViewById(R.id.albumArtistRV);
            AlbumAdapter = new AlbumAdapter(this, vAlbums);
            albumRecycleView.setAdapter(AlbumAdapter);
            albumRecycleView.setLayoutManager(new GridLayoutManager(this, 2));
        }
        else{
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Artist Detail");
        }
        else {
            setTitle("Artist Detail");
        }

        backBtn.setOnClickListener(v -> {
            finish();
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }


}