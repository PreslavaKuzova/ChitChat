package com.example.chitchat.ui.authenticate;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chitchat.R;
import com.example.chitchat.data.AuthenticationService;


public class RegisterFragment extends Fragment implements AuthenticationService.AuthenticationListener {

    private AuthenticationService service;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onFailed() {

    }
}