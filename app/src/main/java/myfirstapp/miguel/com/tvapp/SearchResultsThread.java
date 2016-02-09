package myfirstapp.miguel.com.tvapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
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

public class SearchResultsThread extends AsyncTask<String, Void, String> {
    private final ListView listView;
    private final Context context;
    private final Activity activity;
    private final ArrayList<String> titles = new ArrayList<>();
    private final ArrayList<Bitmap> covers = new ArrayList<>();

    public SearchResultsThread(ListView listView, Context context, Activity activity) {
        this.listView = listView;
        this.context = context;
        this.activity = activity;
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

            JSONArray searchJSON = topLevel.getJSONArray("Search");
            final int length = searchJSON.length();
            int i;
            String resultTitle, imageUrl;

            for(i = 0; i < length; i++) {
                resultTitle = searchJSON.getJSONObject(i).getString("Title");
                imageUrl = searchJSON.getJSONObject(i).getString("Poster");
                titles.add(resultTitle);
                if(imageUrl.equals("N/A")) {
                    Drawable myDrawable = context.getResources().getDrawable(R.drawable.noimage);
                    covers.add(((BitmapDrawable) myDrawable).getBitmap());
                }
                else
                    covers.add(Request.getBitmapFromURL(imageUrl));
            }

            urlConnection.disconnect();
        } catch (IOException | JSONException e) {
            return "NotFound";
        }
        return response;
    }

    @Override
    protected void onPostExecute(String temp) {

        if(temp.equals("NotFound")) {
            final TextView notFound = (TextView) activity.findViewById(R.id.not_found);
            notFound.setVisibility(View.VISIBLE);
            return;
        }

        final ListView results = (ListView) activity.findViewById(R.id.results);
        results.setVisibility(View.VISIBLE);
        ResultAdapter adapter = new ResultAdapter(activity, titles, covers);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = (String) listView.getItemAtPosition(position);
                item = item.replace(" ", "+");

                String url = "http://www.omdbapi.com/?t=" + item + "&y=&plot=full&r=json";
                new Request(context, activity).execute(url);
            }
        });
    }
}