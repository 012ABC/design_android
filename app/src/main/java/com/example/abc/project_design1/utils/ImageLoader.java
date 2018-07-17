package com.example.abc.project_design1.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.LruCache;
import android.widget.ImageView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ImageLoader {

    LruCache<String,Bitmap>mImageCache;

    ExecutorService mExecutorService= Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public ImageLoader(){
        initImageCache();
    }

    private void initImageCache() {
        final int maxMemory=(int)(Runtime.getRuntime().maxMemory()/1024);
        final int cacheSize=maxMemory/4;
        mImageCache=new LruCache<String,Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes()*value.getHeight()/1024;
            }
        };
    }
    public void displayImage(final String url, final ImageView imageView){
        imageView.setTag(url);
        mExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap=downloadImage(url);
                if (bitmap==null){
                    return;
                }
                if (imageView.getTag().equals(url)){
                    imageView.setImageBitmap(bitmap);
                }
                mImageCache.put(url,bitmap);
            }
        });
    }

    private Bitmap downloadImage(String imageUrl) {
        Bitmap bitmap=null;
        try {
            URL url=new URL(imageUrl);
            HttpURLConnection connection= (HttpURLConnection) url.openConnection();
            bitmap= BitmapFactory.decodeStream(connection.getInputStream());
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}