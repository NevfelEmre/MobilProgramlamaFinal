package com.example.mobilprogramlamafinal.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobilprogramlamafinal.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class GalleryFragment extends Fragment {

    FirebaseFirestore firestore;
    LinearLayout fotolarListe;
    Spinner dropdown;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        SetVariables();
        GetPhotos();
        return inflater.inflate(R.layout.fragment_gallery, container, false);
    }
    private void GetPhotos(){
        firestore.collection("Label_DataStructure").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    List<String> labels = new ArrayList<>();
                    for(QueryDocumentSnapshot document : task.getResult()){
                        String label = document.getString("label");
                        labels.add(label);
                    }
                    if(!labels.isEmpty()){
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireActivity(),
                                android.R.layout.simple_spinner_item, labels);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


                    dropdown.setAdapter(adapter);
                    dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            String selectedlabel = dropdown.getSelectedItem().toString();
                            Toast.makeText(getContext(), selectedlabel, Toast.LENGTH_SHORT).show();
                            GetPosts(selectedlabel);
                        }


                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                        });
                    }
                    else
                        Toast.makeText(getContext(), "Hata!", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(getContext(), "Hata oluştu", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Hata oluştu", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void SetVariables(){
        firestore = FirebaseFirestore.getInstance();
        fotolarListe = getView().findViewById(R.id.asdsdf);
        dropdown = getView().findViewById(R.id.spinner);
    }
    private void GetPosts(String labels){
        fotolarListe.removeAllViews();
        CollectionReference postscollection = firestore.collection("PostModel");
        Query query = postscollection.whereArrayContains("labels",labels);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    String uid = document.getString("uid");
                    String url = document.getString("url");



                    ImageView img = new ImageView(requireContext());
                    LinearLayout yan = new LinearLayout(requireContext());
                    TextView user = new TextView(requireContext());

                    DocumentReference userdocref = firestore.collection("User_DataStructure").
                            document(uid);
                    userdocref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            DocumentSnapshot documentSnapshot = task.getResult();
                            if(documentSnapshot.exists()){
                                String firstName = documentSnapshot.getString("firstName");
                                String lastName = documentSnapshot.getString("lastName");
                                user.setText(firstName+" "+lastName);
                            }
                        }
                    });
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(600,600);
                    img.setLayoutParams(layoutParams);

                    yan.setLayoutParams(new LinearLayout.LayoutParams
                            (LinearLayout.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.MATCH_PARENT));
                    yan.addView(img);
                    yan.addView(user);
                    fotolarListe.addView(yan);

                }

            }
        });
    }

}