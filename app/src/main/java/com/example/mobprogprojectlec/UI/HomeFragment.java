package com.example.mobprogprojectlec.UI;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
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
import com.example.mobprogprojectlec.UI.NavBar.AlbumAdapter;
import com.example.mobprogprojectlec.UI.NavBar.AlbumFragment;
import com.example.mobprogprojectlec.UI.NavBar.ArtistAdapter;
import com.example.mobprogprojectlec.UI.NavBar.ArtistFragment;
import com.example.mobprogprojectlec.UI.NavBar.SongAdapter;
import com.example.mobprogprojectlec.UI.NavBar.SongFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Vector;

public class HomeFragment extends Fragment {

    TextView allArtist, allAlbum, allSong;
    RecyclerView artistRV, albumRV, songRV;
    Vector<Artist> vArtist;
    Vector<Album> vAlbum;
    Vector<Song> vSong;
    ArtistAdapter artistAdapter;
    AlbumAdapter albumAdapter;
    SongAdapter songAdapter;
    ArtistHelper artistHelper;
    AlbumHelper albumHelper;
    SongHelper songHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        allArtist = view.findViewById(R.id.allArtist);
        allAlbum = view.findViewById(R.id.allAlbum);
        allSong = view.findViewById(R.id.allSong);
        artistRV = view.findViewById(R.id.artistRV);
        albumRV = view.findViewById(R.id.albumRV);
        songRV = view.findViewById(R.id.songRV);

        allArtist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ArtistFragment()).commit();
            }
        });

        allAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AlbumFragment()).commit();
            }
        });

        allSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SongFragment()).commit();
            }
        });

        artistHelper = new ArtistHelper(getContext());
        artistHelper.open();
        vArtist = artistHelper.viewArtist();
        artistHelper.close();

        albumHelper = new AlbumHelper(getContext());
        albumHelper.open();
        vAlbum = albumHelper.viewAlbum("name","ASC");
        albumHelper.close();

        songHelper = new SongHelper(getContext());
        songHelper.open();
        vSong = songHelper.viewSong();
        songHelper.close();

        vArtist.setSize(5);
        vAlbum.setSize(5);
        vSong.setSize(5);

        artistAdapter = new ArtistAdapter(getContext(), vArtist);
        artistRV.setAdapter(artistAdapter);
        artistRV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        albumAdapter = new AlbumAdapter(getContext(), vAlbum);
        albumRV.setAdapter(albumAdapter);
        albumRV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        songAdapter = new SongAdapter(getContext(), vSong);
        songRV.setAdapter(songAdapter);
        songRV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

    }

}