package com.example.trainingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    private static final String LOG_TAG = RegisterActivity.class.getName();

    EditText userNameEditText;
    EditText userEmailEditText;
    EditText passwordEditText;
    EditText passwordAgainEditText;
    RadioGroup genderRadioButtonGroup;
    EditText bodyWeightEditText;
    EditText ageEditText;
    Button registerButton;

    FirebaseAuth mAuth;
    FirebaseFirestore fStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        this.userNameEditText = findViewById(R.id.userNameEditText);
        this.userEmailEditText = findViewById(R.id.userEmailEditText);
        this.passwordEditText = findViewById(R.id.passwordEditText);
        this.passwordAgainEditText = findViewById(R.id.passwordAgainEditText);
        this.genderRadioButtonGroup = findViewById(R.id.genderRadioButtonGroup);
        this.bodyWeightEditText = findViewById(R.id.bodyWeightEditText);
        this.ageEditText = findViewById(R.id.ageEditText);
        this.registerButton = findViewById(R.id.activitiesButton);

        this.mAuth = FirebaseAuth.getInstance();
        this.fStore = FirebaseFirestore.getInstance();

        registerButton.setOnClickListener(view -> {
            createUser();
        });

    }

    public void createUser() {
        String username = userNameEditText.getText().toString();
        String email = userEmailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String passwordAgain = passwordAgainEditText.getText().toString();

        int checkedId = genderRadioButtonGroup.getCheckedRadioButtonId();
        RadioButton radioButton = genderRadioButtonGroup.findViewById(checkedId);
        String gender = radioButton.getText().toString();

        String bodyWeight = bodyWeightEditText.getText().toString();
        String age = ageEditText.getText().toString();

        if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(passwordAgain) && !TextUtils.isEmpty(gender) && !TextUtils.isEmpty(bodyWeight) && !TextUtils.isEmpty(age)) {
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(RegisterActivity.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                        userID = mAuth.getCurrentUser().getUid();
                        DocumentReference documentReference = fStore.collection("users").document(userID);
                        Map<String,Object> user = new HashMap<>();
                        user.put("username", username);
                        user.put("email", email);
                        user.put("gender", gender);
                        user.put("bodyWeight", bodyWeight);
                        user.put("age", age);
                        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.d(LOG_TAG, "User profile is successfully created for: " + userID);
                            }
                        });
                        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                    } else {
                        Toast.makeText(RegisterActivity.this, "Registration error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    public void cancel(View view) {
        finish();
    }
}