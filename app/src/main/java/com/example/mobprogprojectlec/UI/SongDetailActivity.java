package com.example.mobprogprojectlec.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.mobprogprojectlec.Model.Review;
import com.example.mobprogprojectlec.Model.Song;
import com.example.mobprogprojectlec.Model.User;
import com.example.mobprogprojectlec.R;

import java.util.Vector;

public class SongDetailActivity extends AppCompatActivity {

    public static final String detSong = "detSong";
    Vector<Song> vSong;
    TextView songTitle, songArtist, songGenre;
    ImageView albumImage;
    RatingBar songRating;
    EditText songComment;
    ToggleButton playPauseButton;
    MediaPlayer mediaPlayer;
    Button reviewButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_detail);

        albumImage = findViewById(R.id.albumImage);
        songTitle = findViewById(R.id.songTitle);
        songArtist = findViewById(R.id.songArtist);
        songGenre = findViewById(R.id.songGenre);
        playPauseButton = findViewById(R.id.playPauseButton);
        mediaPlayer = new MediaPlayer();
        songRating = findViewById(R.id.songRating);
        songComment = findViewById(R.id.songComment);
        reviewButton = findViewById(R.id.reviewButton);

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
            songTitle.setText(song.getTitle());
            songArtist.setText(artist.getName());
            songGenre.setText(song.getGenre());

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
                    reviewHelper.insertReview(songComment.getText().toString(), user.getId(), song.getId(), songRating.getRating());
                    reviewHelper.close();
                    Toast.makeText(this, "Review Submitted", Toast.LENGTH_SHORT).show();
                }

            });
        }
        else {
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Song Detail");
        }
        else {
            setTitle("Song Detail");
        }

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