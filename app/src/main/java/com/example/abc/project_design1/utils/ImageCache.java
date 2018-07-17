package com.example.abc.project_design1.utils;

import android.graphics.Bitmap;
import android.util.LruCache;

public class ImageCache {

    LruCache<String,Bitmap>mImageCache;

    public ImageCache(){
        initImageCache();
    }

    private void initImageCache() {
        int maxMemory=(int)(Runtime.getRuntime().maxMemory()/1024);
        int cacheSize=maxMemory/4;
        mImageCache=new LruCache<String,Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getRowBytes()*bitmap.getHeight()/1024;
            }
        };
    }
    public void put(String url,Bitmap bitmap){
        mImageCache.put(url,bitmap);
    }
    public Bitmap get(String url){
        return mImageCache.get(url);
    }
}
