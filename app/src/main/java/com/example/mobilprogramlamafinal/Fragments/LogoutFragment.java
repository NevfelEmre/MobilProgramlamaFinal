package com.example.mobilprogramlamafinal.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mobilprogramlamafinal.R;
import com.example.mobilprogramlamafinal.SplashActivity;
import com.google.firebase.auth.FirebaseAuth;

public class LogoutFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(requireContext(), SplashActivity.class));
        if(getActivity() != null)
            getActivity().finish();
        return inflater.inflate(R.layout.fragment_logout, container, false);
    }
}