package com.example.mobprogprojectlec.UI;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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

        user.getId();

        songHelper = new SongHelper(ctx);
        songHelper.open();
        song = songHelper.getSong(String.valueOf(r.getSongId()));
        songHelper.close();

        reviewHelper = new ReviewHelper(ctx);
        reviewHelper.open();
        vReview = reviewHelper.vReview(String.valueOf(user.getId()));
        reviewHelper.close();
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder {
        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
