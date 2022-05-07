package com.example.trainingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddNewActivity extends AppCompatActivity {
    private static final String LOG_TAG = AddNewActivity.class.getName();


    EditText nameEditText;
    EditText durationEditText;
    EditText burnedCaloriesEditText;

    Button addButton;
    Button cancelButton;
    Button motivateButton;

    FirebaseAuth mAuth;
    FirebaseFirestore fStore;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new);

        this.db = FirebaseFirestore.getInstance();

        this.nameEditText = findViewById(R.id.nameEditText);
        this.durationEditText = findViewById(R.id.durationEditText);
        this.burnedCaloriesEditText = findViewById(R.id.burnedCaloriesEditText);

        this.mAuth = FirebaseAuth.getInstance();
        this.fStore = FirebaseFirestore.getInstance();

        this.addButton = findViewById(R.id.addButton);
        this.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewActivity();
            }
        });

        this.cancelButton = findViewById(R.id.cancelButton);
        this.cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        final MediaPlayer mp = MediaPlayer.create(this, R.raw.motivatemebeci);

        this.motivateButton = findViewById(R.id.motivateButton);
        this.motivateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.start();
            }
        });

    }

    private void addNewActivity() {
        String name = nameEditText.getText().toString();
        int duration = -1;
        int burnedCalories = -1;
        try {
            duration = Integer.parseInt(durationEditText.getText().toString());
            burnedCalories = Integer.parseInt(burnedCaloriesEditText.getText().toString());
        } catch (Exception e) {
            Toast.makeText(AddNewActivity.this, "Give a number!", Toast.LENGTH_SHORT).show();
        }

        if (!TextUtils.isEmpty(name) && duration >= 0 && burnedCalories >= 0) {
            Map<String, Object> exercise = new HashMap<>();
            exercise.put("userID", mAuth.getCurrentUser().getUid());
            exercise.put("name", name);
            exercise.put("duration", duration);
            exercise.put("burnedcalories", burnedCalories);

            db.collection("activities")
                    .add(exercise)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d(LOG_TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(LOG_TAG, "Error adding document", e);
                        }
                    });
        }

        finish();
    }

}