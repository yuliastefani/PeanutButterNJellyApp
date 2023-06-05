package com.example.mobprogprojectlec.UI.BottomNav;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.example.mobprogprojectlec.Model.User;
import com.example.mobprogprojectlec.R;

import java.util.Vector;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder>{

    Context ctx;
    Vector<Review> vReview;
    SongHelper songHelper;
    UserHelper userHelper;
    Song song;

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
        Review review = vReview.get(position);

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

        Long systemTime = System.currentTimeMillis();
        Long timeDiff = systemTime - review.getDate();

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

    }

    @Override
    public int getItemCount() {
        return vReview.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView usernameID, timeAdded, songReview, songRating, songTitle, songArtist, albumName;
        ImageView albumImage;
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
        }
    }
}
