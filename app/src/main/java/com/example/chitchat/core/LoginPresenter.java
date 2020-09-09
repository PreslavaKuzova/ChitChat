package com.example.chitchat.core;

import com.example.chitchat.data.AuthenticationService;
import com.example.chitchat.ui.authenticate.LoginFragment;

public class LoginPresenter {
    private AuthenticationService authenticationService;
    private LoginFragment loginFragment;

    public LoginPresenter(LoginFragment fragment) {
        this.loginFragment = fragment;
        this.authenticationService = AuthenticationService.getInstance();
    }

    public void onFragmentCreated() {
        if(this.authenticationService.isUserLogged()) {
            this.loginFragment.redirectToMainScreen();
        }
    }

    public void onLoginButtonPressed(String email, String password) {
        if(!email.isEmpty() && !password.isEmpty()) {
            authenticationService.login(email, password, new AuthenticationService.AuthenticationListener() {
                @Override
                public void onSuccess(String uid) {
                    loginFragment.redirectToMainScreen();
                }

                @Override
                public void onFailure() {
                    loginFragment.showLoginError("Invalid credentials. Please try again.");
                }
            });
        } else {
            loginFragment.showLoginError("Fields cannot be empty. Please try again.");
        }
    }

    public void onRedirectRegisterFragmentPressed() {
        this.loginFragment.redirectToRegisterScreen();
    }

}
