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
    private ArtistAdapter artistAdapter;

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
        artistHelper = new ArtistHelper(getContext());
        artistHelper.open();
        vArtists = artistHelper.viewArtist();
        artistHelper.close();

        artistRecycleView = view.findViewById(R.id.artistRV);
        artistAdapter = new ArtistAdapter(getContext(), vArtists);
        artistRecycleView.setAdapter(artistAdapter);
        artistRecycleView.setLayoutManager(new GridLayoutManager(getContext(), 2));

    }


}