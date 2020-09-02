package com.example.chitchat.data;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class AuthenticationService {

    private static AuthenticationService service = null;
    private static final String TAG = "AuthenticationService";
    private FirebaseAuth firebaseAuth;

    private AuthenticationService() { }

    public static AuthenticationService getInstance() {
        if (service == null) {
            service = new AuthenticationService();
        }
        return service;
    }

    public void login(String email, String password, final AuthenticationListener auth) {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithEmail:success");
                            auth.onSuccess();
                        } else {
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            auth.onFailed();
                        }
                    }
                });
    }

    public void register(String email, String password, final AuthenticationListener auth) {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "createUserWithEmail:success");
                            auth.onSuccess();
                        } else {
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            auth.onFailed();
                        }
                    }
                });
    }

    public boolean isUserLogged() {
        return firebaseAuth.getCurrentUser() != null;
    }

    public String getCurrentUserId() {
        return isUserLogged() ? firebaseAuth.getCurrentUser().getUid() : null;
    }

    public interface AuthenticationListener {
        void onSuccess();

        void onFailed();
    }
}
