package com.example.abc.project_design1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.abc.project_design1.utils.ImageLoader;

public class MainActivity extends AppCompatActivity {

    private ImageLoader mImageLoder=new ImageLoader();
    private ImageView mImage;
    public static final String url="http://image.jisuxz.com/desktop/2031/jisu_awngziwen_30.jpg";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImage = findViewById(R.id.img);

        new Thread(new Runnable() {
            @Override
            public void run() {
                mImageLoder.displayImage(url,mImage);
            }
        }).start();

    }
}
