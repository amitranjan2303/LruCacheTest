package com.test.lrucachetest.cache;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

public class ImageCache extends LruCache<String, Bitmap> {

    private LruCache mMemoryCache;

    public ImageCache(int maxSize) {
        super(maxSize);
        mMemoryCache = new LruCache<String, Bitmap>(maxSize);
    }

    @Override
    protected int sizeOf(String key, Bitmap value) {
        return value.getByteCount();
    }

    @Override
    protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {
        oldValue.recycle();
    }
    public static int setUpImageCache(Context activity){
        int memClass = ((ActivityManager) activity.getSystemService( Context.ACTIVITY_SERVICE ) ).getMemoryClass();
        int cacheSize = 1024 * 1024 * memClass / 10;
        return cacheSize;
    }

    public void setImageCacheToMemoryCache(String key, Bitmap value) {
        if(getImageCacheFromMemoryCache(key) == null) {
            mMemoryCache.put(key,value);
        }
    }
    public  Bitmap getImageCacheFromMemoryCache(String key) {
        return (Bitmap) mMemoryCache.get(key);
    }

}