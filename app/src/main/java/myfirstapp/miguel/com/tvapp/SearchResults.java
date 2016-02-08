package myfirstapp.miguel.com.tvapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

public class SearchResults extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results_layout);

        ListView results = (ListView) findViewById(R.id.results);

        final String url = getIntent().getStringExtra("url");
        new SearchResultsThread(results, this, SearchResults.this).execute(url);
    }
}
