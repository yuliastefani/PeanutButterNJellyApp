package com.example.mobprogprojectlec.UI.NavBar;

import android.os.Bundle;

import androidx.annotation.NonNull;
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
import com.example.mobprogprojectlec.Database.ArtistHelper;
import com.example.mobprogprojectlec.Model.Artist;
import com.example.mobprogprojectlec.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Vector;

public class ArtistFragment extends Fragment {

    private Vector<Artist> vArtists;
    private RecyclerView artistRecycleView;
    private ArtistHelper artistHelper;

    public ArtistFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_artist, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String url = "https://mocki.io/v1/a2a8b40c-80f1-4704-941d-9285c7f31b89";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Vector<Artist> vArtists = jsonParse(response);
                        artistHelper = new ArtistHelper(getContext());
                        artistHelper.open();
                        vArtists = artistHelper.vArtist();
                        artistHelper.close();

                        artistRecycleView = view.findViewById(R.id.artistRV);
                        ArtistAdapter artistAdapter = new ArtistAdapter(getContext(), vArtists);
                        artistRecycleView.setAdapter(artistAdapter);
                        artistAdapter.notifyDataSetChanged();
                        artistRecycleView.setLayoutManager(new GridLayoutManager(getContext(),2));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity().getApplicationContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });

        Volley.newRequestQueue(getActivity().getApplicationContext()).add(request);

    }

    private void initialize() {
        vArtists = new Vector<>();
    }

    private Vector<Artist> jsonParse(JSONObject response) {
        initialize();
        try {
            JSONArray jsonArray = response.getJSONArray("artist");
            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject artist = jsonArray.getJSONObject(i);
                String name = artist.getString("name");
                String description = artist.getString("description");
                String image = artist.getString("image");
                artistHelper = new ArtistHelper(getContext());
                artistHelper.open();
                if (!artistHelper.validateArtist(name)){
                    artistHelper.insertArtist(name, description, image);
                }
                artistHelper.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vArtists;
    }


}