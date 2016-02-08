package myfirstapp.miguel.com.tvapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.TextView;

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
    private TextView textView;
    private Context context;
    public static Bitmap cover;

    public Request(TextView textView, Context context) {
        this.textView = textView;
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

    private Bitmap getBitmapFromURL(String urlStr) {
        try {
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            connection.disconnect();
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }

    @Override
    protected void onPostExecute(String temp) {

        try {
            JSONObject topLevel = new JSONObject(temp);

            final String title = (String) topLevel.get("Title");
            final String plot = (String) topLevel.get("Plot");
            final String rating = (String) topLevel.get("imdbRating");
            final String released = (String) topLevel.get("Released");
            final String runTime = (String) topLevel.get("Runtime");
            final String genre = (String) topLevel.get("Genre");
            final String director = (String) topLevel.get("Director");
            final String writer = (String) topLevel.get("Writer");
            final String poster = (String) topLevel.get("Poster");

            String params[] = new String[]{title, plot, rating, released, runTime, genre,
                director, writer, poster};
            ResultInfo info = new ResultInfo(params);

            Intent intent = new Intent(context, InfoActivity.class);
            intent.putExtra("info", info);
            context.startActivity(intent);


        } catch(JSONException e){
            String params[] = new String[]{"N/A", "N/A", "N/A", "N/A", "N/A", "N/A", "N/A", "N/A",
                    "N/A"};
            ResultInfo info = new ResultInfo(params);
        }
    }
}