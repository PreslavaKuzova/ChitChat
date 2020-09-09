package com.example.chitchat.core;

import com.example.chitchat.data.AuthenticationService;
import com.example.chitchat.data.UsersDatabaseService;
import com.example.chitchat.ui.authenticate.RegisterFragment;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterPresenter {
    private AuthenticationService authenticationService;
    private UsersDatabaseService databaseService;
    private RegisterFragment registerFragment;

    private UsersDatabaseService.UsersDatabaseListener databaseListener = new UsersDatabaseService.UsersDatabaseListener() {
        @Override
        public void onUserRegistrationSuccess() {
            registerFragment.redirectToLoginFragment();
        }

        @Override
        public void onUserRegistrationFailure() {
            registerFragment.showRegistrationError();
        }
    };

    public RegisterPresenter(RegisterFragment fragment) {
        this.registerFragment = fragment;
        this.authenticationService = AuthenticationService.getInstance();
        this.databaseService = UsersDatabaseService.getInstance();
    }

    public void onUsernameFocusLost(String username) {
        String trimmedUsername = this.formatFieldData(username);
        getUsernameErrorMessage(trimmedUsername, error1 -> {
            registerFragment.setUsernameError(error1);
            registerFragment.setUsername(trimmedUsername);
        });

    }

    private void getUsernameErrorMessage(String username, OnMessageArrivedListener listener) {
        if (username.length() == 0) {
            listener.onFieldError("This field cannot be blank.");
        } else {
            databaseService.checkIfUsernameExists(username, new UsersDatabaseService.FieldExistsListener() {
                @Override
                public void onFieldExists() {
                    listener.onFieldError("Username already exists.");
                }

                @Override
                public void onFieldIsMissing() {
                    listener.onFieldError(null);
                }

                @Override
                public void onNotSuccessful() {
                    listener.onFieldError("Something went wrong.");
                }
            });
        }
    }

    public void onEmailFocusLost(String email) {
        String trimmedEmail = formatFieldData(email);
        getEmailErrorMessage(email, error -> {
            registerFragment.setEmailError(error);
            registerFragment.setEmail(trimmedEmail);
        });
    }

    private void getEmailErrorMessage(String email, OnMessageArrivedListener listener) {
        final String regex = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

        if (email.length() == 0) {
            listener.onFieldError("This field cannot be blank.");
        } else {
            databaseService.checkIfEmailExists(email, new UsersDatabaseService.FieldExistsListener() {
                @Override
                public void onFieldExists() {
                    listener.onFieldError("User with that email address already exists.");
                }

                @Override
                public void onFieldIsMissing() {
                    Pattern pattern = Pattern.compile(regex);
                    Matcher matcher = pattern.matcher(email);
                    String error = matcher.matches() ? null : "Please enter a valid email address.";
                    listener.onFieldError(error);
                }

                @Override
                public void onNotSuccessful() {
                    listener.onFieldError("Something went wrong.");
                }
            });
        }
    }

    public void onPasswordFocusLost(String password) {
        String trimmedPassword = formatFieldData(password);
        String error = getPasswordErrorMessage(trimmedPassword);
        registerFragment.setPasswordError(error);
        registerFragment.setPassword(trimmedPassword);
    }

    private String getPasswordErrorMessage(String password) {
        final String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$";
        String error = null;

        if (password.length() == 0) {
            error = "This field cannot be blank.";
        } else {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(password);
            error = matcher.matches() ? null :
                    "Password should be at least 6 characters long, contain both uppercase and lowercase and at least ope digit.";
        }

        return error;
    }

    public void onPasswordRepeatFocusLost(String password, String repeatedPassword) {
        String error = getPasswordRepeatErrorMessage(password, repeatedPassword);
        registerFragment.setRepeatPasswordError(error);
    }

    private String getPasswordRepeatErrorMessage(String password, String repeatedPassword) {
        String error = password.equals(repeatedPassword) ? null : "Passwords should match.";
        return error;
    }

    private String formatFieldData(String data) {
        return data.trim();
    }

    public void onRegisterButtonPressed(String name, String username,
                                        String email, String password, String repeatedPassword) {
        getUsernameErrorMessage(username, error -> {
            if (error == null
                    && getPasswordErrorMessage(password) == null
                    && getPasswordRepeatErrorMessage(password, repeatedPassword) == null) {

                registerNewUser(name, username, email, password);
            }
        });
    }

    public void registerNewUser(String name, String username, String email, String password) {
        authenticationService.register(email, password, new AuthenticationService.AuthenticationListener() {
            @Override
            public void onSuccess(String uid) {
                databaseService.addNewUser(name, username, email, uid, databaseListener);
            }

            @Override
            public void onFailure() {
                registerFragment.showRegistrationError();
            }
        });
    }

    public void onRedirectLoginFragmentPressed() {
        registerFragment.redirectToLoginFragment();
    }

    public interface OnMessageArrivedListener {
        void onFieldError(String error);

    }

}
