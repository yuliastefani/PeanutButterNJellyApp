package com.example.mobprogprojectlec.UI.BottomNav;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mobprogprojectlec.Database.AlbumHelper;
import com.example.mobprogprojectlec.Database.ArtistHelper;
import com.example.mobprogprojectlec.Database.SongHelper;
import com.example.mobprogprojectlec.Database.UserHelper;
import com.example.mobprogprojectlec.Model.Review;
import com.example.mobprogprojectlec.Model.Song;
import com.example.mobprogprojectlec.R;
import com.example.mobprogprojectlec.UI.NavBar.SongDetailActivity;

import java.util.Vector;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder>{

    Context ctx;
    Vector<Review> vReview;
    SongHelper songHelper;
    UserHelper userHelper;
    Song song, song2;

    public FeedAdapter(Context ctx, Vector<Review> vReview) {
        this.ctx = ctx;
        this.vReview = vReview;
    }

    @NonNull
    @Override
    public FeedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewFeed = LayoutInflater.from(ctx).inflate(R.layout.feed_item, parent, false);
        return new ViewHolder(viewFeed);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedAdapter.ViewHolder holder, int position) {

        if (position < 0 || position >= vReview.size()) {
            Log.d("FeedAdapter", "Invalid position: " + position);
            return;
        }

        Review review = vReview.get(position);

        if (review == null) {
            Log.d("FeedAdapter", "Review object is null at position: " + position);
            return;
        }

        userHelper = new UserHelper(ctx);
        userHelper.open();
        String username = userHelper.fetchUser(review.getUserId()).getUsername();
        userHelper.close();

        songHelper = new SongHelper(ctx);
        songHelper.open();
        song = songHelper.fetchSong(review.getSongId());
        String songTitle = songHelper.fetchSong(review.getSongId()).getTitle();
        songHelper.close();

        ArtistHelper artistHelper = new ArtistHelper(ctx);
        artistHelper.open();
        String songArtist = artistHelper.fetchArtist(song.getArtistId()).getName();
        artistHelper.close();

        AlbumHelper albumHelper = new AlbumHelper(ctx);
        albumHelper.open();
        String albumName = albumHelper.fetchAlbum(song.getAlbumId()).getName();
        String albumImage = albumHelper.fetchAlbum(song.getAlbumId()).getImage();
        albumHelper.close();

        long systemTime = System.currentTimeMillis();
        long timeDiff = systemTime - review.getDate();

        holder.usernameID.setText(username);
        if (timeDiff < (60 * 1000)) { // Less than a minute
            holder.timeAdded.setText("Just now");
        } else if (timeDiff < (60 * 60 * 1000)) { // Less than an hour
            Long timeDiffInMinutes = timeDiff / (60 * 1000);
            holder.timeAdded.setText(timeDiffInMinutes + "m ago");
        } else if (timeDiff < (24 * 60 * 60 * 1000)) { // Less than a day
            Long timeDiffInHours = timeDiff / (60 * 60 * 1000);
            holder.timeAdded.setText(timeDiffInHours + "h ago");
        } else if (timeDiff < (7 * 24 * 60 * 60 * 1000)) { // Less than a week
            Long timeDiffInDays = timeDiff / (24 * 60 * 60 * 1000);
            holder.timeAdded.setText(timeDiffInDays + "d ago");
        } else if (timeDiff < (30 * 24 * 60 * 60 * 1000)) { // Less than a month
            Long timeDiffInWeeks = timeDiff / (7 * 24 * 60 * 60 * 1000);
            holder.timeAdded.setText(timeDiffInWeeks + "w ago");
        } else if (timeDiff < (365L * 24 * 60 * 60 * 1000)) { // Less than a year
            Long timeDiffInMonths = timeDiff / (30 * 24 * 60 * 60 * 1000);
            holder.timeAdded.setText(timeDiffInMonths + "mo ago");
        } else { // More than a year
            Long timeDiffInYears = timeDiff / (365L * 24 * 60 * 60 * 1000);
            holder.timeAdded.setText(timeDiffInYears + "y ago");
        }
        holder.songReview.setText('"' + review.getComment() + '"');
        holder.songRating.setText(String.valueOf(review.getRating()));
        holder.songTitle.setText(songTitle);
        holder.songArtist.setText(songArtist);
        holder.albumName.setText(albumName);
        Glide.with(ctx).load(albumImage).into(holder.albumImage);

        int nightMode = ctx.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

        if (nightMode == Configuration.UI_MODE_NIGHT_YES) {
            holder.usernameID.setTextColor(ctx.getResources().getColor(R.color.brown_3));
            holder.timeAdded.setTextColor(ctx.getResources().getColor(R.color.brown_3));
            holder.songReview.setTextColor(ctx.getResources().getColor(R.color.brown_3));
            holder.songRating.setTextColor(ctx.getResources().getColor(R.color.brown_3));
            holder.songTitle.setTextColor(ctx.getResources().getColor(R.color.brown_3));
            holder.songArtist.setTextColor(ctx.getResources().getColor(R.color.brown_3));
            holder.albumName.setTextColor(ctx.getResources().getColor(R.color.brown_3));
        } else {
            holder.usernameID.setTextColor(ctx.getResources().getColor(R.color.brown_1));
            holder.timeAdded.setTextColor(ctx.getResources().getColor(R.color.brown_1));
            holder.songReview.setTextColor(ctx.getResources().getColor(R.color.brown_1));
            holder.songRating.setTextColor(ctx.getResources().getColor(R.color.brown_1));
            holder.songTitle.setTextColor(ctx.getResources().getColor(R.color.brown_1));
            holder.songArtist.setTextColor(ctx.getResources().getColor(R.color.brown_1));
            holder.albumName.setTextColor(ctx.getResources().getColor(R.color.brown_1));
        }

        holder.rlDet.setOnClickListener(v -> {
            songHelper = new SongHelper(ctx);
            songHelper.open();
            song2 = songHelper.fetchSong(review.getSongId());
            songHelper.close();

            Intent intent = new Intent(ctx, SongDetailActivity.class);
            intent.putExtra(SongDetailActivity.detSong, song2);
            ctx.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return vReview.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView usernameID, timeAdded, songReview, songRating, songTitle, songArtist, albumName;
        ImageView albumImage;
        RelativeLayout rlDet;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            usernameID = itemView.findViewById(R.id.usernameID);
            timeAdded = itemView.findViewById(R.id.timeAdded);
            songReview = itemView.findViewById(R.id.songReview);
            songRating = itemView.findViewById(R.id.songRating);
            songTitle = itemView.findViewById(R.id.songTitle);
            songArtist = itemView.findViewById(R.id.songArtist);
            albumName = itemView.findViewById(R.id.albumName);
            albumImage = itemView.findViewById(R.id.albumImage);
            rlDet = itemView.findViewById(R.id.rlDet);
        }
    }
}
