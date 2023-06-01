package com.example.mobprogprojectlec.UI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobprogprojectlec.Database.AlbumHelper;
import com.example.mobprogprojectlec.Database.ArtistHelper;
import com.example.mobprogprojectlec.Database.SongHelper;
import com.example.mobprogprojectlec.Model.Album;
import com.example.mobprogprojectlec.Model.Artist;
import com.example.mobprogprojectlec.Model.Song;
import com.example.mobprogprojectlec.R;

import java.util.Vector;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder>{

    Context ctx;
    Vector<Artist> vArtist;
    Vector<Album> vAlbum;
    Vector<Song> vSong;

    private ArtistHelper artistHelper;
    private AlbumHelper albumHelper;
    private SongHelper songHelper;

    private Artist artist;
    private Album album;
    private Song song;

    public HomeAdapter(Context ctx, Vector<Artist> vArtist, Vector<Album> vAlbum, Vector<Song> vSong) {
        this.ctx = ctx;
        this.vArtist = vArtist;
        this.vAlbum = vAlbum;
        this.vSong = vSong;
    }

    @NonNull
    @Override
    public HomeAdapter.HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View viewHome = LayoutInflater.from(ctx).inflate(R.layout.home_item,parent,false);
//        return new HomeViewHolder(viewHome);
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdapter.HomeViewHolder holder, int position) {
        Artist artist = vArtist.get(position);
        Album album = vAlbum.get(position);
        Song song = vSong.get(position);

        artistHelper = new ArtistHelper(ctx);
        artistHelper.open();
        artist = artistHelper.fetchArtist(song.getArtistId());
        artistHelper.close();

        albumHelper = new AlbumHelper(ctx);
        albumHelper.open();
        album = albumHelper.fetchAlbum(song.getAlbumId());
        albumHelper.close();




    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder {

        TextView allArtist, allAlbum, allSong;
        CardView artistCV, albumCV, songCV;

        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
