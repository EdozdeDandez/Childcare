package com.example.childcare.childcare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.childcare.childcare.adapters.ChildAdapter;
import com.example.childcare.childcare.model.Child;
import com.example.childcare.childcare.sql.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity activity = HomeActivity.this;

    int user_id;
    String role;

    private Button cadd;
    private ListView simpleList;

    private DatabaseHelper databaseHelper;
    private Child child;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        this.setTitle("Children");
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        initViews();
        final ArrayList<Child> children;

        if (role == "Admin") {
            cadd.setVisibility(View.GONE);
            children  = databaseHelper.getAllChildren();
        }
        else {
            children  = databaseHelper.getMyChildren(user_id);
        }
        ChildAdapter childAdapter;


        childAdapter= new ChildAdapter (HomeActivity.this, 0, children);
        simpleList.setAdapter(childAdapter);

        initListeners();
        simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            // argument position gives the index of item which is clicked
            public void onItemClick(AdapterView<?> arg0, View v,int position, long arg3)
            {
                Child child = children.get(position);
                Intent achild = new Intent(activity, ChildActivity.class);
                achild.putExtra("CHILD", child.getId());
                achild.putExtra("ROLE", role);
                achild.putExtra("USER_ID", user_id);
                startActivity(achild);
//                Toast.makeText(getApplicationContext(), "Movie Selected : "+selectedmovie,   Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * This method is to initialize views
     */
    private void initViews() {
        cadd = (Button) findViewById(R.id.cadd);
        simpleList = (ListView) findViewById(R.id.simpleListView);

        databaseHelper = new DatabaseHelper(activity);

        user_id = getIntent().getIntExtra("USER_ID", 0);
        role = getIntent().getStringExtra("ROLE");
    }

    /**
     * This method is to initialize listeners
     */
    private void initListeners() {
        cadd.setOnClickListener(this);
    }

    /**
     * This implemented method is to listen the click on view
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.cadd:
                Intent newChild = new Intent(activity, NewChildActivity.class);
                newChild.putExtra("USER_ID", user_id);
                startActivity(newChild);
                break;
        }
    }
}
