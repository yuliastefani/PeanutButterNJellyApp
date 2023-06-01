package com.example.mobprogprojectlec.UI;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mobprogprojectlec.Database.AlbumHelper;
import com.example.mobprogprojectlec.Database.ArtistHelper;
import com.example.mobprogprojectlec.Database.ReviewHelper;
import com.example.mobprogprojectlec.Database.SongHelper;
import com.example.mobprogprojectlec.Database.UserHelper;
import com.example.mobprogprojectlec.Model.Review;
import com.example.mobprogprojectlec.Model.Song;
import com.example.mobprogprojectlec.Model.User;
import com.example.mobprogprojectlec.R;

import java.util.Vector;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    Context ctx;
    Vector<Review> vReview;
    private ReviewHelper reviewHelper;
    private SongHelper songHelper;
    private UserHelper userHelper;

    User user;
    Song song;

    public ReviewAdapter(Context ctx, Vector<Review> vReview) {
        this.ctx = ctx;
        this.vReview = vReview;
    }

    public void setvReview(Vector<Review> vReview) {
        this.vReview = vReview;
    }

    @NonNull
    @Override
    public ReviewAdapter.ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewReview = LayoutInflater.from(ctx).inflate(R.layout.review_item, parent, false);
        return new ReviewViewHolder(viewReview);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewAdapter.ReviewViewHolder holder, int position) {
        Review r = vReview.get(position);

        SharedPreferences sharedPreferences = ctx.getSharedPreferences("user", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");

        userHelper = new UserHelper(ctx);
        userHelper.open();
        user = userHelper.getUser(username);
        userHelper.close();

        songHelper = new SongHelper(ctx);
        songHelper.open();
        song = songHelper.fetchSong(r.getSongId());
        songHelper.close();

        ArtistHelper artistHelper = new ArtistHelper(ctx);
        artistHelper.open();
        String artistName = artistHelper.fetchArtist(song.getArtistId()).getName();
        artistHelper.close();

        AlbumHelper albumHelper = new AlbumHelper(ctx);
        albumHelper.open();
        String albumName = albumHelper.fetchAlbum(song.getAlbumId()).getName();
        Integer albumYear = albumHelper.fetchAlbum(song.getAlbumId()).getYear();
        String albumImage = albumHelper.fetchAlbum(song.getAlbumId()).getImage();
        albumHelper.close();

        holder.songTitle.setText(song.getTitle());
        holder.artistName.setText(artistName);
        holder.albumName.setText(albumName);
        holder.albumYear.setText(String.valueOf(albumYear));
        holder.songRating.setText(String.valueOf(r.getRating()));
        Glide.with(ctx).load(albumImage).into(holder.albumImage);
        holder.reviewCV.setOnClickListener(v->{
            Toast.makeText(ctx, "Review by " + user.getUsername(), Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return vReview.size();
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder {
        TextView songTitle, artistName, albumName, albumYear, songRating;
        ImageView albumImage;
        CardView reviewCV;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            songTitle = itemView.findViewById(R.id.songTitle);
            artistName = itemView.findViewById(R.id.artistName);
            albumName = itemView.findViewById(R.id.albumName);
            albumYear = itemView.findViewById(R.id.albumYear);
            albumImage = itemView.findViewById(R.id.albumImage);
            songRating = itemView.findViewById(R.id.songRating);
            reviewCV = itemView.findViewById(R.id.reviewCV);
        }
    }
}
