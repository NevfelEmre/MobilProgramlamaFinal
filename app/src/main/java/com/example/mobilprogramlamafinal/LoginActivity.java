package com.example.mobilprogramlamafinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    Button SignUpButton, LogInButton;
    EditText MailField, PasswordField;
    String mailTxt, passwordTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        SetVariables();
        ButtonFunctions();
    }


    private void ButtonFunctions(){
        SignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
        LogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetTexts();
                if(!mailTxt.isEmpty() && !passwordTxt.isEmpty()){
                    mAuth = FirebaseAuth.getInstance();
                    mAuth.signInWithEmailAndPassword(mailTxt, passwordTxt).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isComplete()){
                                Toast.makeText(LoginActivity.this, "Giriş yapılıyor.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            else
                                Toast.makeText(LoginActivity.this, "Bilgilerden en az biri hatalı.", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(LoginActivity.this, "Sunucu hatası.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else
                    Toast.makeText(LoginActivity.this, "Bütün boşlukları doldurunuz.", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void GetTexts(){
        mailTxt = MailField.getText().toString();
        passwordTxt = PasswordField.getText().toString();
    }
    private void SetVariables(){
        SignUpButton = findViewById(R.id.button5);
        LogInButton = findViewById(R.id.button3);
        MailField = findViewById(R.id.editTextTextEmailAddress);
        PasswordField = findViewById(R.id.editTextTextPassword);
    }
}