package com.example.aayush.firebasecrud;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ActorAct extends AppCompatActivity implements View.OnClickListener, ValueEventListener {

    TextView tv;
    EditText editText;
    Button b1;
    RatingBar ratingBar;
    DatabaseReference reference;
    ListView lv;
    List<Movie> movieslist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actor);
        tv = (TextView)this.findViewById(R.id.tvactorname);
        String id = this.getIntent().getExtras().getString("key");
        String name = this.getIntent().getExtras().getString("name");
        tv.setText(name);
        editText  = (EditText)this.findViewById(R.id.txtmoviename);
        b1  = (Button)this.findViewById(R.id.btnaddmovie);
        ratingBar = (RatingBar)this.findViewById(R.id.ratingmovie);
        b1.setOnClickListener(this);
        reference = FirebaseDatabase.getInstance().getReference("movie").child(id);
        lv  = (ListView)this.findViewById(R.id.lvmovie);
        reference.addValueEventListener(this);
        movieslist = new ArrayList<>();

    }

    @Override
    public void onClick(View view) {
      String moviename = editText.getText().toString();
      String ratings = String.valueOf(ratingBar.getRating());
      String key = reference.push().getKey();
      Movie m = new Movie(key,moviename,ratings);
      reference.child(key).setValue(m).addOnCompleteListener(this, new OnCompleteListener<Void>() {
          @Override
          public void onComplete(@NonNull Task<Void> task) {
              if(task.isSuccessful()){
                  Toast.makeText(ActorAct.this, "Data Added", Toast.LENGTH_SHORT).show();
                  editText.setText("");
              }
          }
      }).addOnFailureListener(this, new OnFailureListener() {
          @Override
          public void onFailure(@NonNull Exception e) {
              Toast.makeText(ActorAct.this, "ERROR: "+e, Toast.LENGTH_SHORT).show();
          }
      });

    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        movieslist.clear();
        for(DataSnapshot mydata :dataSnapshot.getChildren()){
            Movie actor =  mydata.getValue(Movie.class);
            movieslist.add(actor);
        }
        MovieList actorList1 = new MovieList(this,movieslist);
        lv.setAdapter(actorList1);
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
