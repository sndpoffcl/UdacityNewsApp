package com.example.shrutina.newsapp;

/**
 * Created by SHRUTINA on 12-03-2017.
 */

public class NewsItem {
    private String mAuthor,mTitle,mUrlImage,mDate,mUrl;
    NewsItem(String author,String title,String date,String urlImage,String url){
        mAuthor= author;
        mTitle= title;
        mDate= date;
        mUrlImage= urlImage;
        mUrl= url;
    }
    public String getmAuthor(){
        return mAuthor;
    }
    public String getmTitle(){
        return mTitle;
    }
    public String getmUrlImage(){
        return mUrlImage;
    }
    public String getmDate(){
        return mDate;
    }
    public String getmUrl(){
        return mUrl;
    }
}
