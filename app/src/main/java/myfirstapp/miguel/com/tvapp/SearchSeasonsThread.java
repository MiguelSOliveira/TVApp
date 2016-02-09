package myfirstapp.miguel.com.tvapp;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SearchSeasonsThread extends AsyncTask<String, Void, Void> {
    public static int SEASONS;

    @Override
    protected Void doInBackground(String... strings) {
        int seasonNumber = 1;
        while(true) {
            try {
                URL url = new URL(strings[0] + "&Season=" + seasonNumber);
                seasonNumber += 5;
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                InputStream stream = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
                StringBuilder builder = new StringBuilder();

                String inputString;
                while ((inputString = bufferedReader.readLine()) != null) {
                    builder.append(inputString);
                }

                urlConnection.disconnect();
                JSONObject topLevel = new JSONObject(builder.toString());
                topLevel.getString("Title");

                SEASONS = seasonNumber-2;
                InfoActivity.setFinished();
                break;
            } catch (IOException | JSONException e) {
                seasonNumber--;
            }
        }
        return null;
    }
}