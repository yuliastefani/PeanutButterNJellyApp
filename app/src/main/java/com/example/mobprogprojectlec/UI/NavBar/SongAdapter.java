package com.example.mobprogprojectlec.UI.NavBar;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mobprogprojectlec.Database.AlbumHelper;
import com.example.mobprogprojectlec.Database.ArtistHelper;
import com.example.mobprogprojectlec.Model.Album;
import com.example.mobprogprojectlec.Model.Artist;
import com.example.mobprogprojectlec.Model.Song;
import com.example.mobprogprojectlec.R;
import com.example.mobprogprojectlec.UI.SongDetailActivity;

import java.util.Vector;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongViewHolder>{

    Context songContent;
    Vector<Song> vSong;

    private ArtistHelper artistHelper;
    private AlbumHelper albumHelper;

    Artist artist;
    Album album;

    public SongAdapter(Context songContent, Vector<Song> vSong) {
        this.songContent = songContent;
        this.vSong = vSong;
    }

    public void setvSong(Vector<Song> vSong) {
        this.vSong = vSong;
    }

    @NonNull
    @Override
    public SongAdapter.SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewSong = LayoutInflater.from(songContent).inflate(R.layout.song_item,parent,false);
        return new SongViewHolder(viewSong);
    }

    @Override
    public void onBindViewHolder(@NonNull SongAdapter.SongViewHolder holder, int position) {
        Song s = vSong.get(position);

        artistHelper = new ArtistHelper(songContent);
        artistHelper.open();
        artist = artistHelper.fetchArtist(s.getArtistId());
        artistHelper.close();

        albumHelper = new AlbumHelper(songContent);
        albumHelper.open();
        album = albumHelper.fetchAlbum(s.getAlbumId());
        albumHelper.close();

        holder.songName.setText(s.getTitle());
        holder.artistName.setText(artist.getName());
        Glide.with(songContent).load(album.getImage()).into(holder.albumImage);
        holder.songCV.setOnClickListener(v -> {
            Intent songIntent = new Intent(songContent, SongDetailActivity.class);
            songIntent.putExtra(SongDetailActivity.detSong, vSong.get(position));
            songContent.startActivity(songIntent);
        });
    }

    @Override
    public int getItemCount() {
        return vSong.size();
    }

    public class SongViewHolder extends RecyclerView.ViewHolder {

        TextView songName, artistName;
        ImageView albumImage;
        CardView songCV;

        public SongViewHolder(@NonNull View itemView) {
            super(itemView);
            songName = itemView.findViewById(R.id.songName);
            artistName = itemView.findViewById(R.id.artistName);
            albumImage = itemView.findViewById(R.id.albumImage);
            songCV = itemView.findViewById(R.id.songCV);
        }
    }
}
