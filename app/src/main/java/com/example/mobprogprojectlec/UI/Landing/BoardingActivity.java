package com.example.mobprogprojectlec.UI.Landing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mobprogprojectlec.Database.AlbumHelper;
import com.example.mobprogprojectlec.Database.ArtistHelper;
import com.example.mobprogprojectlec.Database.SongHelper;
import com.example.mobprogprojectlec.Model.Album;
import com.example.mobprogprojectlec.Model.Artist;
import com.example.mobprogprojectlec.Model.Song;
import com.example.mobprogprojectlec.R;
import com.example.mobprogprojectlec.UI.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Vector;

public class BoardingActivity extends AppCompatActivity {

    private SongHelper songHelper;
    private ArtistHelper artistHelper;
    private AlbumHelper albumHelper;
    private Vector<Song> vSong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boarding);
        Button btnGetStarted = findViewById(R.id.btnGetStarted);

        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start main app activity
                Intent intent = new Intent(BoardingActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        String songUrl = "https://mocki.io/v1/f5140d70-7614-45d6-83ee-694240fa12f0";

        songHelper = new SongHelper(this);
        artistHelper = new ArtistHelper(this);
        albumHelper = new AlbumHelper(this);

        JsonObjectRequest songRequest = new JsonObjectRequest(Request.Method.GET, songUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        jsonParse(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(BoardingActivity.this, "Error: " + error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        Volley.newRequestQueue(this).add(songRequest);
    }

    private Vector<Song> jsonParse(JSONObject response) {
        initialize();

        try {
            JSONArray artistArray = response.getJSONArray("artist");

            artistHelper.open();
            albumHelper.open();
            songHelper.open();

            for (int i = 0; i < artistArray.length(); i++) {
                JSONObject artistObject = artistArray.getJSONObject(i);
                int artistId = insertArtist(artistObject);
                JSONArray albumArray = artistObject.getJSONArray("album");

                for (int j = 0; j < albumArray.length(); j++) {
                    JSONObject albumObject = albumArray.getJSONObject(j);
                    int albumId = insertAlbum(albumObject, artistId);
                    JSONArray songArray = albumObject.getJSONArray("song");

                    for (int k = 0; k < songArray.length(); k++) {
                        JSONObject songObject = songArray.getJSONObject(k);
                        String title = songObject.getString("title");
                        String genre = songObject.getString("genre");
                        String preview = songObject.getString("preview");

                        if (!songHelper.validateSong(title, artistId, albumId)) {
                            songHelper.insertSong(title, artistId, albumId, genre, preview);
                        }
                    }
                }
            }

            artistHelper.close();
            albumHelper.close();
            songHelper.close();

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        return vSong;

    }


    private int insertAlbum(JSONObject albumObject, int artistId) {
        try {
            String name = albumObject.getString("name");
            Integer year = albumObject.getInt("year");
            String description = albumObject.getString("description");
            String image = albumObject.getString("image");

            albumHelper.open();
            if (!albumHelper.validateAlbum(name, artistId)) {
                int albumID = albumHelper.insertAlbum(name, artistId, year, description, image);
                albumHelper.close();
                return albumID;
            } else {
                Album album = albumHelper.getAlbum(name, artistId);
                if (album != null) {
                    albumHelper.close();
                    return album.getId();
                } else {
                    // Handle the case where the album is null
                    albumHelper.close();
                    return -1; // or any other appropriate value
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return -1;
        }
    }

    private int insertArtist(JSONObject artistObject) {
        try {
            String name = artistObject.getString("name");
            String description = artistObject.getString("description");
            String image = artistObject.getString("image");

            artistHelper.open();
            if (!artistHelper.validateArtist(name)) {
                int artistID = artistHelper.insertArtist(name, description, image);
                artistHelper.close();
                return artistID;
            } else {
                Artist artist = artistHelper.getArtist(name);
                artistHelper.close();
                return artist.getId();
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private void initialize() {
        vSong = new Vector<>();
    }

}