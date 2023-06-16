package com.example.mobprogprojectlec.UI.NavBar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.example.mobprogprojectlec.Database.AlbumHelper;
import com.example.mobprogprojectlec.Database.ArtistHelper;
import com.example.mobprogprojectlec.Database.ReviewHelper;
import com.example.mobprogprojectlec.Database.UserHelper;
import com.example.mobprogprojectlec.Model.Album;
import com.example.mobprogprojectlec.Model.Artist;
import com.example.mobprogprojectlec.Model.Song;
import com.example.mobprogprojectlec.Model.User;
import com.example.mobprogprojectlec.R;

public class SongDetailActivity extends AppCompatActivity {

    public static final String detSong = "detSong";
    TextView songTitle, songArtist, songGenre;
    ImageView albumImage, albumImageBack, includedLayout, spotifyIcon;
    RatingBar songRating;
    EditText songComment;
    ToggleButton playPauseButton;
    MediaPlayer mediaPlayer;
    Button reviewButton;
    ImageButton backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_detail);

        albumImage = findViewById(R.id.albumImage);
        albumImageBack = findViewById(R.id.albumImageBack);
        spotifyIcon = findViewById(R.id.spotifyIcon);
        songTitle = findViewById(R.id.songTitle);
        songArtist = findViewById(R.id.songArtist);
        songGenre = findViewById(R.id.songGenre);
        playPauseButton = findViewById(R.id.playPauseButton);
        mediaPlayer = new MediaPlayer();
        songRating = findViewById(R.id.songRating);
        songComment = findViewById(R.id.songComment);
        reviewButton = findViewById(R.id.reviewButton);
        backBtn = findViewById(R.id.backBtn);

        if (getIntent().getExtras() != null) {
            Song song = getIntent().getParcelableExtra(detSong, Song.class);

            AlbumHelper albumHelper = new AlbumHelper(this);
            albumHelper.open();
            Album album = albumHelper.fetchAlbum(song.getAlbumId());
            albumHelper.close();

            ArtistHelper artistHelper = new ArtistHelper(this);
            artistHelper.open();
            Artist artist = artistHelper.fetchArtist(song.getArtistId());
            artistHelper.close();

            Glide.with(this).load(album.getImage()).into(albumImage);
            Glide.with(this).load(album.getImage()).into(albumImageBack);
            songTitle.setText(song.getTitle());
            songArtist.setText(artist.getName());
            songGenre.setText(song.getGenre());

            spotifyIcon.setOnClickListener(v -> {
                String url = song.getSpotify();
                String songUrl = "spotify:track:" + url;

                Intent spotifyIntent = new Intent(Intent.ACTION_VIEW);
                spotifyIntent.setData(Uri.parse(songUrl));
                spotifyIntent.setPackage("com.spotify.music");

                if (spotifyIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(spotifyIntent);
                }
                else {
                    Intent webIntent = new Intent(Intent.ACTION_VIEW);
                    webIntent.setData(Uri.parse("https://open.spotify.com/track/" + url));
                    startActivity(webIntent);
                }

                Toast.makeText(this, "Opening Spotify", Toast.LENGTH_SHORT).show();
            });

            try {
                mediaPlayer.setDataSource(song.getPreview());
                mediaPlayer.prepare();
            } catch (Exception e) {
                e.printStackTrace();
            }

            playPauseButton.setOnClickListener(v -> {
                if(playPauseButton.isChecked()){
                    mediaPlayer.start();
                    Toast.makeText(this, "Playing", Toast.LENGTH_SHORT).show();
                }
                else {
                    mediaPlayer.pause();
                    mediaPlayer.reset();
                    Toast.makeText(this, "Paused", Toast.LENGTH_SHORT).show();
                }
            });

            mediaPlayer.setOnCompletionListener(mp -> playPauseButton.setChecked(false));

            reviewButton.setOnClickListener(v -> {
                if(validate()){
                    SharedPreferences sharedPreferences = getSharedPreferences("username", MODE_PRIVATE);
                    String username = sharedPreferences.getString("username", "");

                    UserHelper userHelper = new UserHelper(this);
                    userHelper.open();
                    User user = userHelper.getUser(username);
                    userHelper.close();

                    ReviewHelper reviewHelper = new ReviewHelper(this);
                    reviewHelper.open();
                    long time = System.currentTimeMillis();
                    reviewHelper.insertReview(songComment.getText().toString(), user.getId(), song.getId(), songRating.getRating(), time);
                    reviewHelper.close();
                    Toast.makeText(this, "Review Submitted", Toast.LENGTH_SHORT).show();
                }

            });
        }
        else {
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        }

        backBtn.setOnClickListener(v -> finish());

        includedLayout = findViewById(R.id.includedLayout);
        int nightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        TextView songPreview = findViewById(R.id.songPreview);
        TextView rating = findViewById(R.id.rating);
        TextView Review = findViewById(R.id.Review);

        songPreview.setText("Track Preview");
        rating.setText("Rate this song");
        Review.setText("Write a Review for this Song");

        if (nightMode == Configuration.UI_MODE_NIGHT_YES) {
            includedLayout.setImageResource(R.drawable.rounded_background_dark);
            songTitle.setTextColor(getResources().getColor(R.color.brown_3));
            songArtist.setTextColor(getResources().getColor(R.color.brown_3));
            songGenre.setTextColor(getResources().getColor(R.color.brown_3));
            songComment.setTextColor(getResources().getColor(R.color.brown_3));
            songComment.setBackgroundColor(getResources().getColor(R.color.black));
            songPreview.setTextColor(getResources().getColor(R.color.brown_3));
            rating.setTextColor(getResources().getColor(R.color.brown_3));
            Review.setTextColor(getResources().getColor(R.color.brown_3));

        } else {
            includedLayout.setImageResource(R.drawable.rounded_background);
            songTitle.setTextColor(getResources().getColor(R.color.brown_1));
            songArtist.setTextColor(getResources().getColor(R.color.brown_1));
            songGenre.setTextColor(getResources().getColor(R.color.brown_1));
            songComment.setTextColor(getResources().getColor(R.color.brown_1));
            songComment.setBackgroundColor(getResources().getColor(R.color.white));
            songPreview.setTextColor(getResources().getColor(R.color.brown_1));
            rating.setTextColor(getResources().getColor(R.color.brown_1));
            Review.setTextColor(getResources().getColor(R.color.brown_1));
        }

//        if (getSupportActionBar() != null) {
//            getSupportActionBar().setTitle("Song Detail");
//        }
//        else {
//            setTitle("Song Detail");
//        }

    }

    public Boolean validate(){
        if(songComment.getText().toString().length() == 0){
            Toast.makeText(this, "Comment must be filled", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.release();
    }


}