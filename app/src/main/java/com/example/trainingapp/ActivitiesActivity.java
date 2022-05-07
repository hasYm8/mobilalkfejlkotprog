package com.example.trainingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ActivitiesActivity extends AppCompatActivity {

    private String queryType;

    private ImageButton addImageButton;
    private RecyclerView activitiesRV;
    private ArrayList<ActivityItem> activitiesArrayList;
    private ActivitiesRVAdapter activitiesRVAdapter;
    private FirebaseFirestore db;
    ProgressBar loadingPB;

    FirebaseAuth mAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activities);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        addImageButton = findViewById(R.id.addImageButton);
        addImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ActivitiesActivity.this, AddNewActivity.class);
                startActivity(i);
            }
        });

        activitiesRV = findViewById(R.id.idRVactivities);
        loadingPB = findViewById(R.id.idProgressBar);

        db = FirebaseFirestore.getInstance();

//        activitiesArrayList = new ArrayList<>();
//        activitiesRV.setHasFixedSize(true);
//        activitiesRV.setLayoutManager(new LinearLayoutManager(this));

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            queryType = extras.getString("queryType");
        }

//        switch (queryType) {
//            case "showAll":
//                queryAll();
//                break;
//
//            case "mostCaloriesBurned":
//                queryMostCaloriesBurned();
//                break;
//
//            case "shortestWorkout":
//                queryShortestWorkout();
//                break;
//
//        }
//
//        activitiesRVAdapter = new ActivitiesRVAdapter(activitiesArrayList, this);
//
//        activitiesRV.setAdapter(activitiesRVAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();

        activitiesArrayList = new ArrayList<>();
        activitiesRV.setHasFixedSize(true);
        activitiesRV.setLayoutManager(new LinearLayoutManager(this));

        switch (queryType) {
            case "showAll":
                queryAll();
                break;

            case "mostCaloriesBurned":
                queryMostCaloriesBurned();
                break;

            case "shortestWorkout":
                queryShortestWorkout();
                break;

        }

        activitiesRVAdapter = new ActivitiesRVAdapter(activitiesArrayList, this);

        activitiesRV.setAdapter(activitiesRVAdapter);

    }

    private void queryAll() {
        db.collection("activities").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()) {
                    loadingPB.setVisibility(View.GONE);
                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot d : list) {
                        if (user.getUid().equals(d.get("userID"))) {
                            ActivityItem AI = d.toObject(ActivityItem.class);
                            activitiesArrayList.add(AI);
                        }
                    }
                    activitiesRVAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(ActivitiesActivity.this, "No data found in database", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ActivitiesActivity.this, "Fail to get the data.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void queryMostCaloriesBurned() {
        db.collection("activities")
                .whereEqualTo("userID", user.getUid())
                .orderBy("burnedcalories", Query.Direction.DESCENDING)
                .limit(1)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            loadingPB.setVisibility(View.GONE);
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                ActivityItem AI = d.toObject(ActivityItem.class);
                                activitiesArrayList.add(AI);
                            }
                            activitiesRVAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(ActivitiesActivity.this, "No data found in database", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ActivitiesActivity.this, "Fail to get the data.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void queryShortestWorkout() {
        db.collection("activities")
                .whereEqualTo("userID", user.getUid())
                .orderBy("duration", Query.Direction.ASCENDING)
                .limit(1)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            loadingPB.setVisibility(View.GONE);
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                ActivityItem AI = d.toObject(ActivityItem.class);
                                activitiesArrayList.add(AI);
                            }
                            activitiesRVAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(ActivitiesActivity.this, "No data found in database", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ActivitiesActivity.this, "Fail to get the data.", Toast.LENGTH_SHORT).show();
            }
        });
    }

}