package com.example.mobprogprojectlec.UI.NavBar;

import android.content.Context;
import android.content.SharedPreferences;
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
import com.example.mobprogprojectlec.Database.SongHelper;
import com.example.mobprogprojectlec.Model.Album;
import com.example.mobprogprojectlec.Model.Artist;
import com.example.mobprogprojectlec.Model.Song;
import com.example.mobprogprojectlec.R;

import java.util.Vector;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongViewHolder>{

    Context songContent;
    Vector<Song> vSong;

    private ArtistHelper artistHelper;
    private AlbumHelper albumHelper;
    private SongHelper songHelper;

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

        SharedPreferences localArtist = songContent.getSharedPreferences("name", Context.MODE_PRIVATE);
        String dataArtist = localArtist.getString("name","");
        artistHelper = new ArtistHelper(songContent);
        artistHelper.open();
        artist = artistHelper.getArtist(dataArtist);
        artistHelper.close();

        artist.getId();

        SharedPreferences localAlbum = songContent.getSharedPreferences("image", Context.MODE_PRIVATE);
        String dataAlbum = localAlbum.getString("image","");
        albumHelper = new AlbumHelper(songContent);
        albumHelper.open();
        album = albumHelper.getAlbum(dataAlbum, artist.getId());
        albumHelper.close();

        album.getId();

        songHelper = new SongHelper(songContent);
        songHelper.open();
        vSong = songHelper.vSong(String.valueOf(artist.getId()), String.valueOf(album.getId()));
        songHelper.close();

        holder.songName.setText(s.getTitle());
        holder.artistName.setText(artist.getName());
        Glide.with(songContent).load(album.getImage()).into(holder.albumImage);
        holder.songCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(songContent, SongDetail.class);
                //intent.putExtra("songName",s.getName());
                //intent.putExtra("songArtist",s.getArtist());
                //intent.putExtra("songYear",s.getYear());
                //intent.putExtra("songGenre",s.getGenre());
                //intent.putExtra("songDesc",s.getDescription());
                //songContent.startActivity(intent);
            }
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
