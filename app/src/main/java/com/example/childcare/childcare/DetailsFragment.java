package com.example.childcare.childcare;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment implements View.OnClickListener {

    private ChildActivity activity;

    private TextView name;
    private TextView age;
    private TextView dob;
    private TextView schedule;
    private TextView address;

    private Button edit;
    private Button delete;

    public DetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_details, container, false);

        name = (TextView) rootView.findViewById(R.id.childName);
        age = (TextView) rootView.findViewById(R.id.childAge);
        dob = (TextView) rootView.findViewById(R.id.ChildDOB);
        schedule = (TextView) rootView.findViewById(R.id.childSchedule);
        address = (TextView) rootView.findViewById(R.id.childAddress);

        edit = (Button) rootView.findViewById(R.id.editChild);
        delete = (Button) rootView.findViewById(R.id.deleteChild);

        Bundle crole = activity.getRole();
        String role = crole.getString("ROLE");

        if (role == "Admin") {
            edit.setVisibility(View.GONE);
            edit.setVisibility(View.GONE);
        }

        initViews();
        initListeners();

        Bundle child = activity.getChildData();
        String cname = child.getString("NAME");
        String cdob = child.getString("DOB");
        String cadd = child.getString("ADDRESS");
        String cschedule = child.getString("SCHEDULE");
        int cage = child.getInt("AGE");

        name.setText(cname);
        age.setText(Integer.toString(cage));
        dob.setText(cdob);
        schedule.setText(cschedule);
        address.setText(cadd);

        return rootView;
    }

    private void initViews () {
        activity = (ChildActivity) getActivity();
    }

    private void initListeners() {
        edit.setOnClickListener(this);
        delete.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.editChild:
                Bundle child = activity.getChildID();
                int childID = child.getInt("ID");
                Intent newChild = new Intent(activity, EditChildActivity.class);
                newChild.putExtra("CHILD", childID);
                startActivity(newChild);
                break;
            case R.id.deleteChild:
                activity.deleteChild();
                break;
        }
    }

}
