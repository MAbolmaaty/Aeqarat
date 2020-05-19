package com.aqratsa.aeqarat.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AppCompatActivity;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class SoftKeyboard {
    public static void dismissKeyboardInActivity(Context context) {
        AppCompatActivity activity = (AppCompatActivity) context;
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(
                    INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }
}
