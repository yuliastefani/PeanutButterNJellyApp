package com.example.mobprogprojectlec.UI.BottomNav;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
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
    Dialog updateReviewDialog;

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
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
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

        int nightMode = ctx.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

        if (nightMode == Configuration.UI_MODE_NIGHT_YES) {
            holder.songTitle.setTextColor(ctx.getResources().getColor(R.color.brown_3));
            holder.artistName.setTextColor(ctx.getResources().getColor(R.color.brown_3));
            holder.albumName.setTextColor(ctx.getResources().getColor(R.color.brown_3));
            holder.albumYear.setTextColor(ctx.getResources().getColor(R.color.brown_3));
            holder.songRating.setTextColor(ctx.getResources().getColor(R.color.brown_3));
            holder.editReview.setColorFilter(ctx.getResources().getColor(R.color.brown_3));
        } else {
            holder.songTitle.setTextColor(ctx.getResources().getColor(R.color.brown_1));
            holder.artistName.setTextColor(ctx.getResources().getColor(R.color.brown_1));
            holder.albumName.setTextColor(ctx.getResources().getColor(R.color.brown_1));
            holder.albumYear.setTextColor(ctx.getResources().getColor(R.color.brown_1));
            holder.songRating.setTextColor(ctx.getResources().getColor(R.color.brown_1));
        }

        holder.deleteReview.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
            builder.setMessage("Are you sure you want to delete this review?");
            builder.setPositiveButton("Yes", (dialog, which) -> {
                reviewHelper = new ReviewHelper(ctx);
                reviewHelper.open();
                reviewHelper.deleteReview(r.getId());
                reviewHelper.close();
                vReview.remove(position);
                Toast.makeText(ctx, "Review deleted!", Toast.LENGTH_SHORT).show();
            });
            builder.setNegativeButton("No", (dialog, which) -> {
                dialog.dismiss();
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });

        holder.editReview.setOnClickListener(v->{
            updateReviewDialog = new Dialog(ctx);
            updateReviewDialog.setContentView(R.layout.edit_review);

            RatingBar edtSongRating = updateReviewDialog.findViewById(R.id.edtSongRating);
            EditText edtSongReview = updateReviewDialog.findViewById(R.id.edtSongReview);
            Button btnUpdateReview = updateReviewDialog.findViewById(R.id.btnUpdateReview);
            ImageView closeDialog = updateReviewDialog.findViewById(R.id.closeDialog);

            reviewHelper = new ReviewHelper(ctx);
            reviewHelper.open();
            String songReviewText = reviewHelper.fetchReview(r.getId()).getComment();
            Float songRatingFloat = reviewHelper.fetchReview(r.getId()).getRating();
            reviewHelper.close();

            edtSongRating.setRating(songRatingFloat);
            edtSongReview.setText(songReviewText);

            long time = System.currentTimeMillis();
            AlertDialog.Builder builder = new AlertDialog.Builder(ctx);

            btnUpdateReview.setOnClickListener(v1->{
                if(edtSongReview.getText().toString().isEmpty()){
                    builder.setMessage("Please fill in all fields");
                    builder.show();
                }else{
                    reviewHelper = new ReviewHelper(ctx);
                    reviewHelper.open();
                    reviewHelper.updateReview(r.getId(), edtSongReview.getText().toString(), edtSongRating.getRating(), time);
                    reviewHelper.close();

                    Toast.makeText(ctx, "Review updated!", Toast.LENGTH_SHORT).show();
                    updateReviewDialog.dismiss();
                }
            });

            closeDialog.setOnClickListener(v1->{
                updateReviewDialog.dismiss();
            });

            updateReviewDialog.show();

        });

    }



    @Override
    public int getItemCount() {
        return vReview.size();
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder {
        TextView songTitle, artistName, albumName, albumYear, songRating;
        ImageView albumImage, editReview, deleteReview, closeDialog;
        EditText edtSongReview;
        RatingBar edtSongRating;
        Button btnUpdateReview;
        CardView reviewCV;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            songTitle = itemView.findViewById(R.id.songTitle);
            artistName = itemView.findViewById(R.id.artistName);
            albumName = itemView.findViewById(R.id.albumName);
            albumYear = itemView.findViewById(R.id.albumYear);
            albumImage = itemView.findViewById(R.id.albumImage);
            songRating = itemView.findViewById(R.id.songRating);
            editReview = itemView.findViewById(R.id.editReview);
            deleteReview = itemView.findViewById(R.id.deleteReview);
            edtSongRating = itemView.findViewById(R.id.edtSongRating);
            edtSongReview = itemView.findViewById(R.id.edtSongReview);
            btnUpdateReview = itemView.findViewById(R.id.btnUpdateReview);
            closeDialog = itemView.findViewById(R.id.closeDialog);
            reviewCV = itemView.findViewById(R.id.reviewCV);
        }
    }
}
