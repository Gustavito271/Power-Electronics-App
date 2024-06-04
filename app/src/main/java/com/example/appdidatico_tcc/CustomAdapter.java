package com.example.appdidatico_tcc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends ArrayAdapter<SpinnerCheckbox> {
    private Context mContext;
    private ArrayList<SpinnerCheckbox> listState;
    private boolean isFromView = false;

    public CustomAdapter(Context context, int resource, List<SpinnerCheckbox> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.listState = (ArrayList<SpinnerCheckbox>) objects;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView);
    }

    public View getCustomView(final int position, View convertView) {

        final ViewHolder holder;
        if (convertView == null) {
            LayoutInflater layoutInflator = LayoutInflater.from(mContext);
            convertView = layoutInflator.inflate(R.layout.spinner_item, null);
            holder = new ViewHolder();
            holder.mTextView = convertView.findViewById(R.id.text);
            holder.mCheckBox = convertView.findViewById(R.id.checkbox);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.mTextView.setText(listState.get(position).getTitle());

        isFromView = true;
        holder.mCheckBox.setChecked(listState.get(position).isSelected());
        isFromView = false;

        if (position == 0) {
            holder.mCheckBox.setVisibility(View.INVISIBLE);
        } else {
            holder.mCheckBox.setVisibility(View.VISIBLE);
        }
        holder.mCheckBox.setTag(position);
        holder.mCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {

            if (!isFromView) {
                SpinnerCheckbox selected = listState.get(position);
                selected.setSelected(isChecked);
                visibilityFields(selected.getTextView(), selected.getEditText(), isChecked);
            }
        });

        return convertView;
    }

    private static class ViewHolder {
        private TextView mTextView;
        private CheckBox mCheckBox;
    }

    private void visibilityFields(TextView textView, EditText editText, boolean visible) {
        textView.setVisibility(visible ? View.VISIBLE : View.GONE);
        editText.setVisibility(visible ? View.VISIBLE : View.GONE);
    }
}
