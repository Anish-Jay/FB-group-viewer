package io.google.facebookgroupviewer;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private DatabaseReference mDatabase;
    private ProgressDialog progressDialog;
    private List<Feed> feedList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.feed);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        feedList = new ArrayList<>();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("LOADING...");
        progressDialog.show();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                progressDialog.dismiss();
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String name =postSnapshot.child("name").getValue().toString();
                    String desc= postSnapshot.child("url").getValue().toString();
                    Feed upload = new Feed(name,desc);
                    feedList.add(upload);
                }

                    adapter = new myadapter(feedList,getApplicationContext());
                    recyclerView.setAdapter(adapter);




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss();

            }
        });


    }
}
