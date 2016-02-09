package myfirstapp.miguel.com.tvapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText showName = (EditText) findViewById(R.id.showName);
        final Button searchButton = (Button) findViewById(R.id.searchBtn);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String searchUrl = "http://www.omdbapi.com/?s=" + showName.getText().toString().trim().replaceAll(" ", "+");
                Log.d("URL", searchUrl);
                Intent intent = new Intent(MainActivity.this, SearchResults.class);
                intent.putExtra("url", searchUrl);
                startActivity(intent);
            }
        });

    }
}
