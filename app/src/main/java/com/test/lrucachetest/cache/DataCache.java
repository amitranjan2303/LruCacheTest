package com.test.lrucachetest.cache;

import android.app.ActivityManager;
import android.content.Context;
import android.util.LruCache;

public class DataCache extends LruCache<String, String> {

    private LruCache mMemoryCache;
    public DataCache(int maxSize) {
        super(maxSize);
        mMemoryCache = new LruCache<String, String>(maxSize);

    }

    @Override
    protected int sizeOf(String key, String value) {
        return super.sizeOf(key, value);
    }

    @Override
    protected void entryRemoved(boolean evicted, String key, String oldValue, String newValue) {
        super.entryRemoved(evicted, key, oldValue, newValue);
    }

    public static int setUpDataCache(Context activity){
        int memClass = ((ActivityManager) activity.getSystemService( Context.ACTIVITY_SERVICE ) ).getMemoryClass();
        int cacheSize = 1024 * 1024 * memClass / 8;
        return cacheSize;
    }

    public void setDataToMemoryCache(String key, String value) {
        if(getDataFromMemoryCache(key) == null) {
            mMemoryCache.put(key,value);
        }
    }
    public  String getDataFromMemoryCache(String key) {
        return (String) mMemoryCache.get(key);
    }
}
