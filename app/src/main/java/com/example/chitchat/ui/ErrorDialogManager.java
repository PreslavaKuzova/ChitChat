package com.example.chitchat.ui;

import android.content.Context;
import android.widget.Toast;

public class ErrorDialogManager {

    public static void showError(Context context, String errorMessage) {
        Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show();
    }

}
