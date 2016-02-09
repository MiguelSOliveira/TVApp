package myfirstapp.miguel.com.tvapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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

public class Request extends AsyncTask<String, Void, String> {
    private Context context;
    public static Bitmap cover;

    public Request(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {
        String response = "UNDEFINED";
        try {
            URL url = new URL(strings[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            InputStream stream = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder builder = new StringBuilder();

            String inputString;
            while ((inputString = bufferedReader.readLine()) != null) {
                builder.append(inputString);
            }

            JSONObject topLevel = new JSONObject(builder.toString());
            response = topLevel.toString();

            cover = getBitmapFromURL(topLevel.getString("Poster"));

            urlConnection.disconnect();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return response;
    }

    public static Bitmap getBitmapFromURL(String urlStr) {
        try {
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            connection.disconnect();
            return myBitmap;
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    protected void onPostExecute(String temp) {

        try {
            JSONObject topLevel = new JSONObject(temp);

            final String title    = (String) topLevel.get("Title");
            final String plot     = (String) topLevel.get("Plot");
            final String rating   = (String) topLevel.get("imdbRating");
            final String released = (String) topLevel.get("Released");
            final String runTime  = (String) topLevel.get("Runtime");
            final String genre    = (String) topLevel.get("Genre");
            final String director = (String) topLevel.get("Director");
            final String writer   = (String) topLevel.get("Writer");
            final String poster   = (String) topLevel.get("Poster");
            final String type     = (String) topLevel.get("Type");
            final String votes    = (String) topLevel.get("imdbVotes");
            final String awards   = (String) topLevel.get("Awards");
            final String actors   = (String) topLevel.get("Actors");

            if(poster.equals("N/A"))
                changeCover(R.drawable.noimage);

            String params[] = new String[]{title, plot, rating, released, runTime, genre,
                director, writer, poster, type, votes, awards, actors};

            ResultInfo info = new ResultInfo(params);
            Intent intent = new Intent(context, InfoActivity.class);
            intent.putExtra("info", info);
            context.startActivity(intent);

        } catch(JSONException ignored){}
    }

    private void changeCover(int noimage) {
        Drawable myDrawable = context.getResources().getDrawable(R.drawable.noimage);
        cover = ((BitmapDrawable) myDrawable).getBitmap();
    }
}