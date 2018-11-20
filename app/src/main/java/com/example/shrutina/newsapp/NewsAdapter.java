package com.example.shrutina.newsapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import static android.os.Build.VERSION_CODES.M;
import static com.example.shrutina.newsapp.NewsActivity.LOG_TAG;

/**
 * Created by SHRUTINA on 12-03-2017.
 */

public class NewsAdapter extends ArrayAdapter<NewsItem> {
    NewsAdapter(Context context, ArrayList<NewsItem> newsItemArrayList){
        super(context,0,newsItemArrayList);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View newsItemView = convertView;
        if (newsItemView == null) {
            newsItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }
        NewsItem currentNewsItem = getItem(position);

        String imageUrl = currentNewsItem.getmUrlImage();
        ImageView imageView = (ImageView) newsItemView.findViewById(R.id.image);
        new DownloadImageTask(imageView).execute(imageUrl);



        String title= currentNewsItem.getmTitle();
        TextView titleView = (TextView) newsItemView.findViewById(R.id.title);
        titleView.setText(title);

        String author = currentNewsItem.getmAuthor();
        if(author.equalsIgnoreCase("null")){
            author = "by Unknown";
        }
        else{
            author= "by "+author;
        }
        TextView authorView = (TextView) newsItemView.findViewById(R.id.author);
        authorView.setText(author);

        String date = currentNewsItem.getmDate();
        TextView dateView = (TextView) newsItemView.findViewById(R.id.date);
        dateView.setText(date);

        return newsItemView;

    }

    //Asynctask class for downloading image
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

}
