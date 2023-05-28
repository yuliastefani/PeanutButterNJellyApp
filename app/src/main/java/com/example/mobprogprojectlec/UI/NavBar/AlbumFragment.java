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
    private AlbumAdapter albumAdapter;

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
        albumHelper = new AlbumHelper(getContext());
        albumHelper.open();
        vAlbums = albumHelper.viewAlbum();
        albumHelper.close();

        albumRecycleView = view.findViewById(R.id.albumRV);
        albumAdapter = new AlbumAdapter(getContext(), vAlbums);
        albumRecycleView.setAdapter(albumAdapter);
        albumRecycleView.setLayoutManager(new GridLayoutManager(getContext(), 2));


    }

}