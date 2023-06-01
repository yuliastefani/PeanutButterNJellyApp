package com.example.mobprogprojectlec.UI.NavBar;

import android.content.Context;
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
    private Vector<Song> vSong;
    private RecyclerView songRecyclerView;

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
        initialize();

        songHelper = new SongHelper(getContext());
        songHelper.open();
        vSong = songHelper.viewSong();
        songHelper.close();

        songRecyclerView = view.findViewById(R.id.songRV);
        SongAdapter songAdapter = new SongAdapter(getContext(), vSong);
        songRecyclerView.setAdapter(songAdapter);
        songRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    }

    private void initialize() {
        vSong = new Vector<>();
    }
}
