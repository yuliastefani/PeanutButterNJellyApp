package com.example.mobprogprojectlec.UI.BottomNav;

import android.content.Context;
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
import com.example.mobprogprojectlec.Database.ReviewHelper;
import com.example.mobprogprojectlec.Database.SongHelper;
import com.example.mobprogprojectlec.Database.UserHelper;
import com.example.mobprogprojectlec.Model.Album;
import com.example.mobprogprojectlec.Model.Artist;
import com.example.mobprogprojectlec.Model.Review;
import com.example.mobprogprojectlec.Model.Song;
import com.example.mobprogprojectlec.Model.User;
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

    TextView allArtist, allAlbum, allSong, allReview;
    RecyclerView artistRV, albumRV, songRV, feedRV;
    Vector<Review> vReview;
    Vector<Artist> vArtist;
    Vector<Album> vAlbum;
    Vector<Song> vSong;
    FeedAdapter feedAdapter;
    ArtistAdapter artistAdapter;
    AlbumAdapter albumAdapter;
    SongAdapter songAdapter;
    ArtistHelper artistHelper;
    AlbumHelper albumHelper;
    SongHelper songHelper;
    ReviewHelper reviewHelper;
    UserHelper userHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        allReview = view.findViewById(R.id.allReview);
        allArtist = view.findViewById(R.id.allArtist);
        allAlbum = view.findViewById(R.id.allAlbum);
        allSong = view.findViewById(R.id.allSong);
        feedRV = view.findViewById(R.id.feedRV);
        artistRV = view.findViewById(R.id.artistRV);
        albumRV = view.findViewById(R.id.albumRV);
        songRV = view.findViewById(R.id.songRV);

        allReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FeedFragment()).commit();
                getActivity().setTitle("Review");
            }
        });

        allArtist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ArtistFragment()).commit();
                getActivity().setTitle("Artist");

            }
        });

        allAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AlbumFragment()).commit();
                getActivity().setTitle("Album");
            }
        });

        allSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SongFragment()).commit();
                getActivity().setTitle("Song");
            }
        });

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("username", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");

        userHelper = new UserHelper(getContext());
        userHelper.open();
        User user = userHelper.getUser(username);
        userHelper.close();

        reviewHelper = new ReviewHelper(getContext());
        reviewHelper.open();
        vReview = reviewHelper.viewReview(user.getId());
        reviewHelper.close();

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

        if (vReview.size() > 5){
            vReview.setSize(5);
        }
        vArtist.setSize(5);
        vAlbum.setSize(5);
        vSong.setSize(5);

        feedAdapter = new FeedAdapter(getContext(), vReview);
        feedRV.setAdapter(feedAdapter);
        feedRV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

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