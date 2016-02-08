package myfirstapp.miguel.com.tvapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView showName = (TextView) findViewById(R.id.showName);
        final Button searchButton = (Button) findViewById(R.id.searchBtn);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = showName.getText().toString().replace(" ", "+");
                String url = "http://www.omdbapi.com/?t=" + name + "&y=2011&plot=full&r=json";
                new Request(showName, MainActivity.this).execute(url);
            }
        });

    }
}
