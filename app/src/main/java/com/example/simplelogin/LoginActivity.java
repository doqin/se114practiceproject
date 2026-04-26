package com.example.simplelogin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        TextView registerButton = findViewById(R.id.button_register);
        registerButton.setOnClickListener((v) -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        });
        Button signInButton = findViewById(R.id.button_sign_in);
        signInButton.setOnClickListener((v) -> {
            TextInputEditText editTextEmailUsername = findViewById(R.id.input_email_username_login);
            TextInputEditText editTextPassword = findViewById(R.id.input_password_login);
            if (editTextEmailUsername != null && !Objects.requireNonNull(editTextEmailUsername.getText()).toString().isEmpty()
                && editTextPassword != null && !Objects.requireNonNull(editTextPassword.getText()).toString().isEmpty()
            ) {
                Intent intent = new Intent(this, PostsActivity.class);
                intent.putExtra("username", editTextEmailUsername.getText().toString());
                startActivity(intent);
            }
        });
    }
}
