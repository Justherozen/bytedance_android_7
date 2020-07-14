package com.bytedance.videoplayer;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView imageView = findViewById(R.id.imageView);
        String url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1562328963755&di=f4aa87b95c87dc01ff0ca2c9150845c8&imgtype=0&src=http%3A%2F%2Fwww.uimaker.com%2Fuploads%2Fallimg%2F121105%2F1_121105084854_2.jpg";

        Glide.with(this)
                .load(url)
                .error(R.drawable.icon_failure)
                .fallback(R.drawable.loading_picture)
                .transition(withCrossFade(4000))
                .into(imageView);

        findViewById(R.id.btn_video).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MyVideoPlayer.class);
                startActivity(intent);
            }
        });
    }
}
