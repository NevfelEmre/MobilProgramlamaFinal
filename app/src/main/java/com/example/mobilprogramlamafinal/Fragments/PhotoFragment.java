package com.example.mobilprogramlamafinal.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.mobilprogramlamafinal.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class PhotoFragment extends Fragment {
    private static final int CAMERA_REQUEST = 1888;
    ImageView img;
    Button cameraButton;
    LinearLayout photo_layout;
    Bitmap photo;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    StorageReference storageReference;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        SetVariables();
        getLabel();
        ButtonFunction();
        return inflater.inflate(R.layout.fragment_photo, container, false);
    }
    private void SetVariables() {
        storageReference = FirebaseStorage.getInstance().getReference();
        cameraButton = getView().findViewById(R.id.button8);
        photo_layout = getView().findViewById(R.id.imageView4);
        img= getView().findViewById(R.id.LinearLayout111);
    }
    private void ButtonFunction() {
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePhoto();
            }
        });
    }

    private void takePhoto(){
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent,CAMERA_REQUEST);
    }
    public void onActivityResult(int requestCode,int resultCode , Intent data){
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            photo = (Bitmap) data.getExtras().get("data");
            img.setImageBitmap(photo);
        }
    }
    private void getLabel(){
        photo_layout.removeAllViews();
        db.collection("LabelModel").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        CheckBox checkBox = new CheckBox(requireContext());
                        checkBox.setText(document.getString("label"));
                        checkBox.setTag(document.getId());
                        photo_layout.addView(checkBox);
                    }
                }else{
                    Toast.makeText(getContext(),"İnternet Bağlantınızı kontrol Ediniz",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}