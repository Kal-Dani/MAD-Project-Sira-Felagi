package com.example.sra;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.Surface;
import android.view.TextureView;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        TextureView textureView = findViewById(R.id.textureView);

        textureView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
            @Override
            public void onSurfaceTextureAvailable(android.graphics.SurfaceTexture surfaceTexture, int width, int height) {
                MediaPlayer mediaPlayer = new MediaPlayer();
                try {
                    mediaPlayer.setDataSource(getApplicationContext(),
                            Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.opening_video));
                    mediaPlayer.setSurface(new Surface(surfaceTexture));
                    mediaPlayer.setLooping(false);
                    mediaPlayer.setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT);

                    mediaPlayer.setOnVideoSizeChangedListener((mp, videoWidth, videoHeight) -> {
                        // Resize TextureView to maintain aspect ratio
                        float ratio = (float) videoHeight / videoWidth;
                        int newHeight = (int) (textureView.getWidth() * ratio);
                        textureView.getLayoutParams().height = newHeight;
                        textureView.requestLayout();
                    });

                    mediaPlayer.prepare();
                    mediaPlayer.start();

                    mediaPlayer.setOnCompletionListener(mp -> {
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        finish();
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override public void onSurfaceTextureSizeChanged(android.graphics.SurfaceTexture surface, int width, int height) {}
            @Override public boolean onSurfaceTextureDestroyed(android.graphics.SurfaceTexture surface) { return true; }
            @Override public void onSurfaceTextureUpdated(android.graphics.SurfaceTexture surface) {}
        });


    }
}
