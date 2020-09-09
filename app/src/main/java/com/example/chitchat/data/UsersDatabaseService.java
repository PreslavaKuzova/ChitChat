package com.example.chitchat.data;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UsersDatabaseService {

    private static UsersDatabaseService service = null;
    private static final String TAG = "UsersDatabaseService";
    private FirebaseFirestore db;

    private UsersDatabaseService() {
        db = FirebaseFirestore.getInstance();
    }

    public static UsersDatabaseService getInstance() {
        if (service == null) {
            service = new UsersDatabaseService();
        }
        return service;
    }

    public void addNewUser(String name, String username, String email, String uid, final UsersDatabaseListener listener) {

        Map<String, String> user = new HashMap<>();
        user.put("name", name);
        user.put("username", username);
        user.put("email", email);
        user.put("uid", uid);

        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        listener.onUserRegistrationSuccess();
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        listener.onUserRegistrationFailure();
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

    //change password method
    //change email method
    //add room

    public void checkIfUsernameExists(String username, FieldExistsListener fieldExistsListener) {
        checkIfDataExists("username", username, fieldExistsListener);
    }

    public void checkIfEmailExists(String email, FieldExistsListener fieldExistsListener) {
        checkIfDataExists("email", email, fieldExistsListener);
    }

    private void checkIfDataExists(String field, String data, FieldExistsListener fieldExistsListener) {
        db.collection("users")
                .whereEqualTo(field, data)
                .get()
                .addOnCompleteListener(task -> {
                    if(!task.isSuccessful()) {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                        fieldExistsListener.onNotSuccessful();
                        return;
                    }

                    if (task.getResult() != null && task.getResult().size() > 0) {
                        fieldExistsListener.onFieldExists();
                    } else {
                        fieldExistsListener.onFieldIsMissing();
                    }
                });
    }

    public interface FieldExistsListener {
        void onFieldExists();

        void onFieldIsMissing();

        void onNotSuccessful();
    }

    public interface UsersDatabaseListener {
        void onUserRegistrationSuccess();

        void onUserRegistrationFailure();
    }
}
