package com.example.mobilprogramlamafinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SplashActivity extends AppCompatActivity {
    private Button loginButton, SignupButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        SetButtons();
        ButtonFunctions();
    }
    private void SetButtons(){
        loginButton = findViewById(R.id.button);
        SignupButton = findViewById(R.id.button2);
    }
    private void ButtonFunctions(){
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        SignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SplashActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
    }
}