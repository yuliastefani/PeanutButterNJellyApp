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
import com.example.mobprogprojectlec.Model.Album;
import com.example.mobprogprojectlec.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Vector;

public class AlbumFragment extends Fragment {

    private Vector<Album> vAlbums;
    private RecyclerView albumRecycleView;
    private AlbumHelper albumHelper;

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
        String albumurl = "";

        JsonObjectRequest albumRequest = new JsonObjectRequest(Request.Method.GET, albumurl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Vector<Album> vAlbums = jsonParse(response);
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
            JSONArray jsonArray = response.getJSONArray("albums");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject album = jsonArray.getJSONObject(i);
                String name = album.getString("name");
                Integer artist = album.getInt("artist");
                Integer year = album.getInt("year");
                String description = album.getString("description");
                String image = album.getString("image");
                albumHelper.open();
                if (!albumHelper.validateAlbum(name)) {
                    albumHelper.insertAlbum(name, artist, year, description, image);
                }

                albumHelper.close();

            }

        }catch (JSONException e) {
            throw new RuntimeException(e);
        }

        return vAlbums;

        }

    private void initialize() {
        vAlbums = new Vector<>();
    }
}