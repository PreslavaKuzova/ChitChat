package com.example.chitchat.ui.authenticate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.example.chitchat.R;
import com.example.chitchat.databinding.ActivityAuthenticateBinding;
import com.example.chitchat.ui.MainActivity;

public class AuthenticationActivity extends AppCompatActivity implements RegisterFragment.RegisterFragmentListener,
        LoginFragment.LoginFragmentListener {

    private ActivityAuthenticateBinding binding;
    private final FragmentManager manager = getSupportFragmentManager();

    private LoginFragment loginFragment;
    private RegisterFragment registerFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_authenticate);

        this.loginFragment = new LoginFragment();
        this.registerFragment = new RegisterFragment();

        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.grp_container, loginFragment);
        transaction.commit();
    }

    @Override
    public void onRedirectionToLoginRequested() {
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.grp_container, loginFragment);
        transaction.commit();
    }

    @Override
    public void onRedirectionToRegisterScreenRequested() {
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.grp_container, registerFragment);
        transaction.commit();
    }

    @Override
    public void onRedirectionToMainScreenRequested() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}