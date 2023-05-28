package com.example.mobprogprojectlec.UI.NavBar;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Vector;

public class SongFragment extends Fragment {

    private SongHelper songHelper;
    private ArtistHelper artistHelper;
    private AlbumHelper albumHelper;

    private Song song;
    private Vector<Song> vSong;
    private RecyclerView songRecyclerView;

    private Artist artist;
    private Album album;

    public SongFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_song, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String songUrl = "https://mocki.io/v1/a2a8b40c-80f1-4704-941d-9285c7f31b89";

        songRecyclerView = view.findViewById(R.id.songRV);
        songHelper = new SongHelper(getContext());
        artistHelper = new ArtistHelper(getContext());
        albumHelper = new AlbumHelper(getContext());

        // Retrieve artistName and albumName from SharedPreferences
        SharedPreferences localArtist = getContext().getSharedPreferences("name", 0);
        String artistName = localArtist.getString("name", "");

        SharedPreferences localAlbum = getContext().getSharedPreferences("image", 0);
        String albumImage = localAlbum.getString("image", "");

        artistHelper.open();
        albumHelper.open();
        songHelper.open();

        JsonObjectRequest songRequest = new JsonObjectRequest(Request.Method.GET, songUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        vSong = jsonParse(response, artistName, albumImage);
//
//                        artist = artistHelper.getArtist(artistName);
//                        if (artist != null) {
//                            album = albumHelper.getAlbum(albumImage, artist.getId());
//                        }
//
//                        artistHelper.close();
//                        albumHelper.close();
                        songHelper.close();
//
//                        if (artist != null && album != null) {
                            songRecyclerView.setAdapter(new SongAdapter(getContext(), vSong));
                            songRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//                        } else {
//                            Toast.makeText(getContext(), "Artist or Album not found", Toast.LENGTH_SHORT).show();
//                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Error: " + error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        Volley.newRequestQueue(getContext()).add(songRequest);
    }



        private Vector<Song> jsonParse(JSONObject response, String artistName, String albumName) {
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

            // Close the songHelper and reopen it after getting the artist and album
            artist = artistHelper.getArtist(artistName);
            if (artist != null) {
                album = albumHelper.getAlbum(albumName, artist.getId());
            }

            artistHelper.close();
            albumHelper.close();

            songHelper.open();
            if (artist != null && album != null) {
                vSong = songHelper.vSong(String.valueOf(artist.getId()), String.valueOf(album.getId()));
            }
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
