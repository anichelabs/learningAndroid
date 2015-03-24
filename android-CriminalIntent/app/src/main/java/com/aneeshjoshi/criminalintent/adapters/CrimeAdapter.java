package com.aneeshjoshi.criminalintent.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.aneeshjoshi.criminalintent.R;
import com.aneeshjoshi.criminalintent.models.Crime;

import java.util.ArrayList;

/**
* Created by anjoshi on 1/27/15.
*/
public class CrimeAdapter extends ArrayAdapter<Crime> {

    private static class ViewHolder {
        TextView titleTextView;
        CheckBox solvedCheckBox;
        TextView dateTextView;
    }

    public CrimeAdapter(Context activityContext, ArrayList<Crime> crimes){
        super(activityContext, 0, crimes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder viewHolder;
        //If we weren't given a view, infalte on
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_item_crime, parent, false);
            viewHolder.titleTextView = (TextView) convertView.findViewById(R.id.crime_list_item_titleTextView);
            viewHolder.dateTextView = (TextView) convertView.findViewById(R.id.crime_list_item_dateTextView);
            viewHolder.solvedCheckBox = (CheckBox) convertView.findViewById(R.id.crime_list_item_solvedCheckBox);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //Configure the view for this Crime
        Crime c = getItem(position);

        viewHolder.titleTextView.setText(c.getTitle());

        viewHolder.dateTextView.setText(c.getDate().toString());

        viewHolder.solvedCheckBox.setChecked(c.isSolved());

        return convertView;
    }

}
