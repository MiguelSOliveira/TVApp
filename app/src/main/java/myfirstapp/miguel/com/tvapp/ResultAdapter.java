package myfirstapp.miguel.com.tvapp;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ResultAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final ArrayList<String> titles;
    private final ArrayList<Bitmap> posters;

    public ResultAdapter(Activity context, ArrayList<String> titles, ArrayList<Bitmap> posters) {
        super(context, R.layout.single_item, titles);
        this.context = context;
        this.titles = titles;
        this.posters = posters;

    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.single_item, null, true);
        TextView title = (TextView) rowView.findViewById(R.id.titleResult);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.coverResult);
        title.setText(titles.get(position));

        imageView.setImageBitmap(posters.get(position));
        return rowView;
    }
}