package com.example.chitchat.core;

import com.example.chitchat.data.AuthenticationService;
import com.example.chitchat.data.UsersDatabaseService;
import com.example.chitchat.ui.authenticate.RegisterFragment;

public class RegisterPresenter {
    private AuthenticationService authenticationService;
    private UsersDatabaseService databaseService;
    private RegisterFragment registerFragment;

    public RegisterPresenter(RegisterFragment fragment) {
        this.registerFragment = fragment;
        this.authenticationService = AuthenticationService.getInstance();
    }


    public boolean checkIfUsernameAlreadyExists(String username) {
        return true;
    }
}
