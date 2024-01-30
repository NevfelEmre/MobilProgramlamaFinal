package com.example.mobilprogramlamafinal.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobilprogramlamafinal.DataStructures.Label_DataStructure;
import com.example.mobilprogramlamafinal.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class LabelFragment extends Fragment {
    EditText LabelField, DescField;
    Button AddButton;
    LinearLayout LabelListe;
    FirebaseFirestore dataBase = FirebaseFirestore.getInstance();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_label, container, false);
        SetVariables();
        ButtonFunction();
        return view;
    }
    private void GetLabels(){
        LabelListe.removeAllViews();
        dataBase.collection("Label_DataStructure").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document : task.getResult()){
                        LinearLayout child = new LinearLayout(requireContext());
                        TextView textView = new TextView(requireContext());
                        textView.setText(document.getString("label"));
                        textView.setTextSize(22);
                        child.addView(textView);
                        LabelListe.addView(child);
                    }
                }
                else
                    Toast.makeText(getContext(), "Sunucuda bir hata oluştu.", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Sunucuda bir hata oluştu.", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void SendLabel(){
        String LabelTxt = LabelField.getText().toString();
        String DescTxt = DescField.getText().toString();
        if(!LabelTxt.isEmpty() && !DescTxt.isEmpty()){
            Label_DataStructure label = new Label_DataStructure(LabelTxt, DescTxt);
            dataBase.collection("Label_DataStructure").add(label).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    Toast.makeText(getContext(), "Etiket Başarıyla eklendi.", Toast.LENGTH_SHORT).show();
                    GetLabels();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), "Bir hata oluştu.", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else
            Toast.makeText(getContext(), "Boşlukları doldurunuz.", Toast.LENGTH_SHORT).show();
    }
    private void ButtonFunction(){
        AddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendLabel();
            }
        });
    }
    private void SetVariables(){
        LabelField = getView().findViewById(R.id.editTextText3);
        DescField = getView().findViewById(R.id.editTextText4);
        AddButton = getView().findViewById(R.id.button7);
        LabelListe = getView().findViewById(R.id.LabelList);
    }
}