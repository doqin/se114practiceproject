package com.example.simplelogin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Button createButton = findViewById(R.id.button_create);
        createButton.setOnClickListener((v) -> {
            TextInputEditText inputName = findViewById(R.id.input_name);
            TextInputEditText inputEmail = findViewById(R.id.input_email);
            TextInputEditText inputPassword = findViewById(R.id.input_password);
            TextInputEditText inputConfirmPassword = findViewById(R.id.input_confirm_password);
            if (inputName != null && !Objects.requireNonNull(inputName.getText()).toString().isEmpty()
            &&  inputEmail != null && !Objects.requireNonNull(inputEmail.getText()).toString().isEmpty()
            && inputPassword != null && !Objects.requireNonNull(inputPassword.getText()).toString().isEmpty()
            && inputConfirmPassword != null && !Objects.requireNonNull(inputConfirmPassword.getText()).toString().isEmpty()) {
                Intent intent = new Intent(this, ProfileActivity.class);
                startActivity(intent);
            }
        });
    }
}