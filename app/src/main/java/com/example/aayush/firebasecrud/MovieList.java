package com.example.aayush.firebasecrud;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Aayush on 11/9/2017.
 */

public class MovieList extends ArrayAdapter<Movie> {
    Activity context;
    List<Movie> actorList;
    public MovieList(Activity context,List<Movie> actorList) {
        super(context,R.layout.movielist,actorList);
        this.context = context;
        this.actorList = actorList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Movie actor = actorList.get(position);
        LayoutInflater layoutInflater = context.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.movielist,null,true);
        TextView textView = view.findViewById(R.id.tvmovielist);
        TextView textView1 = view.findViewById(R.id.tvmovieRating);
        textView.setText(actor.getMoviename());
        textView1.setText(actor.getRating());
        return view;
    }
}
