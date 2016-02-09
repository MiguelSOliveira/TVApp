package myfirstapp.miguel.com.tvapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class EpisodesAndSeasonsActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results_layout);

        boolean exit = false;
        while(!exit) {
            if(InfoActivity.getFinished()) {

                int length = SearchSeasonsThread.SEASONS;
                String seasons[] = new String[length];
                final ListView results = (ListView) findViewById(R.id.results);

                for(int i = 0; i < length; i++)
                    seasons[i] = "Season " + (i+1);

                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, seasons);
                results.setAdapter(adapter);
                exit = true;
            }
            try { Thread.sleep(500); }
            catch (InterruptedException e) { exit = true; }
        }
    }
}
