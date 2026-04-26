package com.example.simplelogin;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);
        Intent intent = getIntent();
        String username = Objects.requireNonNull(intent.getExtras()).getString("username");
        TextInputEditText editTextName = findViewById(R.id.input_name_profile);
        editTextName.setText(username);
    }
}
