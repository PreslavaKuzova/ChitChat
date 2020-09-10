package com.example.chitchat.ui.authenticate;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chitchat.R;
import com.example.chitchat.core.RegisterPresenter;
import com.example.chitchat.databinding.FragmentRegisterBinding;
import com.example.chitchat.ui.ErrorDialogManager;


public class RegisterFragment extends Fragment {

    private FragmentRegisterBinding binding;
    private RegisterPresenter registerPresenter;
    private RegisterFragmentListener request;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerPresenter = new RegisterPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.edtUsername.setOnFocusChangeListener((view1, hasFocus) -> {
            if (!hasFocus)
                registerPresenter.onUsernameFocusLost(binding.edtUsername.getText().toString());
        });

        binding.edtEmail.setOnFocusChangeListener((view1, hasFocus) -> {
            if (!hasFocus)
                registerPresenter.onEmailFocusLost(binding.edtEmail.getText().toString());
        });

        binding.edtPass.setOnFocusChangeListener((view1, hasFocus) -> {
            if (!hasFocus)
                registerPresenter.onPasswordFocusLost(binding.edtPass.getText().toString());
        });

        binding.edtRepeatPass.addTextChangedListener(getRepeatPasswordTextWatcher());

        binding.btnRegister.setOnClickListener(view12 -> registerPresenter.onRegisterButtonPressed(binding.edtName.getText().toString(),
                binding.edtUsername.getText().toString(),
                binding.edtEmail.getText().toString(),
                binding.edtPass.getText().toString(),
                binding.edtRepeatPass.getText().toString()));

        binding.txtLoginRedirect.setOnClickListener(view13 -> registerPresenter.onOpenLoginBtnPressed());

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            request = (RegisterFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnRedirectionToLoginFragmentRequestListener");
        }
    }

    public void setUsernameError(String error) {
        binding.layoutUsername.setError(error);
    }

    public void setUsername(String trimmedUsername) {
        binding.edtUsername.setText(trimmedUsername);
    }

    public void setEmailError(String error) {
        binding.layoutEmail.setError(error);
    }

    public void setEmail(String trimmedEmail) {
        binding.edtEmail.setText(trimmedEmail);
    }

    public void setPasswordError(String error) {
        binding.layoutPass.setError(error);
    }

    public void setPassword(String trimmedPass) {
        binding.edtPass.setText(trimmedPass);
    }

    public void setRepeatPasswordError(String error) {
        binding.layoutRepeatPass.setError(error);
    }

    private TextWatcher getRepeatPasswordTextWatcher() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                registerPresenter.onPasswordRepeatFocusLost(binding.edtPass.getText().toString(),
                        binding.edtRepeatPass.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
    }

    public void showRegistrationError(String error) {
        ErrorDialogManager.showError(getContext(), error);
    }

    public void redirectToLoginFragment() {
        request.onRedirectionToLoginRequested();
    }

    public void redirectToMainFragment() { request.onRedirectionToMainScreenRequested(); }

    public interface RegisterFragmentListener {
        void onRedirectionToLoginRequested();

        void onRedirectionToMainScreenRequested();
    }
    
}