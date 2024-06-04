package com.example.appdidatico_tcc;

import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class SpinnerCheckbox {
    private String title;
    private boolean selected;
    private EditText editText;
    private TextView textView;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public EditText getEditText() {
        return editText;
    }

    public void setEditText(EditText editText) {
        this.editText = editText;
    }

    public TextView getTextView() {
        return textView;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }

}
