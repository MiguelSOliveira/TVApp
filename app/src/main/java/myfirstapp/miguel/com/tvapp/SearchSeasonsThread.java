package myfirstapp.miguel.com.tvapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class SearchSeasonsThread extends AsyncTask<String, Void, Void> {
    private final ListView listView;
    private final Context context;
    private final Activity activity;
    private final ArrayList<String> titles = new ArrayList<>();
    private final ArrayList<Bitmap> covers = new ArrayList<>();
    public static int SEASONS;

    public SearchSeasonsThread(ListView listView, Context context, Activity activity) {
        this.listView = listView;
        this.context = context;
        this.activity = activity;
    }

    @Override
    protected Void doInBackground(String... strings) {
        int seasonNumber = 1;
        while(true) {
            try {
                URL url = new URL(strings[0] + "&Season=" + seasonNumber++);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                InputStream stream = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
                StringBuilder builder = new StringBuilder();

                String inputString;
                while ((inputString = bufferedReader.readLine()) != null) {
                    builder.append(inputString);
                }

                JSONObject topLevel = new JSONObject(builder.toString());
                topLevel.getString("Title");

                urlConnection.disconnect();
            } catch (IOException | JSONException e) {
                SEASONS = seasonNumber-2;
                InfoActivity.setFinished();
                return null;
            }
        }
    }
}