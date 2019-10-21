package com.example.videoplayer;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    private int currentPosition = -1;
    private int actualSongId = -1;
    private  ImageButton actualImageButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final VideoView videoView = (VideoView) findViewById(R.id.videoView);
        final MediaController mediaController = new MediaController(this);

        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
        mediaController.setVisibility(View.INVISIBLE);

        final MediaPlayer mediaPlayer = new MediaPlayer();


        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {

                mediaController.setAnchorView(videoView);
                mediaController.setVisibility(View.VISIBLE);

            }
        });
        //run it
//        videoView.start();
//        final Button button1 = findViewById(R.id.button1);
//        final Button button1 = findViewById(R.id.button2);
        final ImageButton leftUpper = findViewById(R.id.imageLeftUp);
        ImageButton leftDown = findViewById(R.id.imageLeftDown);
        ImageButton rightUpper = findViewById(R.id.imageRightUp);
        ImageButton rightDown = findViewById(R.id.imageRightDown);
        final ImageButton firstSong = findViewById(R.id.songFirstControl);
        final ImageButton secondSong = findViewById(R.id.songSecondControl);
        leftUpper.setClickable(true);
//        leftDown.setClickable(true);
//        rightDown.setClickable(true);
//        rightUpper.setClickable(true);
//        button1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.e("MAIN", "MyClass.getView() — get item number ");
//                videoView.start();
//                button1.setVisibility(View.INVISIBLE);
//
//
//            }
//        });

        leftUpper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                leftUpper.setVisibility(View.INVISIBLE);
                videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.v);
                videoView.start();
                Toast.makeText(MainActivity.this,
                        "The video is playing",
                        Toast.LENGTH_SHORT).show();
            }
        });
        leftDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.v2);
                videoView.start();
                Toast.makeText(MainActivity.this,
                        "The video is playing",
                        Toast.LENGTH_SHORT).show();

            }
        });
        rightUpper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.v3);
                videoView.start();
                Toast.makeText(MainActivity.this,
                        "The video is playing",
                        Toast.LENGTH_SHORT).show();
            }
        });
        rightDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,
                        "The video is playing",
                        Toast.LENGTH_SHORT).show();

                videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.v4);
                videoView.start();

            }
        });

        firstSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toogleMusic(mediaPlayer, firstSong, 1);


            }
        });
        secondSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                toogleMusic(mediaPlayer, secondSong, 2);
            }
        });


    }
    private void playSong(MediaPlayer mediaPlayer, final ImageButton songButton, int songId){
        actualSongId = songId;
        actualImageButton=songButton;
        Toast.makeText(MainActivity.this,
                "The song is playing",
                Toast.LENGTH_LONG).show();
        Uri mediaPath;
        if (songId == 1) {
            mediaPath = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.m1);
        } else {
            mediaPath = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.m2);
        }
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(getApplicationContext(), mediaPath);
            mediaPlayer.prepare();
            mediaPlayer.start();
            songButton.setBackgroundResource(R.drawable.ic_pause_black_24dp);
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    actualSongId = -1;
                    currentPosition = -1;
                    songButton.setBackgroundResource(R.drawable.ic_play_arrow_black_24dp);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void toogleMusic(MediaPlayer mediaPlayer, final ImageButton songButton, int songId) {
        if (actualSongId == -1) {
            playSong(mediaPlayer, songButton, songId);
        } else {
            if (songId == actualSongId) {
                if (mediaPlayer.isPlaying()) {
                    currentPosition = mediaPlayer.getCurrentPosition();
                    mediaPlayer.pause();
                    songButton.setBackgroundResource(R.drawable.ic_play_arrow_black_24dp);
                } else {
                    mediaPlayer.seekTo(currentPosition);
                    mediaPlayer.start();
                    songButton.setBackgroundResource(R.drawable.ic_pause_black_24dp);
                }
            } else {
                currentPosition = -1;
                actualSongId = songId;
                actualImageButton.setBackgroundResource(R.drawable.ic_play_arrow_black_24dp);
                playSong(mediaPlayer, songButton, songId);

            }
        }
    }


}
