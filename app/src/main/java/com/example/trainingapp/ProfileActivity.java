package com.example.trainingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

public class ProfileActivity extends AppCompatActivity {
    private static final String LOG_TAG = ProfileActivity.class.getName();

    TextView profileTextView;
    TextView userNameTextView;
    TextView emailTextView;
    TextView genderTextView;
    TextView ageTextView;
    TextView bodyWeightTextView;

    Button activitiesButton;
    Button cancelButton;
    Button mostCaloriesBurnedButton;
    Button shortestWorkoutButton;

    FirebaseAuth mAuth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        this.profileTextView = findViewById(R.id.profileTextView);
        this.userNameTextView = findViewById(R.id.userNameTextView);
        this.emailTextView = findViewById(R.id.userEmailTextView);
        this.genderTextView = findViewById(R.id.genderTextView);
        this.ageTextView = findViewById(R.id.ageTextView);
        this.bodyWeightTextView = findViewById(R.id.bodyWeightTextView);

        this.activitiesButton = findViewById(R.id.activitiesButton);
        this.cancelButton = findViewById(R.id.cancelButton);
        this.mostCaloriesBurnedButton = findViewById(R.id.mostCaloriesBurnedButton);
        this.shortestWorkoutButton = findViewById(R.id.shortestWorkoutButton);

        this.mAuth = FirebaseAuth.getInstance();
        this.fStore = FirebaseFirestore.getInstance();

        fStore.collection("users").document(mAuth.getCurrentUser().getUid())
                .get().addOnCompleteListener(task -> {
            if(task.isSuccessful() && task.getResult() != null){
                String username = task.getResult().getString("username");
                String email = task.getResult().getString("email");
                String gender = task.getResult().getString("gender");
                String age = task.getResult().getString("age");
                String bodyWeight = task.getResult().getString("bodyWeight");

                userNameTextView.setText(String.format("%s\n\t%s", userNameTextView.getText(), username));
                emailTextView.setText(String.format("%s\n\t%s", emailTextView.getText(), email));
                genderTextView.setText(String.format("%s\n\t%s", genderTextView.getText(), gender));
                ageTextView.setText(String.format("%s\n\t%s years old.", ageTextView.getText(), age));
                bodyWeightTextView.setText(String.format("%s\n\t%s kg.", bodyWeightTextView.getText(), bodyWeight));
            }else{
                Toast.makeText(ProfileActivity.this, "Something is fishy!", Toast.LENGTH_SHORT).show();
            }
        });

        activitiesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String queryType = "showAll";
                Intent i = new Intent(ProfileActivity.this,ActivitiesActivity.class);
                i.putExtra("queryType", queryType);
                startActivity(i);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mostCaloriesBurnedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String queryType = "mostCaloriesBurned";
                Intent i = new Intent(ProfileActivity.this,ActivitiesActivity.class);
                i.putExtra("queryType", queryType);
                startActivity(i);
            }
        });

        shortestWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String queryType = "shortestWorkout";
                Intent i = new Intent(ProfileActivity.this,ActivitiesActivity.class);
                i.putExtra("queryType", queryType);
                startActivity(i);
            }
        });

    }

}