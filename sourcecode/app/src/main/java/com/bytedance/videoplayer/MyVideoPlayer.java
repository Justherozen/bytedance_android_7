package com.bytedance.videoplayer;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bytedance.videoplayer.player.VideoPlayerIJK;
import com.bytedance.videoplayer.player.VideoPlayerListener;

import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/**
 * 使用开源IjkPlayer播放视频
 */
public class MyVideoPlayer extends AppCompatActivity {
    private VideoPlayerIJK ijkPlayer;
    private MediaPlayer player;
    private SurfaceHolder holder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_video);
        setTitle("Video Player");

        ijkPlayer = findViewById(R.id.ijkPlayer);

        //加载native库
        try {
            IjkMediaPlayer.loadLibrariesOnce(null);
            IjkMediaPlayer.native_profileBegin("libijkplayer.so");
        } catch (Exception e) {
            this.finish();
        }
        ijkPlayer.setListener(new VideoPlayerListener());
        ijkPlayer.setVideoResource(R.raw.bytedance);
        findViewById(R.id.buttonPlay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ijkPlayer.start();
            }
        });
        findViewById(R.id.buttonPause).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ijkPlayer.pause();
            }
        });

        SeekBar seekBar = findViewById(R.id.seek_bar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    ijkPlayer.seekTo(ijkPlayer.getDuration() / 100 * progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        findViewById(R.id.btn_quit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        final TextView tv_total = findViewById(R.id.total);
        final TextView tv_current = findViewById(R.id.current);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                long current = ijkPlayer.getCurrentPosition()/1000;
                long total = ijkPlayer.getDuration()/1000;
                tv_current.setText(String.format(getResources().getString(R.string.progress_text), current/ 60, current% 60));
                tv_total.setText(String.format(getResources().getString(R.string.progress_text_all), total/ 60, total% 60));
                handler.postDelayed(this, 1000);
            }
        }, 1000);

    }

    private String getVideoPath() {
       return "android.resource://" + this.getPackageName() + "/" + R.raw.bytedance;
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (ijkPlayer.isPlaying()) {
            ijkPlayer.stop();
        }

        IjkMediaPlayer.native_profileEnd();
    }
}
