package com.example.childcare.childcare;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.example.childcare.childcare.adapters.ChildPagerAdapter;
import com.example.childcare.childcare.model.Child;
import com.example.childcare.childcare.sql.DatabaseHelper;

public class ChildActivity extends AppCompatActivity {
    private final AppCompatActivity activity = ChildActivity.this;

    int child_id;
    int user_id;
    String role;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private ChildPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    private DatabaseHelper databaseHelper;
    private Child child;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Create the adapter that will return a fragment for each of the three

        mSectionsPagerAdapter = new ChildPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        initViews();

    }

    /**
     * This method is to initialize views
     */
    private void initViews() {

        databaseHelper = new DatabaseHelper(activity);

        child_id = getIntent().getIntExtra("CHILD", 0);
        role = getIntent().getStringExtra("ROLE");
        user_id = getIntent().getIntExtra("USER_ID", 0);

        child = databaseHelper.getChild(child_id);
    }

    public Bundle getRole() {
        Bundle myRole = new Bundle();
        myRole.putString("ROLE", role);
        return myRole;
    }

    public Bundle getChildID() {
        Bundle childID = new Bundle();
        childID.putInt("ID", child_id);
        return childID;
    }

    public Bundle getChildData() {
        Bundle childData = new Bundle();
        childData.putString("NAME",child.getName());
        childData.putInt("AGE",child.getAge());
        childData.putString("ADDRESS",child.getAddress());
        childData.putString("SCHEDULE",child.getSchedule());
        childData.putString("DOB",child.getDob());
        return childData;
    }

    public void deleteChild() {
        databaseHelper.deleteChild(child);
        Intent home = new Intent(activity, HomeActivity.class);
        home.putExtra("USER_ID", user_id);
        home.putExtra("ROLE", role);
        startActivity(home);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_child, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
