package com.example.appdidatico_tcc;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class Utils {
    /**
     * Gets the selected item in a Spinner component.
     * @param spinner
     * @return Current text selected.
     */
    public static String getItem(Spinner spinner) {
        return spinner.getSelectedItem().toString();
    }

    /**
     * Puts a desired value in intent, so it can be passed to the next page. Uses a try catch to
     * verify if it is a valid value.
     * @param intent
     * @param key
     * @param editText
     */
    public static void put(Intent intent, String key, EditText editText) {
        try {
            if (editText.getVisibility() == View.VISIBLE) {
                intent.putExtra(key, Double.parseDouble(editText.getText().toString()));
            } else {
                intent.putExtra(key, 0);
            }
        } catch (Exception e) {
            intent.putExtra(key, 0);
        }
    }

    /**
     * Applies a specific visibility into a set of Text View plus Edit Text.
     * @param textView
     * @param editText
     * @param visibility Code representing visibility (e.g. View.GONE).
     */
    public static void visibilityFields(TextView textView, EditText editText, int visibility) {
        textView.setVisibility(visibility);
        editText.setVisibility(visibility);
    }
}
