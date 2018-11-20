package com.example.shrutina.newsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;

import static com.example.shrutina.newsapp.NewsActivity.LOG_TAG;

/**
 * Created by SHRUTINA on 12-03-2017.
 */

public class NewsLoader extends AsyncTaskLoader<List<NewsItem>> {
    String mUrl;
    NewsLoader(Context context, String url){
        super(context);
        mUrl=url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<NewsItem> loadInBackground() {
        if(mUrl==null)
            return null;
        List<NewsItem> newsItemList= QueryUtils.fetchNewsItems(mUrl);
        Log.d(LOG_TAG,"fetch done");
        return  newsItemList;
    }
}
