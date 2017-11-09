package com.example.aayush.firebasecrud;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Aayush on 11/9/2017.
 */

public class ActorList extends ArrayAdapter<Actor> {


    Activity context;
    List<Actor> actorList;
    public ActorList(Activity context,List<Actor> actorList) {
        super(context,R.layout.actorlist,actorList);
        this.context = context;
        this.actorList = actorList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Actor actor = actorList.get(position);
       LayoutInflater layoutInflater = context.getLayoutInflater();
       View view = layoutInflater.inflate(R.layout.actorlist,null,true);
        TextView textView = view.findViewById(R.id.tvactor);
        TextView textView1 = view.findViewById(R.id.tvindustry);
        textView.setText(actor.getActor_name());
        textView1.setText(actor.getActor_industry());
        return view;
    }
}
