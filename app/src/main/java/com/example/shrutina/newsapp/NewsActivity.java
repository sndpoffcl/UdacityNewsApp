package com.example.shrutina.newsapp;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static android.os.Build.VERSION_CODES.N;

public class NewsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<NewsItem>> {
    public static final String LOG_TAG = NewsActivity.class.getName();
    private NewsAdapter mAdapter;
    public static final int NEWS_LOADER_ID=1;

    public static final String NEWS_FEED_URL="https://newsapi.org/v1/articles?source=the-times-of-india&sortBy=latest&apiKey=9c84deba3e684349997e59a314197716";

    @Override
    public Loader<List<NewsItem>> onCreateLoader(int id, Bundle args) {
        return new NewsLoader(this,NEWS_FEED_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<NewsItem>> loader, List<NewsItem> data) {
        mAdapter.clear();
        if(data!=null&& !data.isEmpty()){
            mAdapter.addAll(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<NewsItem>> loader) {
        mAdapter.clear();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        LoaderManager loaderManager= getLoaderManager();
        loaderManager.initLoader(NEWS_LOADER_ID,null,this);


        ListView newsListView = (ListView)findViewById(R.id.list);

        mAdapter = new NewsAdapter(this,new ArrayList<NewsItem>());

        newsListView.setAdapter(mAdapter);



        //setting an onclickListener to throw intents to the web
        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
              @Override
              public  void onItemClick(AdapterView<?> parent, View view, int position, long id){
                  NewsItem currentItem= mAdapter.getItem(position);

                  Intent urlIntent = new Intent(Intent.ACTION_VIEW,
                          Uri.parse(currentItem.getmUrl()));
                  startActivity(urlIntent);

              }
          }
        );
    }

}
