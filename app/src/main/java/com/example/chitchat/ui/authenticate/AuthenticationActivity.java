package com.example.chitchat.ui.authenticate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;

import com.example.chitchat.R;
import com.example.chitchat.data.AuthenticationService;
import com.example.chitchat.databinding.ActivityAuthenticateBinding;

public class AuthenticationActivity extends AppCompatActivity implements AuthenticationService.AuthenticationListener {

    private ActivityAuthenticateBinding binding;
    private AuthenticationService service;

    private LoginFragment login;
    private RegisterFragment register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initData();
    }

    private void initData() {
        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_authenticate);
        this.service = AuthenticationService.getInstance();
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onFailed() {

    }
}