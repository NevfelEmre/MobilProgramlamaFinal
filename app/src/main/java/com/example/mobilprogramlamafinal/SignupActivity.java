package com.example.mobilprogramlamafinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mobilprogramlamafinal.DataStructures.User_DataStructure;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignupActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    Button signUpButton, logInButton;
    EditText firstName, lastName, eMail, password;
    String fName, lName, email, pWord;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        setVariables();
        ButtonFunctions();
    }
    private void ButtonFunctions(){
        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetAllTexts();
                if(!fName.isEmpty() && !lName.isEmpty() && !email.isEmpty() && !pWord.isEmpty()){
                    mAuth = FirebaseAuth.getInstance();
                    mAuth.createUserWithEmailAndPassword(email, pWord).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isComplete()){
                                String userId = task.getResult().getUser().getUid();
                                FirebaseFirestore dataBase = FirebaseFirestore.getInstance();
                                User_DataStructure user = new User_DataStructure(fName,lName,email);
                                dataBase.collection("User_DataStructure").document(userId).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(SignupActivity.this, "Kayıt başarılı bir şekilde oluşturuldu.", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                                        startActivity(intent);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(SignupActivity.this, "Sunucuda hata oldu", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SignupActivity.this, "Bir hata meydana geldi", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else
                    Toast.makeText(SignupActivity.this, "Bütün boşlukları doldurunuz.", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void GetAllTexts(){
        fName = firstName.getText().toString();
        lName = lastName.getText().toString();
        email = eMail.getText().toString();
        pWord = password.getText().toString();
    }
    private void setVariables(){
        signUpButton = findViewById(R.id.button4);
        logInButton = findViewById(R.id.button6);
        firstName = findViewById(R.id.editTextText);
        lastName = findViewById(R.id.editTextText2);
        eMail = findViewById(R.id.editTextTextEmailAddress2);
        password = findViewById(R.id.editTextTextPassword2);
    }
}