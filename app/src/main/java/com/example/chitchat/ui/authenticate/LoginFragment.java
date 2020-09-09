package com.example.chitchat.ui.authenticate;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.chitchat.R;
import com.example.chitchat.core.LoginPresenter;
import com.example.chitchat.databinding.FragmentLoginBinding;
import com.example.chitchat.ui.ErrorDialogManager;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;
    private LoginPresenter loginPresenter;
    private OnRedirectionToAnotherScreenRequestListener request;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginPresenter = new LoginPresenter(this);
        loginPresenter.onFragmentCreated();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btnLogin.setOnClickListener(view1 -> {
            String email = binding.edtEmail.getText().toString();
            String password = binding.edtPass.getText().toString();
            loginPresenter.onLoginButtonPressed(email, password);
        });

        binding.txtRegisterRedirect.setOnClickListener(view2 -> {
            loginPresenter.onRedirectRegisterFragmentPressed();
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            request = (OnRedirectionToAnotherScreenRequestListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnRedirectionToRegistrationScreenRequestListener");
        }
    }

    public void redirectToRegisterScreen() {
        request.onRedirectionToRegisterScreenRequested();
    }

    public void redirectToMainScreen() {
        request.onRedirectionToMainScreenRequested();
    }

    public void showLoginError(String error) {
        ErrorDialogManager.showError(getContext(), error);
    }

    public interface OnRedirectionToAnotherScreenRequestListener {
        void onRedirectionToRegisterScreenRequested();

        void onRedirectionToMainScreenRequested();
    }
}