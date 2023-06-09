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
import com.example.mobprogprojectlec.Model.Artist;
import com.example.mobprogprojectlec.R;

import java.util.Vector;

public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ArtistViewHolder>{

    Context artistContent;
    Vector<Artist> vArtist;

    public ArtistAdapter(Context artistContent, Vector<Artist> vArtist) {
        this.artistContent = artistContent;
        this.vArtist = vArtist;
    }

    public void setvArtist(Vector<Artist> vArtist) {
        this.vArtist = vArtist;
    }


    @NonNull
    @Override
    public ArtistAdapter.ArtistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewArtist = LayoutInflater.from(artistContent).inflate(R.layout.artist_item,parent,false);
        return new ArtistViewHolder(viewArtist);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistAdapter.ArtistViewHolder holder, int position) {
        Artist a = vArtist.get(position);
        holder.artistName.setText(a.getName());
        Glide.with(artistContent).load(a.getImage()).into(holder.artistImage);

        int nightMode = artistContent.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

        if (nightMode == Configuration.UI_MODE_NIGHT_YES) {
            holder.artistName.setTextColor(artistContent.getResources().getColor(R.color.brown_3));
        } else {
            holder.artistName.setTextColor(artistContent.getResources().getColor(R.color.brown_2));
        }

        holder.artistCV.setOnClickListener(v -> {
            Intent detailArtistIntent = new Intent(artistContent, ArtistDetailActivity.class);
            detailArtistIntent.putExtra(ArtistDetailActivity.detArtist, vArtist.get(position));
            artistContent.startActivity(detailArtistIntent);
        });
    }

    @Override
    public int getItemCount() {
        return vArtist.size();
    }

    public class ArtistViewHolder extends RecyclerView.ViewHolder {

        TextView artistName;
        ImageView artistImage;
        CardView artistCV;
        public ArtistViewHolder(View viewArtist) {
            super(viewArtist);
            artistName = viewArtist.findViewById(R.id.artistName);
            artistImage = viewArtist.findViewById(R.id.artistImage);
            artistCV = viewArtist.findViewById(R.id.artistCV);
        }
    }
}
