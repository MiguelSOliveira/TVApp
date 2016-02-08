package myfirstapp.miguel.com.tvapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

public class InfoActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_layout);

        final ResultInfo infoObject = (ResultInfo) getIntent().getSerializableExtra("info");
        final TextView title        = (TextView) findViewById(R.id.movieName);
        final TextView plot         = (TextView) findViewById(R.id.plot);
        final TextView director     = (TextView) findViewById(R.id.director);
        final TextView genre        = (TextView) findViewById(R.id.genre);
        final TextView rating       = (TextView) findViewById(R.id.rating);
        final TextView released     = (TextView) findViewById(R.id.released);
        final TextView writer       = (TextView) findViewById(R.id.writers);
        final TextView runTime      = (TextView) findViewById(R.id.runTime);
        final ImageView poster      = (ImageView) findViewById(R.id.poster);
        final TextView votes        = (TextView) findViewById(R.id.votes);
        final TextView type        = (TextView) findViewById(R.id.type);
        final TextView awards        = (TextView) findViewById(R.id.awards);

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
        poster.setImageBitmap(Request.cover);
    }
}
