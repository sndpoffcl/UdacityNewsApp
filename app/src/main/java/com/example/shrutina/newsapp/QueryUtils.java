package com.example.shrutina.newsapp;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import static com.example.shrutina.newsapp.NewsActivity.LOG_TAG;

/**
 * Created by SHRUTINA on 12-03-2017.
 */

public final class QueryUtils {
    //empty constructor
    QueryUtils(){

    }
    //method to control all the methods and then to finally fetch the json response

    public static List<NewsItem> fetchNewsItems(String stringUrl){
        URL url = createUrl(stringUrl);
        String jsonResponse= null;
        try{
            jsonResponse= makeHttpRequest(url);

        }
        catch (Exception e){
            Log.e(LOG_TAG,"Error making the request",e);
        }
        List<NewsItem> newsItemList = extractNewsJson(jsonResponse);
        return newsItemList;
    }
    //method to create url
    public static URL createUrl(String stringUrl){
        URL url= null;
        if(stringUrl==null)
            return null;
        else{
            try{
                url= new URL(stringUrl);
            }
            catch(Exception e){
                Log.e(LOG_TAG,"Problem creating the url",e);
            }
            return url;
        }
    }
    public static String makeHttpRequest(URL url)throws IOException{
        String newsJson="";
        if(url==null)
            return newsJson;
        else{
            HttpURLConnection urlConnection= null;
            InputStream inputStream= null;
            try{
                urlConnection= (HttpURLConnection) url.openConnection();
                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(15000);
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                //checking if the response is valid or not
                if(urlConnection.getResponseCode()==200){
                    inputStream= urlConnection.getInputStream();
                    newsJson = readFromStream(inputStream);         //readFromStream() is a helper method that retuns the json response from the given inputStream

                }
                else{
                    Log.e(LOG_TAG,"Error response code "+urlConnection.getResponseCode());
                }

            }
            catch(Exception e){
                Log.e(LOG_TAG,"Error in establishing the connection",e);
            }
            if(urlConnection!=null)
                urlConnection.disconnect();
            if(inputStream!=null)
                inputStream.close();
            return newsJson;
        }
    }

    public static String readFromStream(InputStream inputStream)throws IOException{
        StringBuilder outJson= new StringBuilder();
        if(inputStream!=null){
            InputStreamReader read = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader in = new BufferedReader(read);
            String line = in.readLine();
            while(line!=null){
                outJson.append(line);
                line= in.readLine();
            }
        }
        return outJson.toString();
    }

    //method to extract json response
    public static List<NewsItem> extractNewsJson(String newsJson){
        List<NewsItem> newsItemList= new ArrayList<NewsItem>();
        if(TextUtils.isEmpty(newsJson)==true)
            return null;
        try{
            JSONObject root = new JSONObject(newsJson);
            JSONArray articles =root.getJSONArray("articles");
            int i;
            for(i=0;i<articles.length();i++){
                JSONObject c= articles.getJSONObject(i);
                String author = c.getString("author");
                String title = c.getString("title");
                String date = c.getString("publishedAt");
                String url = c.getString("url");
                String imageUrl = c.getString("urlToImage");
                newsItemList.add(new NewsItem(author,title,date,imageUrl,url));
            }
        }
        catch(Exception e){
            Log.e(LOG_TAG,"Problem parsing the json",e);
        }
        return newsItemList;
    }
}
