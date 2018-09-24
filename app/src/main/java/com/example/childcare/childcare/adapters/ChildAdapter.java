package com.example.childcare.childcare.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.childcare.childcare.R;
import com.example.childcare.childcare.model.Child;

import java.util.ArrayList;

public class ChildAdapter extends ArrayAdapter<Child> {
    private Activity activity;
    private ArrayList<Child> children;
    private static LayoutInflater inflater = null;

    public ChildAdapter (Activity activity, int textViewResourceId, ArrayList<Child> _children) {
        super(activity, textViewResourceId,  _children);
        try {
            this.activity = activity;
            this.children = _children;

            inflater = (LayoutInflater.from(activity));

        } catch (Exception e) {

        }
    }


    public int getCount() {
        return children.size();
    }

    public Child getItem(Child position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder {
        public TextView display_name;
//        public TextView display_number;

    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        final ViewHolder holder;
        try {
            if (convertView == null) {
                vi = inflater.inflate(R.layout.child_listview, null);
                holder = new ViewHolder();

                holder.display_name = (TextView) vi.findViewById(R.id.child_name);
//                holder.display_number = (TextView) vi.findViewById(R.id.display_number);


                vi.setTag(holder);
            } else {
                holder = (ViewHolder) vi.getTag();
            }

            holder.display_name.setText(children.get(position).getName());
//            holder.display_number.setText(children.get(position).number);

        } catch (Exception e) {

        }
        return vi;
    }
}
