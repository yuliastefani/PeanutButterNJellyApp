package com.example.mobprogprojectlec.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.mobprogprojectlec.UI.Landing.LoginActivity;
import com.example.mobprogprojectlec.UI.NavBar.AboutFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Switch;
import android.widget.Toast;

import com.example.mobprogprojectlec.R;
import com.example.mobprogprojectlec.UI.NavBar.AlbumFragment;
import com.example.mobprogprojectlec.UI.NavBar.ArtistFragment;
import com.example.mobprogprojectlec.UI.NavBar.SongFragment;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Vector;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private BottomNavigationView bottom_nav;
//    View switchLayout = getLayoutInflater().inflate(R.layout.switch_item, null);
    Switch switch_dark_mode;
    private boolean isNavigationItemSelected = false;
    AlertDialog.Builder builder;

    private SongHelper songHelper;
    private ArtistHelper artistHelper;
    private AlbumHelper albumHelper;
    private Vector<Song> vSong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
            getSupportActionBar().setTitle("Home");
        }

//        switch_dark_mode = switchLayout.findViewById(R.id.switch_dark_mode);

//        switch_dark_mode.setOnCheckedChangeListener((buttonView, isChecked) -> {
//            if (isChecked) {
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//                recreate();
//                Toast.makeText(MainActivity.this, "Dark Mode On", Toast.LENGTH_SHORT).show();
//            } else {
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//                recreate();
//                Toast.makeText(MainActivity.this, "Dark Mode Off", Toast.LENGTH_SHORT).show();
//            }
//        });

        bottom_nav = findViewById(R.id.bottomNav);
        bottom_nav.setOnItemSelectedListener(item -> {
//            if (isNavigationItemSelected) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                        getSupportActionBar().setTitle("Home");
                        break;

                    case R.id.bottom_review:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ReviewFragment()).commit();
                        getSupportActionBar().setTitle("My Review");
                        break;

                    case R.id.bottom_profile:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AccountFragment()).commit();
                        getSupportActionBar().setTitle("My Profile");
                        break;
                }
//            }
//            isNavigationItemSelected = false;
            return true;
        });

        String songUrl = "https://mocki.io/v1/f35d5c90-a8e9-4f0b-9154-5f4598226daa";

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
                Toast.makeText(MainActivity.this, "Error: " + error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        Volley.newRequestQueue(this).add(songRequest);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        isNavigationItemSelected = true;
        switch (item.getItemId()) {
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                getSupportActionBar().setTitle("Home");
                break;

            case R.id.nav_artist:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ArtistFragment()).commit();
                getSupportActionBar().setTitle("Artist");
                break;

            case R.id.nav_album:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AlbumFragment()).commit();
                getSupportActionBar().setTitle("Album");
                break;

            case R.id.nav_song:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SongFragment()).commit();
                getSupportActionBar().setTitle("Song");
                break;

            case R.id.nav_about:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AboutFragment()).commit();
                getSupportActionBar().setTitle("About");
                break;

            case R.id.nav_logout:
                builder = new AlertDialog.Builder(this);
                builder.setTitle("Logout");
                builder.setMessage("Are you sure you want to logout?");
                builder.setCancelable(true);
                builder.setPositiveButton("Yes", (dialog, which) -> {
                    SharedPreferences local = getSharedPreferences("username", Context.MODE_PRIVATE);
                    SharedPreferences.Editor spEdit = local.edit();
                    spEdit.putString("username", "null");
                    spEdit.commit();
                    Toast.makeText(MainActivity.this, "Logout Successful", Toast.LENGTH_SHORT).show();
                    finish();
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                });
                builder.setNegativeButton("No", (dialog, which) -> {
                    Toast.makeText(MainActivity.this, "Logout Cancelled", Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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