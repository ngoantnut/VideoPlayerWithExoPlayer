package com.example.videoplayer1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.ui.PlayerView;

public class PlayerActivity extends AppCompatActivity {
   //String url ="http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(getIntent().getStringExtra("Video") != null) {
            String url = bundle.getString("Video");

            ExoPlayer player = new ExoPlayer.Builder(this).build();
            PlayerView playerView = findViewById(R.id.player);
            playerView.setPlayer(player);

            MediaItem mediaItem = MediaItem.fromUri(url);
            player.addMediaItem(mediaItem);

            player.prepare();
            player.play();


        }
        else {
            Toast.makeText(this, "Lỗi!!!", Toast.LENGTH_SHORT).show();
        }





    }
}