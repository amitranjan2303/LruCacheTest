package com.test.lrucachetest.repo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.test.lrucachetest.cache.ImageCache;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class ImageDownLoaderRepo extends AsyncTask<String, Integer, Bitmap> {

    private static final String REQUEST_METHOD = "GET";
    private static final int READ_TIMEOUT = 15000;
    private static final int CONNECTION_TIMEOUT = 15000;
    private Context mContext;
    private final WeakReference<ImageView> viewReference;

    public ImageDownLoaderRepo(ImageView imageView) {
        this.mContext = imageView.getContext();
        this.viewReference = new WeakReference<ImageView>(imageView);
    }

    @Override
    protected Bitmap doInBackground(String... strings) {

        String urlKey = strings[0];
        HttpsURLConnection connection = null;
        ImageCache imageCache = new ImageCache(ImageCache.setUpImageCache(mContext));
        Bitmap bitmap = imageCache.getImageCacheFromMemoryCache(urlKey);
        if (bitmap == null) {
            try {
                //Create a URL object holding our url
                URL myUrl = new URL(urlKey);

                //Create a connection
                connection = (HttpsURLConnection) myUrl.openConnection();

                //Set methods and timeouts
                connection.setRequestMethod(REQUEST_METHOD);
                connection.setReadTimeout(READ_TIMEOUT);
                connection.setConnectTimeout(CONNECTION_TIMEOUT);

                //Connect to our url
                connection.connect();

                InputStream is = connection.getInputStream(); //outputs an inputstream
                if (is != null) {
                    if (!isCancelled()) {
                        bitmap = BitmapFactory.decodeStream(is);
                        imageCache.setImageCacheToMemoryCache(urlKey, bitmap);
                    }
                }
            } catch (Exception e) {

            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        if (bitmap != null) {
            ImageView imageView = viewReference.get();
            if (imageView != null) {
                imageView.setImageBitmap(bitmap);
            }
        }

    }

    @Override
    protected void onCancelled(Bitmap bitmap) {
        super.onCancelled(bitmap);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
}
