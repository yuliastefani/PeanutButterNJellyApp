package com.example.mobprogprojectlec.UI;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.mobprogprojectlec.Database.ArtistHelper;
import com.example.mobprogprojectlec.Model.Artist;
import com.example.mobprogprojectlec.R;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.Vector;

public class ArtistDetailActivity extends AppCompatActivity {

    private Vector<Artist> vArtists;
    private RecyclerView artistRecycleView;
    private ArtistHelper artistHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_detail);



    }


}