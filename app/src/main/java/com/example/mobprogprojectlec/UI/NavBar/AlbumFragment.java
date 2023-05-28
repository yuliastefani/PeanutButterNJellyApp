package com.example.mobprogprojectlec.UI.NavBar;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
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
import com.example.mobprogprojectlec.Model.Album;
import com.example.mobprogprojectlec.Model.Artist;
import com.example.mobprogprojectlec.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Vector;

public class AlbumFragment extends Fragment {

    private Vector<Album> vAlbums;
    private RecyclerView albumRecycleView;
    private AlbumHelper albumHelper;
    private ArtistHelper artistHelper;

    public AlbumFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_album, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String albumurl = "https://mocki.io/v1/a2a8b40c-80f1-4704-941d-9285c7f31b89";

        JsonObjectRequest albumRequest = new JsonObjectRequest(Request.Method.GET, albumurl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        vAlbums = jsonParse(response);
                        albumRecycleView = view.findViewById(R.id.albumRV);
                        albumHelper = new AlbumHelper(getContext());
                        albumHelper.open();
                        vAlbums = albumHelper.vAlbum();
                        albumHelper.close();

                        AlbumAdapter albumAdapter = new AlbumAdapter(getContext(),vAlbums);
                        albumRecycleView.setAdapter(albumAdapter);
                        albumRecycleView.setLayoutManager(new GridLayoutManager(getContext(),2));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity().getApplicationContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        Volley.newRequestQueue(getActivity().getApplicationContext()).add(albumRequest);
    }

    private Vector<Album> jsonParse(JSONObject response) {
        initialize();
        try {
            JSONArray artistArray = response.getJSONArray("artist");

            artistHelper = new ArtistHelper(getContext());
            artistHelper.open();

            albumHelper = new AlbumHelper(getContext());
            albumHelper.open();

            for (int i = 0; i < artistArray.length(); i++) {
                JSONObject artistObject = artistArray.getJSONObject(i);
                int artistID = insertArtist(artistObject);

                JSONArray albumArray = artistObject.getJSONArray("album");
                for (int j = 0; j < albumArray.length(); j++) {
                    JSONObject albumObject = albumArray.getJSONObject(j);
                    String name = albumObject.getString("name");
                    int year = albumObject.getInt("year");
                    String description = albumObject.getString("description");
                    String image = albumObject.getString("image");

                    if (!albumHelper.validateAlbum(name)) {
                        albumHelper.insertAlbum(name, artistID, year, description, image);
                    }
                }
            }

            albumHelper.close();
            artistHelper.close();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        albumHelper.open();
        vAlbums = albumHelper.vAlbum();
        albumHelper.close();

        return vAlbums;
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
        Vector<Album> vAlbums = new Vector<>();
    }
}