package com.test.lrucachetest.repo;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.test.lrucachetest.cache.DataCache;
import com.test.lrucachetest.model.DataModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class DataRespository extends AsyncTask<String, Integer, String> {

    public static final String REQUEST_METHOD = "GET";
    public static final int READ_TIMEOUT = 15000;
    public static final int CONNECTION_TIMEOUT = 15000;
    private String urlKey;
    private Context mContext;
    private ResultCallback mResultCallback;

    public interface ResultCallback {
        void onResponseSuccess(DataModel dataModel);
    }

    public DataRespository(Context context) {

        this.mContext = context;
        mResultCallback = (ResultCallback) context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... strings) {

        urlKey = strings[0];
        HttpURLConnection connection = null;
        try {
            //Create a URL object holding our url
            URL myUrl = new URL(urlKey);

            //Create a connection
            connection = (HttpURLConnection) myUrl.openConnection();

            //Set methods and timeouts
            connection.setRequestMethod(REQUEST_METHOD);
            connection.setReadTimeout(READ_TIMEOUT);
            connection.setConnectTimeout(CONNECTION_TIMEOUT);

            //Connect to our url
            connection.connect();

            InputStream is = connection.getInputStream(); //outputs an inputstream

            //Create a new InputStreamReader
            InputStreamReader streamReader = new InputStreamReader(is);
            BufferedReader reader = new BufferedReader(streamReader);
            StringBuilder sb = new StringBuilder();
            String line = null;

            while ((line = reader.readLine()) != null) {
                sb.append(line);
                if (isCancelled()) {
                    break;
                }
            }
            is.close();
            streamReader.close();
            //Set our result equal to our stringBuilder
            return sb.toString();

        } catch (IOException e) {
            Log.w("IOException", "with GetHTTPsAsync. " + e);

        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return null;

    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String response) {
        super.onPostExecute(response);

        DataCache dataCache = new DataCache(DataCache.setUpDataCache(mContext));
        DataModel dataModel = null;
        if (!TextUtils.isEmpty(response)) {
            dataCache.setDataToMemoryCache(urlKey, response);
        } else {
            response = dataCache.getDataFromMemoryCache(urlKey);
        }
        Gson gson = new Gson();
        dataModel = gson.fromJson(response, DataModel.class);

        if (mResultCallback != null) {
            mResultCallback.onResponseSuccess(dataModel);
        }

    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    @Override
    protected void onCancelled(String s) {
        super.onCancelled(s);
    }
}
