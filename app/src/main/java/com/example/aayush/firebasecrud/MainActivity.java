package com.example.aayush.firebasecrud;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
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

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ValueEventListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    EditText t1;
    Spinner spinner;
    Button b1;
    DatabaseReference databaseReference;
    ListView lv;
    List<Actor> actorList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        t1 = (EditText)this.findViewById(R.id.txtActorName);
        spinner = (Spinner)this.findViewById(R.id.spinerIndustry);
        b1 = (Button)this.findViewById(R.id.btnaddData);
        lv = (ListView)this.findViewById(R.id.lvActor);
        b1.setOnClickListener(this);
        databaseReference = FirebaseDatabase.getInstance().getReference("actor");
        databaseReference.addValueEventListener(this);
        actorList = new ArrayList<>();
        lv.setOnItemClickListener(this);
        lv.setOnItemLongClickListener(this);


    }

    @Override
    public void onClick(View view) {
        String actorname = t1.getText().toString();
        String industry = spinner.getSelectedItem().toString();
        String key = databaseReference.push().getKey();
        Actor actor = new Actor(key,actorname,industry);
        databaseReference.child(key).setValue(actor).addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Data Saved", Toast.LENGTH_SHORT).show();
                    t1.setText("");
                }

            }
        }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "ERROR:  "+e, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        actorList.clear();
        for(DataSnapshot mydata :dataSnapshot.getChildren()){
            Actor actor =  mydata.getValue(Actor.class);
            actorList.add(actor);
        }
        ActorList actorList1 = new ActorList(this,actorList);
        lv.setAdapter(actorList1);
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
     Actor actor =  actorList.get(i);
     String id = actor.getActor_id();
     String name = actor.getActor_name();
     Intent intent = new Intent(this,ActorAct.class);
     intent.putExtra("key",id);
     intent.putExtra("name",name);
     startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
       final Actor actor =  actorList.get(i);
        LayoutInflater layoutInflater  = this.getLayoutInflater();
        View view1   = layoutInflater.inflate(R.layout.updatedelete,null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Updating Actor: "+actor.getActor_name());
        builder.setView(view1);
        final AlertDialog dialog =  builder.create();
        dialog.show();
        final EditText t1 =  view1.findViewById(R.id.txtnewactorname);
        Button b1  = view1.findViewById(R.id.btnUpdate);
        Button b2 = view1.findViewById(R.id.btnDelete);
        final Spinner spin = view1.findViewById(R.id.spinnernewindustry);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             DatabaseReference databaseReference  = FirebaseDatabase.getInstance().getReference("actor").child(actor.getActor_id());
             String str =  spin.getSelectedItem().toString();
             String name = t1.getText().toString();
             Actor actor1 = new Actor(actor.getActor_id(),name,str);
             databaseReference.setValue(actor1);
                Toast.makeText(MainActivity.this, "Data Updated", Toast.LENGTH_SHORT).show();
                dialog.dismiss();

            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference actorref =   FirebaseDatabase.getInstance().getReference("actor").child(actor.getActor_id());
                DatabaseReference movieref =   FirebaseDatabase.getInstance().getReference("movie").child(actor.getActor_id());
                actorref.removeValue();
                movieref.removeValue();
                Toast.makeText(MainActivity.this, "Data Deleted..", Toast.LENGTH_SHORT).show();

            }
        });
        return true;
    }
}
