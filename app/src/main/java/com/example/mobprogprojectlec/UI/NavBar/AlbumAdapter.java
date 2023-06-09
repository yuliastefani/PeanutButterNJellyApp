package com.example.mobprogprojectlec.UI.NavBar;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mobprogprojectlec.Model.Album;
import com.example.mobprogprojectlec.R;

import java.util.Vector;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder>{

    Context albumContent;
    Vector<Album> vAlbum;

    public AlbumAdapter(Context albumContent, Vector<Album> vAlbum) {
        this.albumContent = albumContent;
        this.vAlbum = vAlbum;
    }

    @NonNull
    @Override
    public AlbumAdapter.AlbumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewAlbum = LayoutInflater.from(albumContent).inflate(R.layout.album_item,parent,false);
        return new AlbumViewHolder(viewAlbum);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumAdapter.AlbumViewHolder holder, int position) {
        Album a = vAlbum.get(position);
        holder.albumName.setText(a.getName());
        Glide.with(albumContent).load(a.getImage()).into(holder.albumImage);

        int nightMode = albumContent.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

        if (nightMode == Configuration.UI_MODE_NIGHT_YES) {
            holder.albumName.setTextColor(albumContent.getResources().getColor(R.color.brown_3));
        } else {
            holder.albumName.setTextColor(albumContent.getResources().getColor(R.color.brown_2));
        }

        holder.albumCV.setOnClickListener(v -> {
            Intent detailAlbumIntent = new Intent(albumContent, AlbumDetailActivity.class);
            detailAlbumIntent.putExtra(AlbumDetailActivity.detAlbum, vAlbum.get(position));
            albumContent.startActivity(detailAlbumIntent);
        });
    }

    @Override
    public int getItemCount() {
        return vAlbum.size();
    }

    public class AlbumViewHolder extends RecyclerView.ViewHolder {

        TextView albumName;
        ImageView albumImage;
        CardView albumCV;

        public AlbumViewHolder(@NonNull View itemView) {
            super(itemView);
            albumName = itemView.findViewById(R.id.albumName);
            albumImage = itemView.findViewById(R.id.albumImage);
            albumCV = itemView.findViewById(R.id.albumCV);
        }
    }

}
