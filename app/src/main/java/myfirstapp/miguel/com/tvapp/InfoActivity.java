package myfirstapp.miguel.com.tvapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.concurrent.atomic.AtomicBoolean;

public class InfoActivity extends AppCompatActivity {

    private static final AtomicBoolean FINISHED = new AtomicBoolean(false);

    public static void setFinished() {
        FINISHED.set(true);
    }

    public static boolean getFinished() {
        return FINISHED.get();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_layout);

        final ResultInfo infoObject = (ResultInfo) getIntent().getSerializableExtra("info");
        final ImageView poster      = (ImageView) findViewById(R.id.poster);
        final TextView title        = (TextView) findViewById(R.id.movieName);
        final TextView plot         = (TextView) findViewById(R.id.plot);
        final TextView director     = (TextView) findViewById(R.id.director);
        final TextView genre        = (TextView) findViewById(R.id.genre);
        final TextView rating       = (TextView) findViewById(R.id.rating);
        final TextView released     = (TextView) findViewById(R.id.released);
        final TextView writer       = (TextView) findViewById(R.id.writers);
        final TextView runTime      = (TextView) findViewById(R.id.runTime);
        final TextView votes        = (TextView) findViewById(R.id.votes);
        final TextView type         = (TextView) findViewById(R.id.type);
        final TextView awards       = (TextView) findViewById(R.id.awards);
        final TextView actors       = (TextView) findViewById(R.id.actors);
        final TextView episodes     = (TextView) findViewById(R.id.episodes);
        final String actorNames = infoObject.info[12].replaceAll(", ", "\n");

        if(infoObject.info[9].equals("series")) {
            episodes.setVisibility(View.VISIBLE);

            final ListView results = (ListView) findViewById(R.id.results);
            final String url = "http://www.omdbapi.com/?t=" + infoObject.info[0].trim().replace(" ", "+");
            new SearchSeasonsThread(results, this, InfoActivity.this).execute(url);

            episodes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(InfoActivity.this, EpisodesAndSeasonsActivity.class);
                    intent.putExtra("title", infoObject.info[0]);
                    startActivity(intent);
                }
            });
        }

        plot.setMovementMethod(new ScrollingMovementMethod());

        title.setText(infoObject.info[0]);
        plot.setText(infoObject.info[1]);
        rating.setText(infoObject.info[2]);
        released.setText(infoObject.info[3]);
        runTime.setText(infoObject.info[4]);
        genre.setText(infoObject.info[5]);
        director.setText(infoObject.info[6]);
        writer.setText(infoObject.info[7]);
        type.setText(infoObject.info[9]);
        votes.setText(infoObject.info[10]);
        awards.setText(infoObject.info[11]);
        actors.setText(actorNames);
        poster.setImageBitmap(Request.cover);
    }
}
